package com.axonactive.training.api;

import com.axonactive.training.entity.TodoItem;
import com.axonactive.training.service.TodoServiceImp;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static com.axonactive.training.contants.TodoTemp.TODO_ITEMS_TEMP;

@Path("/todosss")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoAPI {

    @Inject
    TodoServiceImp todoServiceImp;

    @GET
    public Response getAll() {
        System.out.println("call service db");
        return Response.ok(todoServiceImp.getAll()).build();
    }

    @GET
    @Path("getById/{id}")
    public Response getById(@PathParam("id") long id) {
        System.out.println("call service db");
        return Response.ok(todoServiceImp.getItem(id)).build();
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

    @GET
    @Transactional
    @Path("getTempItem")
    public Response getTempItem() {
        return Response.ok().entity(TODO_ITEMS_TEMP).build();
    }

    @POST
    @Transactional
    @Path("createTemp")
    public boolean createTemp(TodoItem todo) {
        return todoServiceImp.createTempItem(todo);
    }

}
