package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

@ApplicationScoped
public class SendData extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:sendData")
                .log("Processing message in Validationroutes: ${body}")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:processedQueue");
             //   .to("activemq:queue:processedQueue?jmsMessageType=Object");

    }
}
