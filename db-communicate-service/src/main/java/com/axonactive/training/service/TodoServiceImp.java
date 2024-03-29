package com.axonactive.training.service;

import com.axonactive.training.entity.TodoItem;
import com.axonactive.training.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

import static com.axonactive.training.contants.TodoTemp.TODO_ITEMS_TEMP;

@ApplicationScoped
public class TodoServiceImp implements TodoService{
    @Inject
    TodoRepository repository;

    @Override
    public boolean create(TodoItem doItem) {
        repository.persist(doItem);
        if (repository.isPersistent(doItem)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(long id, TodoItem item) {
        TodoItem doItem = repository.findById(id);
        if (doItem != null) {
            doItem.setItem(item.getItem());
            doItem.setCompleted(item.getCompleted());
            repository.persist(doItem);
            if (repository.isPersistent(doItem)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean delete(long id) {
        TodoItem doItem = repository.findById(id);
        if (doItem != null) {

            repository.delete(doItem);
            return true;
        }
        return false;
    }

    @Override
    public List<TodoItem> getAll() {
        return repository.findAll().list();
    }

    @Override
    public TodoItem getItem(long id) {
        return repository.findById(id);
    }

    @Override
    public boolean createTempItem(TodoItem todoItem) {
        TODO_ITEMS_TEMP.add(todoItem);
        return true;
    }

    @Override
    public List<TodoItem> getTempTodoItem(TodoItem todoItem) {
        return TODO_ITEMS_TEMP;
    }
}
