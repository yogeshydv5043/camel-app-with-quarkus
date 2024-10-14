package org.tech.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.tech.entity.PersonModel;
import org.tech.service.PersonService;

import java.util.List;

@Path("/person")
public class PersonResource {

    @Inject
    private PersonService personService;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPerson(){
        List<PersonModel> personModelList=personService.getAllPerson();
        return Response.ok(personModelList).build();
    }
}
