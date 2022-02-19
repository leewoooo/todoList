package leewoooo.todo.service.todo;

import leewoooo.todo.domain.Todo;
import leewoooo.todo.dto.todo.reqeust.CreateTodoRequest;
import leewoooo.todo.dto.todo.reqeust.UpdateTodoRequest;

import java.util.List;

public interface TodoService {
    Todo register(CreateTodoRequest req);
    Todo findOne(Long id);
    List<Todo> findAll();
    Todo update(Long id,UpdateTodoRequest req);
    void removeById(Long id);
}
