package com.bvn13.bug.camel.fluentproducertemplate.camelbugfluentproducertemplate;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleRouteBuilder extends RouteBuilder {
    public static final String ENDPOINT_IN = "direct://ENDPOINT_LISTEN";
    public static final String ENDPOINT_OUT = "mock://ENDPOINT_WRITE";

    @Override
    public void configure() throws Exception {
        from(ENDPOINT_IN)
                .log("TEST BODY: ${body}")
                .to(ENDPOINT_OUT);
    }
}
