package org.tech.routes;

import org.apache.camel.builder.RouteBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.MessageData;


@ApplicationScoped
public class RouteListener extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:queue:processedQueue")
                .log("Received message in processed route :- ${body}")
                 .unmarshal().json(JsonLibrary.Jackson, MessageData.class)
                .process(exchange -> {
                    // Get the object from the message body
                    MessageData messageData = exchange.getIn().getBody(MessageData.class);

                    if (messageData != null) {
                        System.out.println("Received ObjectMessage: " + messageData);
                        // Optionally modify the message if needed, then set it back
                        exchange.getIn().setBody(messageData);
                    } else {
                        System.out.println("Received null object message.");
                    }
                })
                // Use choice for conditional routing
                .choice()
                // If 'from' equals 'BHS_IN', send to 'bhs_inQueue'
                .when(simple("${body.from} == 'BHS-IN'"))
                .log("Routing message to BHS-IN queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:bhsIn")

                // If 'from' equals 'BHS_OUT', send to 'bhs_outQueue'
                .when(simple("${body.from} == 'BHS-OUT'"))
                .log("Routing message to BHS-OUT queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:bhsOut")

                // Otherwise, route to the default queue
                .otherwise()
                .log("Routing message to default queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:defaultBhs")
                .end();

    }
}
