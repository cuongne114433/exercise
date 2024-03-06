package com.axonactive.training.api;

import com.axonactive.training.dto.TodoItem;
import com.axonactive.training.proxy.TodoDatabaseProxy;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoApi {

    @Inject
    @RestClient
    TodoDatabaseProxy todoDatabaseProxy;

    @GET
    @Fallback(fallbackMethod = "fallbackGetTodoListFromListService")
    public Response getAll() {
        return todoDatabaseProxy.getAll();
    }

    @PUT
    @Transactional
    @Path("/{itemId}")
    public Response update(@PathParam("itemId") Long itemId, TodoItem todo) {
        if (todoDatabaseProxy.update(itemId, todo)) {
            return Response.ok().build();
        }
        return Response.status(404, "item can not found").build();
    }

    @DELETE
    @Path("/{itemId}")
    public Response delete(@PathParam("itemId") Long itemId) {
        if (todoDatabaseProxy.delete(itemId)) {
            return Response.ok().build();
        }
        return Response.status(404, "item can not found").build();
    }

    @POST
    @Transactional
    public Response create(TodoItem todo) {
        if (todoDatabaseProxy.create(todo)) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }

//    @GET
//    @Path("/getAllSorted/{sortColumn}")
//    public Response getAllSorted(@PathParam("sortColumn") String sortColumn) {
//        return Response.ok(listTodoProxy.getAllSorted(sortColumn)).build();
//    }
//
//    @GET
//    @Path("getMessage")
//    @Fallback(fallbackMethod = "fallbackGetMessage")
//    public Response getMessageFromNumberService() {
//        return Response.ok(numberProxy.generateIsbnNumber().isNumber).build();
//    }
//
//    @GET
//    @Path("getNumberById/{id}")
//    @Fallback(fallbackMethod = "fallbackGetNumberFromNumberService")
//    public Response getNumberFromNumberService(@PathParam("id") int id) {
//        return Response.ok(numberProxy.getNumberById(id)).build();
//    }



    public Response fallbackGetMessage() {
        return Response.status(206).build();
    }

    public Response fallbackGetTodoListFromListService() {
        return Response.ok().entity(new ArrayList<>()).build();
    }



}
