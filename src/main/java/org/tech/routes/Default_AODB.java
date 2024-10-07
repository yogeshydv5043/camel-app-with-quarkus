package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.tech.dto.request.RequestToData;

@ApplicationScoped
public class Default_AODB extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:queue:defaultAodb")
                .unmarshal().json(JsonLibrary.Jackson, RequestToData.class)
                .process(exchange -> {
                    RequestToData requestToData = exchange.getIn().getBody(RequestToData.class);
                    exchange.getIn().setBody(requestToData.getXmlData());
                })
                .log("This is Default-AODB :")
                .to("log: {body}");
    }
}
