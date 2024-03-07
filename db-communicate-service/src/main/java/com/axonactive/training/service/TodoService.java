package com.axonactive.training.service;

import com.axonactive.training.entity.TodoItem;

import java.util.List;

public interface TodoService {
    boolean create(TodoItem doItem);
    boolean update(long id, TodoItem doItem);
    boolean delete(long id);
    List<TodoItem> getAll();
    TodoItem getItem(long id);

    boolean createTempItem(TodoItem todoItem);
    List<TodoItem> getTempTodoItem(TodoItem todoItem);
}
