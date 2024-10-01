package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.tech.dto.response.ValidationResponse;


@ApplicationScoped
public class CamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Define the route for XML validation
        from("direct:validateXml")
                .doTry()
                .to("validator:Person.xsd") // Validate XML against the specified XSD
                .setHeader("validationStatus", constant(true)) // Set validation status
                .process(exchange -> {
                    // Set the response for successful validation
                    ValidationResponse response = new ValidationResponse(true);
                    exchange.getIn().setBody(response);
                })
                .to("log:${body}") // Send success message to the queue
                .doCatch(Exception.class)
                .process(exchange -> {
                    // Handle validation failure
                    String errorMessage = "Validation failed: " + exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class).getMessage();
                    ValidationResponse response = new ValidationResponse(false, errorMessage);
                    exchange.getIn().setBody(response);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400)) // Set HTTP response code to 400
                .to("log:error"); // Log the error// Log the error
    }
}
