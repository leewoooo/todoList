package leewoooo.todo.controller.todo;

import leewoooo.todo.domain.Todo;
import leewoooo.todo.dto.todo.reqeust.CreateTodoRequest;
import leewoooo.todo.dto.todo.reqeust.UpdateTodoRequest;
import leewoooo.todo.dto.todo.response.TodoListResponse;
import leewoooo.todo.dto.todo.response.TodoResponse;
import leewoooo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> register(@Valid @RequestBody CreateTodoRequest req) {
        Todo saved = todoService.register(req);
        return new ResponseEntity<>(new TodoResponse(saved), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> findTodo(@PathVariable(required = true, name = "id") Long id) {
        Todo selected = todoService.findOne(id);
        return new ResponseEntity<>(new TodoResponse(selected),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TodoListResponse>> findTodos() {
        List<TodoListResponse> responseBody = todoService.findAll()
                .stream()
                .map(todo -> new TodoListResponse(todo))
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequest req) {
        Todo updated = todoService.update(id, req);
        return new ResponseEntity<>(new TodoResponse(updated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeTodo(@PathVariable Long id) {
        todoService.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
