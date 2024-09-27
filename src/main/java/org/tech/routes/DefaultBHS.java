package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.MessageData;

@ApplicationScoped
public class DefaultBHS extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:queue:default-bhs")
                .log(" This Is DefaultBHS : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, MessageData.class)
                .process(exchange -> {
                    // Assuming the body is of type MessageData (custom class)
                    MessageData messageData = exchange.getIn().getBody(MessageData.class);

                    // Extract the 'from' field from the message
                    String to = messageData.getTo();
                       String  xmlData=  messageData.getXmlData();
                    // Log the 'from' field value
                    System.out.println("To field value: " + to);

                    // Optionally modify the message if needed, then set it back
                    exchange.getIn().setBody(messageData);
                })
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:default-aodb");

    }
}
