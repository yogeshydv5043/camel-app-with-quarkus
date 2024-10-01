package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.MessageData;

@ApplicationScoped
public class AODB_OUT extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:queue:aodb-out")
                .unmarshal().json(JsonLibrary.Jackson, MessageData.class)
                .process(exchange -> {
                    // Assuming the body is of type MessageData (custom class)
                    MessageData messageData = exchange.getIn().getBody(MessageData.class);

                    // Extract the 'from' field from the message
                    String xmlData = messageData.getXmlData();

                    // Log the 'from' field value
                   // System.out.println("To field value: " + xmlData);

                    // Optionally modify the message if needed, then set it back
                    exchange.getIn().setBody(messageData);
                })
                .log("This is AODB-OUT : ${body}");

    }
}
