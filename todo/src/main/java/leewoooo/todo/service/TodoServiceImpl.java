package leewoooo.todo.service;

import leewoooo.todo.domain.Todo;
import leewoooo.todo.dto.error.ErrorCode;
import leewoooo.todo.dto.todo.reqeust.CreateTodoRequest;
import leewoooo.todo.dto.todo.reqeust.UpdateTodoRequest;
import leewoooo.todo.dto.todo.response.TodoListResponse;
import leewoooo.todo.dto.todo.response.TodoResponse;
import leewoooo.todo.exception.todo.CustomException;
import leewoooo.todo.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public Todo register(CreateTodoRequest req) {
        return todoRepository.save(new Todo(req));
    }

    @Override
    @Transactional(readOnly = true)
    public Todo findOne(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo update(Long id, UpdateTodoRequest req) {
        Todo selected = todoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        selected.updateTodo(req);

        return todoRepository.save(selected);
    }

    @Override
    public void removeById(Long id) {
        todoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        todoRepository.deleteById(id);
    }

}
