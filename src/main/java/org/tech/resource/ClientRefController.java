package org.tech.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.tech.dto.request.ClientRefDto;
import org.tech.entity.ClientRef;
import org.tech.service.DBService;

import java.util.List;

@Path("/clientref")
public class ClientRefController {

    @Inject
    private DBService dbService;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRefClient(ClientRefDto name) {
        String result = dbService.addClientRef(name);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllRefClient() {
        List<ClientRef> result = dbService.getall();
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
