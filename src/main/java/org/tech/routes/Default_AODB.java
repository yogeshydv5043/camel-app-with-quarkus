package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class Default_AODB extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:queue:default-aodb")
                .log("This is Default-AODB : ${body}");
    }
}
