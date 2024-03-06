package com.axonactive.training.proxy;

import com.axonactive.training.dto.TodoItem;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "todolist.proxy")
@Path("/getList")
@Produces(MediaType.APPLICATION_JSON)
public interface ListTodoProxy {

    @GET
    List<TodoItem> getAll();

    @GET
    @Path("/getAllSorted/{sortColumn}")
    List<TodoItem> getAllSorted(@PathParam("sortColumn") String sortColumn);

}
