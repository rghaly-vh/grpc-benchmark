package com.vibrenthealth.benchmark.service.services;

import com.vibrenthealth.benchmark.rest.spi.DoSomethingRequest;
import com.vibrenthealth.benchmark.rest.spi.DoSomethingResponse;
import com.vibrenthealth.benchmark.rest.spi.DoSomethingServiceSpi;
import org.springframework.stereotype.Service;

@Service
public class DoSomethingService implements DoSomethingServiceSpi {

    @Override
    public DoSomethingResponse doSomething(String testId, int iterationId, long sleepMills) {
        DoSomethingRequest request = new DoSomethingRequest();
        request.setTestId(testId);
        request.setIterationId(iterationId);
        request.setMilliseconds(sleepMills);
        long reqRec = System.currentTimeMillis();
        DoSomethingResponse response = new DoSomethingResponse();
        response.setRequestReceived(reqRec);
        response.setTestId(request.getTestId());
        response.setIterationId(request.getIterationId());
        response.setAllWas200(true);

        try {
            Thread.sleep(request.getMilliseconds());
        } catch (InterruptedException e) {
            response.setAllWas200(false);
        }

        response.setResponseSent(System.currentTimeMillis());
        response.setLocalDuration(System.currentTimeMillis() - reqRec);
        return response;
    }
}
