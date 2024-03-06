package com.axonactive.training.api;

import com.axonactive.training.entity.TodoItem;
import com.axonactive.training.service.TodoServiceImp;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoAPI {

    @Inject
    TodoServiceImp todoServiceImp;

    @GET
    public Response getAll() {
        return Response.ok(todoServiceImp.getAll()).build();
    }

    @PUT
    @Transactional
    @Path("/{itemId}")
    public boolean update(@PathParam("itemId") Long itemId, TodoItem todo) {
        return todoServiceImp.update(itemId, todo);
    }

    @DELETE
    @Transactional
    @Path("/{itemId}")
    public boolean delete(@PathParam("itemId") Long itemId) {
        return todoServiceImp.delete(itemId);
    }

    @POST
    @Transactional
    public boolean create(TodoItem todo) {
       return todoServiceImp.create(todo);
    }

}
