package com.axonactive.training.proxy;

import com.axonactive.training.dto.IsNumber;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "number.proxy")
@Path("/api/numbers")
public interface NumberProxy {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    IsNumber generateIsbnNumber() ;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getNumberById/{id}")
    IsNumber getNumberById(@PathParam("id") int id);
}
