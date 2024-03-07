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
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
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
    @Timeout(2000)
    @CircuitBreaker(
            requestVolumeThreshold = 8,
            failureRatio = 0.5,
            delay = 5000,
            successThreshold = 4)
    @Retry(maxRetries = 5)
    @Counted(name = "performedChecks", description = "Count....")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.SECONDS)
    @Operation(summary = "Get all list todo", description = "Returns all item todo")
    @APIResponse(responseCode = "200", description = "Successful operation")
    public Response getAll() {
        System.out.println("Retry");
        return todoDatabaseProxy.getAll();
    }

    @GET
    @Path("getById/{id}")
    @Fallback(fallbackMethod = "fallbackGetTodoItemFromListService")
    @Timeout(2000)
    @CircuitBreaker(
            requestVolumeThreshold = 4,
            failureRatio = 0.5,
            delay = 5000,
            successThreshold = 4)
    @Retry(maxRetries = 5)
    public Response getById(@PathParam("id") long id) {
        System.out.println("Retry");
        return todoDatabaseProxy.getById(id);
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
    @Fallback(fallbackMethod = "fallbackCreateNewTodoItem")
    public Response create(@RequestBody(description = "Input item info with json format" ) TodoItem todo) {
        if (todoDatabaseProxy.create(todo)) {
            return Response.status(201).entity("created").build();
        }
        return Response.notModified().build();
    }

    public Response fallbackGetMessage() {
        return Response.status(206).build();
    }

    public Response fallbackGetTodoListFromListService() {
        return Response.ok().entity("new ArrayList<>()").build();
    }

    public Response fallbackGetTodoItemFromListService(long id) {
        return Response.ok().entity("Get todo item with id = " + id).build();
    }

    public Response fallbackCreateNewTodoItem(TodoItem todoItem) {
        return Response.ok().entity(todoItem).build();
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





}
