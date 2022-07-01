package com.vibrenthealth.benchmark.service.api;

import com.vibrenthealth.benchmark.rest.spi.DoSomethingResponse;
import com.vibrenthealth.benchmark.rest.spi.DoSomethingServiceSpi;
import com.vibrenthealth.benchmark.service.services.DoSomethingService;
import com.vibrenthealth.benchmark.service.services.GrpcClient;
import com.vibrenthealth.benchmark.service.services.RestClient;
import com.vibrenthealth.benchmark.service.spi.BenchmarkRequest;
import com.vibrenthealth.benchmark.service.spi.BenchmarkResponse;
import com.vibrenthealth.benchmark.service.spi.BenchmarkService;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.vibrenthealth.benchmark.service.spi.BenchmarkResponse.*;

@RestController
public class BenchmarkApi implements BenchmarkService {

    private static final Logger log = LoggerFactory.getLogger( BenchmarkApi.class );
    @Autowired
    RestClient restClient;

    @Autowired
    GrpcClient grpcClient;

    @Autowired
    DoSomethingService springService;

    @GetMapping(value = "/testRest")
    public void testRest() {
        restClient.doSomething("", 0, 200);
        log.info("=====> done REST call");
    }

    @GetMapping(value = "/testGrpc")
    public void testGrpc() {
        grpcClient.doSomething("", 0, 200);
        log.info("=====> done GRPC call");
    }

    @PostMapping(value= "/run")
    public BenchmarkResponse run(@RequestBody BenchmarkRequest request) {
       String serviceType = request.getServiceType();
       DoSomethingServiceSpi service;
       if(serviceType.equalsIgnoreCase("SPRING"))
           service = springService;
       else if (serviceType.equalsIgnoreCase("REST"))
            service = restClient;
       else if (serviceType.equalsIgnoreCase("GRPC"))
            service = grpcClient;
       else {
           BenchmarkResponse err = new BenchmarkResponse();
           err.setServiceType("ERROR: Service [" + request.getServiceType() + "] not recognized");
           return err;
       }

        SummaryStats primerResults = prime(service, request.getPrimer());
        BenchmarkResponse response = run(service, request);
        response.setPrimerResults(primerResults);
        response.setResultsSummary(calculateResultsSummary(response.getRawMetrics()));
        
        return response;
    }

    private Summary calculateResultsSummary(List<RawResults> rawData) {
        List<Long> srvcDurationData = new ArrayList<>();
        List<Long> clntDurationData = new ArrayList<>();
        List<Long> reqLatencyData = new ArrayList<>();
        List<Long> respLatencyData = new ArrayList<>();

        rawData.forEach(d -> {
            srvcDurationData.add(d.getServiceDuration());
            clntDurationData.add(d.getClientDuration());
            reqLatencyData.add(d.getServiceReceived() - d.getClientSent());
            respLatencyData.add(d.getClientReceived() - d.getServiceResponded());
        });

        return new Summary(
                getStats(srvcDurationData),
                getStats(clntDurationData),
                getStats(reqLatencyData),
                getStats(respLatencyData)
        );
    }

    private SummaryStats prime(DoSomethingServiceSpi service, BenchmarkRequest.Primer primer) {
        List<Long> durations = new ArrayList<>();
        long sleepVal = primer.getSleepMills();
        for(int i = 0; i < primer.getIterations(); i++) {
            long t0 = System.currentTimeMillis();
            service.doSomething("", i, sleepVal);
            durations.add(System.currentTimeMillis() - t0);
        }

        return getStats(durations);
    }

    protected BenchmarkResponse run(DoSomethingServiceSpi service, BenchmarkRequest req) {
        BenchmarkResponse resp = new BenchmarkResponse();
        resp.setServiceType(req.getServiceType());
        resp.setTestId(req.getTestId());
        resp.setLoopCount(req.getLoopCount());
        resp.setRepeats(req.getRepeats());
        resp.setSleepMills(req.getSleepMills());

        for(int r = 0; r < req.getRepeats(); r++ ) {
            List<RawResults> rawResults = resp.getRawMetrics();
            for(int l = 0; l < req.getLoopCount(); l++) {
                RawResults raw = run(service, req.getTestId(), r, req.getSleepMills());
                raw.setLoopId(l);
                raw.setRepeatId(r);
                rawResults.add(raw);
            }
        }

        return resp;
    }

    private RawResults run(DoSomethingServiceSpi service, String testId, int iterationId, long sleepMills) {
        RawResults result = new RawResults();
        result.setClientSent(System.currentTimeMillis());
        DoSomethingResponse resp = service.doSomething(testId, iterationId, sleepMills);
        result.setClientReceived(System.currentTimeMillis());
        result.setClientDuration(result.getClientReceived() - result.getClientSent());
        result.setServiceReceived(resp.getRequestReceived());
        result.setServiceResponded(resp.getResponseSent());
        result.setServiceDuration(resp.getLocalDuration());

        return result;
    }

    @GetMapping(value="/sampleRequest")
    public BenchmarkRequest getSampleRequest() {
        BenchmarkRequest req = new BenchmarkRequest();
        req.setServiceType("one of [SPRING|REST|GRPC]");
        req.setTestId("testId");
        req.setLoopCount(1);
        req.setRepeats(10);
        req.setSleepMills(100);
        req.setPrimer(new BenchmarkRequest.Primer(10, 10));
        return req;
    }

    private SummaryStats getStats(List<Long> values) {
        SummaryStats result = new SummaryStats();
        SummaryStatistics stats = new SummaryStatistics();

        values.stream().mapToDouble(a -> a).forEach(stats::addValue);
        result.setMin((long) stats.getMin());
        result.setMax((long) stats.getMax());
        result.setAvg((float) stats.getMean());
        result.setStd((float) stats.getStandardDeviation());

        return result;
    }
}
