package com.jatrain.microservices.apigateway.configurations;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class APIGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {

        Function<PredicateSpec, Buildable<Route>> predicateSpecBuildableFunction =
                p -> {
                    Function<GatewayFilterSpec, UriSpec> gatewayFilterSpecUriSpecFunction =
                            f -> f.addRequestHeader("MyHeader", "MyURI")
                                    .addRequestParameter("MyParam", "MyValue");
                    return p.path("/get").filters(
                            gatewayFilterSpecUriSpecFunction
                    ).uri("http://httpbin.org:80");
                };
        return routeLocatorBuilder.routes().route(predicateSpecBuildableFunction)
                .route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion")).build();
    }
}
