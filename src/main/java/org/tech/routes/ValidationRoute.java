package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.MessageData;

@ApplicationScoped
public class ValidationRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        from("direct:Validationroutes")
//                .log("Processing message in Validationroutes: ${body}")
//                .marshal().json(JsonLibrary.Jackson)
//                .to("activemq:queue:processedQueue");
//             //   .to("activemq:queue:processedQueue?jmsMessageType=Object");

        from("direct:Validationroutes")
                .log("Processing message in Validationroutes: ${body}")
                .doTry()
                // Perform XML validation using the provided XSD schema
                .to("validator:Person.xsd")
                // If validation succeeds, log success and set response
                .log("XML validated successfully")
                .setBody(constant("Validation successful, XML is valid"))
                .doCatch(Exception.class)
                // On validation failure, log the error and set failure response
                .log("Validation failed: ${exception.message}")
                .setBody().simple("Validation failed at line: ${exception.lineNumber}, error: ${exception.message}")
                .end();



    }
}
