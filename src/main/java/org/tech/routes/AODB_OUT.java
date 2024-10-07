package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.MessageData;
import org.tech.dto.request.RequestToData;

@ApplicationScoped
public class AODB_OUT extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:queue:aodbOut")
                .unmarshal().json(JsonLibrary.Jackson, RequestToData.class)
                .process(exchange -> {
                    // Assuming the body is of type MessageData (custom class)
                    RequestToData requestToData = exchange.getIn().getBody(RequestToData.class);
                    // Extract the 'from' field from the message
                    String xmlData = requestToData.getXmlData();
                    // Optionally modify the message if needed, then set it back
                    exchange.getIn().setBody(requestToData.getXmlData());
                })
                .log("This is AODB-OUT : ")
                .to("log: ${body}");

    }
}
