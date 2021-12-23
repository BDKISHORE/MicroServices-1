package com.jatrain.microservices.currencyexchangeservice.Controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name="sample-api", fallbackMethod = "hardCodedResponse")
//    @CircuitBreaker(name="default", fallbackMethod = "hardCodedResponse")
    @RateLimiter(name="sample-api")
    public String sampleAPI() {
        logger.info("sample-api call received");
//        ResponseEntity<String> sampleRest = new RestTemplate().getForEntity("http://localhost:8083/dummy", String.class);
//        return sampleRest.getBody();
        return "Sample-api";
    }

    public String hardCodedResponse(Exception ex) {
        return "fallback-response";
    }
}
