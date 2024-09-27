package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.MessageData;

@ApplicationScoped
public class ValidationRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:Validationroutes")
                .log("Processing message in Validationroutes: ${body}")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:processedQueue");
             //   .to("activemq:queue:processedQueue?jmsMessageType=Object");

    }
}
