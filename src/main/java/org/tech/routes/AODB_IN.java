package org.tech.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class AODB_IN extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:queue:aodb-in")
                .log("This Is AODB-IN : ${body}");
    }
}
