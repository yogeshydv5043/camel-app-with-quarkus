package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.MessageData;

@ApplicationScoped
public class BHS_OUT extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:queue:bhs-out")
                .log("This is Bsh_OUT BODY : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, MessageData.class)
                .process(exchange -> {
                    // Assuming the body is of type MessageData (custom class)
                    MessageData messageData = exchange.getIn().getBody(MessageData.class);

                    // Extract the 'from' field from the message
                    String to = messageData.getTo();

                    // Log the 'from' field value
                    System.out.println("To field value: " + to);

                    // Optionally modify the message if needed, then set it back
                    exchange.getIn().setBody(messageData);
                })
                //Add custom work

                .choice()
                // If 'from' equals 'AODB_IN', send to 'aodb_inQueue'
                .when(simple("${body.to} == 'AODB_IN'"))
                .log("Routing message to AODB_IN queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:aodb-in")

                // If 'from' equals 'AODB_OUT', send to 'aodb_outQueue'
                .when(simple("${body.to} == 'AODB_OUT'"))
                .log("Routing message to AODB_OUT queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:aodb-out")

                // Otherwise, route to the default queue
                .otherwise()
                .log("Routing message to default queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:defaultAodb")
                .end();

    }
}
