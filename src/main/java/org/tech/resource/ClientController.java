package org.tech.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.tech.model.Client;
import org.tech.service.DataSendService;

@Path("/client")
public class ClientController {


    @Inject
    private DataSendService ciService;


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUser(Client client) {
        String client1 = ciService.validateAndSend(client);
        return Response.status(Response.Status.OK).entity(client1).build();
    }
}
