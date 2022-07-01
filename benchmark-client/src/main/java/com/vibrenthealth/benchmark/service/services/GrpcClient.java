package com.vibrenthealth.benchmark.service.services;

import com.vibrenthealth.benchmark.grpc.service.lib.DoSomethingReply;
import com.vibrenthealth.benchmark.grpc.service.lib.DoSomethingRequest;
import com.vibrenthealth.benchmark.grpc.service.lib.GrpcBenchmarkServiceGrpc;
import com.vibrenthealth.benchmark.rest.spi.DoSomethingResponse;
import com.vibrenthealth.benchmark.rest.spi.DoSomethingServiceSpi;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class GrpcClient implements DoSomethingServiceSpi {

    private static final Logger log = LoggerFactory.getLogger( GrpcClient.class );

    @Value("${grpc.client.grpcBenchmarkService.address}")
    private String grpcBaseUrl;

//    @net.devh.boot.grpc.client.inject.GrpcClient("grpcBenchmarkService")
    private GrpcBenchmarkServiceGrpc.GrpcBenchmarkServiceBlockingStub grpcClient;

    private ManagedChannel channel;

    @PostConstruct
    public void init() {
        log.info("GrpcService baseUrl =[" + grpcBaseUrl + "]");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        grpcClient = GrpcBenchmarkServiceGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void cleanup() {
        channel.shutdown();
    }

    public DoSomethingResponse doSomething(String testId, int iterationId, long sleepMills) {
        DoSomethingRequest request = DoSomethingRequest.newBuilder()
                .setTestId(testId)
                .setIterationId(iterationId)
                .setMilliseconds(sleepMills)
                .build();
        long reqRec = System.currentTimeMillis();

        DoSomethingReply response = grpcClient.doSomething(request);

        DoSomethingResponse result = new DoSomethingResponse();
        result.setTestId(response.getTestId());
        result.setIterationId(response.getIterationId());
        result.setLocalDuration(response.getLocalDuration());
        result.setResponseSent(response.getResponseSent());
        result.setRequestReceived(response.getRequestReceived());
        return result;
    }

}
