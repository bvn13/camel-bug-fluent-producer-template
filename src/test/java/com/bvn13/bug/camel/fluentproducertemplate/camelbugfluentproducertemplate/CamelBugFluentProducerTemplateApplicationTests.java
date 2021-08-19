package com.bvn13.bug.camel.fluentproducertemplate.camelbugfluentproducertemplate;

import org.apache.camel.CamelContext;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.bvn13.bug.camel.fluentproducertemplate.camelbugfluentproducertemplate.SimpleRouteBuilder.ENDPOINT_IN;
import static com.bvn13.bug.camel.fluentproducertemplate.camelbugfluentproducertemplate.SimpleRouteBuilder.ENDPOINT_OUT;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@CamelSpringBootTest
@SpringBootTest
class CamelBugFluentProducerTemplateApplicationTests {

    static final String DATA = "test data";

    @Autowired
    CamelContext camelContext;

    @Autowired
    SimpleRouteBuilder routeBuilder;

    @Test
    void whenFluentProducerCreatedWithoutProcessor_thenBodyIsSent() throws Exception {
        MockEndpoint mockedEndpoint = camelContext.getEndpoint(ENDPOINT_OUT, MockEndpoint.class);
        mockedEndpoint.expectedBodiesReceived(DATA);

        camelContext.createFluentProducerTemplate()
                .withBody(DATA)
//                .withProcessor(exchange -> exchange.setProperty("prop", DATA))
                .to(ENDPOINT_IN)
                .send();

        mockedEndpoint.assertIsSatisfied();
    }

    @Test
    void whenFluentProducerCreatedWithProcessor_thenBodyIsNull() throws Exception {
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            MockEndpoint mockedEndpoint = camelContext.getEndpoint(ENDPOINT_OUT, MockEndpoint.class);
            mockedEndpoint.expectedBodiesReceived(DATA);

            camelContext.createFluentProducerTemplate()
                    .withBody(DATA)
                    .withProcessor(exchange -> exchange.setProperty("prop", DATA))
                    .to(ENDPOINT_IN)
                    .send();

            mockedEndpoint.assertIsSatisfied();
        });

        assertTrue(exception.getMessage().contains(format("Expected: <%s> but was: <null>", DATA)));
    }

}
