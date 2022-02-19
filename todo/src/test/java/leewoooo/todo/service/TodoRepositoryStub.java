package leewoooo.todo.service;

import leewoooo.todo.domain.Todo;
import leewoooo.todo.dto.todo.reqeust.CreateTodoRequest;
import leewoooo.todo.repository.todo.TodoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoRepositoryStub implements TodoRepository {

    final static Long EXIST_ID = 1L;
    final static Long NOT_EXIST_ID = 999L;
    final static String NAME = "Hello";

    @Override
    public Todo save(Todo todo) {
        return todo;
    }

    @Override
    public Optional<Todo> findById(Long id) {
        if (id == EXIST_ID){
            Todo todo = new Todo(new CreateTodoRequest(NAME));
            return Optional.of(todo);
        }else {
            return Optional.empty();
        }
    }

    @Override
    public List<Todo> findAll() {
        List<Todo> todos = new ArrayList<>();
        CreateTodoRequest[] createTodoRequests = {
                new CreateTodoRequest(NAME),
                new CreateTodoRequest(NAME)
        };

        for (CreateTodoRequest req : createTodoRequests) {
            todos.add(new Todo(req));
        }

        return todos;
    }

    @Override
    public void deleteById(Long id) {
    }
}
