package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.MessageData;
import org.tech.dto.request.RequestToData;

@ApplicationScoped
public class BHS_IN extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:queue:bhsIn")
                .log("This is BSH-IN BODY : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, MessageData.class)
                .process(exchange -> {
                    // Assuming the body is of type MessageData (custom class)
                    MessageData messageData = exchange.getIn().getBody(MessageData.class);

                    RequestToData requestToData = new RequestToData(messageData.getTo(), messageData.getXmlData());
//                    requestToData.setTo(messageData.getTo());
//                    requestToData.setXmlData(messageData.getXmlData());

                    // Extract the 'from' field from the message
                  //  String to = requestToData.getTo();

                    // Log the 'from' field value
                   // System.out.println("To field value: " + to);

                    // Optionally modify the message if needed, then set it back
                    exchange.getIn().setBody(requestToData);
                })
                //Add custom work

                .choice()
                // If 'from' equals 'AODB_IN', send to 'aodb_inQueue'
                .when(simple("${body.to} == 'AODB-IN'"))
                .log("Routing message to AODB-IN queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:aodbIn")

                // If 'from' equals 'AODB_OUT', send to 'aodb_outQueue'
                .when(simple("${body.to} == 'AODB-OUT'"))
                .log("Routing message to AODB-OUT queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:aodbOut")

                // Otherwise, route to the default queue
                .otherwise()
                .log("Routing message to default queue")
                .marshal().json(JsonLibrary.Jackson)
                .to("activemq:queue:defaultAodb")
                .end();
    }
}
