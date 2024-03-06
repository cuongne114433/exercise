package com.axonactive.training.repository;

import com.axonactive.training.entity.TodoItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TodoRepository implements PanacheRepository<TodoItem> {

}
