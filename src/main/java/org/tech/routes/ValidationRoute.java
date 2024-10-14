package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.tech.dto.response.ValidationResponse;


@ApplicationScoped
public class ValidationRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:validateXml")
                .doTry()
                .log("${body}")
                .to("validator:Person.xsd")
              //  .to("validator:AIDXFlightLeg.xsd")
                .setHeader("validationStatus", constant(true))
                .process(exchange -> {

                    String xmlData =exchange.getIn().getBody().toString();

                    ValidationResponse response = new ValidationResponse();
                    response.setValid(true);
                    exchange.getIn().setBody(response);
                })

                .to("log:Validation Status Is : ${body.valid}")
                .doCatch(Exception.class)
                .process(exchange -> {
                    // Handle validation failure
                    String errorMessage = "Validation failed: " + exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class).getMessage();
                    ValidationResponse response = new ValidationResponse(false, errorMessage);
                    exchange.getIn().setBody(response);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .to("log:error");
    }
}
