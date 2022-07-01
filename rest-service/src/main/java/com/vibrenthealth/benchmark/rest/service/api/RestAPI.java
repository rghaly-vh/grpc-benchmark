package com.vibrenthealth.benchmark.rest.service.api;

import com.vibrenthealth.benchmark.rest.spi.DoSomethingRequest;
import com.vibrenthealth.benchmark.rest.spi.DoSomethingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPI {
    private static final Logger log = LoggerFactory.getLogger( RestAPI.class );

    @GetMapping(value = "/ping")
    public void ping() {
//        log.info("Got PINGED");
    }

    @PostMapping(value = "/doSomething", produces = MediaType.APPLICATION_JSON_VALUE)
    public DoSomethingResponse doSomething(@RequestBody DoSomethingRequest request) {
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
