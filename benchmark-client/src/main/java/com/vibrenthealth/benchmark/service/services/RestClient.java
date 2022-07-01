package com.vibrenthealth.benchmark.service.services;

import com.vibrenthealth.benchmark.rest.spi.DoSomethingRequest;
import com.vibrenthealth.benchmark.rest.spi.DoSomethingResponse;
import com.vibrenthealth.benchmark.rest.spi.DoSomethingServiceSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.ConnectException;

@Service
@EnableConfigurationProperties
public class RestClient implements DoSomethingServiceSpi {

    private static final Logger log = LoggerFactory.getLogger( RestClient.class );

    @Value("${rest-service-base-url}")
    private String restBaseUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private WebClient.RequestBodyUriSpec uriSpec;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder
                .baseUrl(restBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CACHE_CONTROL, "no-cache")
                .build();

        this.uriSpec = webClient.post();
        this.uriSpec.uri("/doSomething");

        log.info("Created webclient to base url [" + restBaseUrl + "]");
    }

    @Override
    public DoSomethingResponse doSomething(String testId, int iterationId, long sleepMills) {
        DoSomethingRequest request = new DoSomethingRequest();
        request.setTestId(testId);
        request.setIterationId(iterationId);
        request.setMilliseconds(sleepMills);
        long reqRec = System.currentTimeMillis();

        Mono<DoSomethingResponse> response = null;
        DoSomethingResponse result = null;
        try {
            response = webClient
                    .post()
                    .uri("/doSomething")
                    .body(Mono.just(request), DoSomethingRequest.class)
                    .retrieve()
                    .bodyToMono(DoSomethingResponse.class)
            ;
            result = response.block();
        } catch(Exception e) {
            log.error("Error calling RestService", e);
        }
        return result;

    }
}
