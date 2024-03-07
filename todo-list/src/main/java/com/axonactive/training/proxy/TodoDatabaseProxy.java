package com.axonactive.training.proxy;

import com.axonactive.training.dto.TodoItem;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "tododatabase.proxy")
@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public interface TodoDatabaseProxy {
    @GET
    Response getAll();

    @GET
    @Path("getById/{id}")
    Response getById(@PathParam("id") long id);

    @PUT
    @Transactional
    @Path("/{itemId}")
    boolean update(@PathParam("itemId") Long itemId, TodoItem todo);

    @DELETE
    @Transactional
    @Path("/{itemId}")
    boolean delete(@PathParam("itemId") Long itemId);

    @POST
    @Transactional
    boolean create(TodoItem todo);

    @GET
    @Transactional
    @Path("getTempItem")
    Response getTempItem();
}
