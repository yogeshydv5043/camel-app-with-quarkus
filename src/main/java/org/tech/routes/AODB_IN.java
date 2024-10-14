package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.RequestToData;

@ApplicationScoped
public class AODB_IN extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:queue:aodbIn")
                .unmarshal().json(JsonLibrary.Jackson, RequestToData.class)
                .process(exchange -> {
                    RequestToData requestToData = exchange.getIn().getBody(RequestToData.class);
                    String xmlData = requestToData.getXmlData();
                    exchange.getIn().setBody(xmlData);
                })
                .log("This is AODB-IN SERVICE : ")
                .log("Data Sending for table ...")
                .to("direct:personSave");


    }
}
