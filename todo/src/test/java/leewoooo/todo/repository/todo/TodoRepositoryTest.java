package leewoooo.todo.repository.todo;

import leewoooo.todo.domain.Todo;
import leewoooo.todo.dto.todo.reqeust.CreateTodoRequest;
import leewoooo.todo.dto.todo.reqeust.UpdateTodoRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName("todo 저장하기")
    void save() {
        //given
        CreateTodoRequest req = new CreateTodoRequest("hello");
        Todo todo = new Todo(req);

        //when
        Todo saved = todoRepository.save(todo);

        //then
        assertThat(saved.getName()).isEqualTo("hello");
        assertThat(saved.getCompleted()).isFalse();
        assertThat(saved.getCompletedAt()).isNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("ID값으로 조회")
    void findById() {
        //given
        CreateTodoRequest req = new CreateTodoRequest("hello");
        Todo todo = new Todo(req);

        Long givenId = todoRepository.save(todo).getId();

        //when
        Todo selected = todoRepository.findById(givenId).orElse(null);

        //then
        assertThat(selected).isNotNull();
        assertThat(selected.getId()).isEqualTo(givenId);
        assertThat(selected.getName()).isEqualTo("hello");
    }

    @Test
    @DisplayName("ID 값으로 조회할 떄 존재하지 않음.")
    void findByIdNotExist() {
        //given
        Long givenId = 9999L;

        //when
        Todo selected = todoRepository.findById(givenId).orElse(null);

        //then
        assertThat(selected).isNull();
    }

    @Test
    @DisplayName("Todo 전체 조회")
    void findAll() {
        //given
        CreateTodoRequest[] createTodoRequests = {
                new CreateTodoRequest("todo1"),
                new CreateTodoRequest("todo2")
        };

        for (CreateTodoRequest req : createTodoRequests) {
            todoRepository.save(new Todo(req));
        }

        //when
        List<Todo> todos = todoRepository.findAll();

        //then
        assertThat(todos).isNotEmpty();
        assertThat(todos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Todo 전체 조회 존재하지 않을 때")
    void findAllNotExist() {
        //given

        //when
        List<Todo> todos = todoRepository.findAll();

        //then
        assertThat(todos).isEmpty();
    }

    @Test
    @DisplayName("todo 저장 후 update")
    void update() {
        //given
        CreateTodoRequest createReq = new CreateTodoRequest("hello");
        Todo todo = new Todo(createReq);

        UpdateTodoRequest updateReq = UpdateTodoRequest
                .builder()
                .name("changed")
                .completed(true)
                .build();

        //when
        Todo saved = todoRepository.save(todo);
        saved.updateTodo(updateReq);

        Todo updated = todoRepository.save(saved);

        //then
        assertThat(updated.getName()).isEqualTo("changed");
        assertThat(updated.getCompleted()).isTrue();
        assertThat(updated.getCompletedAt()).isNotNull();
    }

    @Test
    @DisplayName("update시 name만 update할 경우")
    void updateOnlyName() {
        //given
        CreateTodoRequest createReq = new CreateTodoRequest("hello");
        Todo todo = new Todo(createReq);

        UpdateTodoRequest updateReq = UpdateTodoRequest
                .builder()
                .name("changed")
                .completed(null)
                .build();

        //when
        Todo saved = todoRepository.save(todo);
        saved.updateTodo(updateReq);

        Todo updated = todoRepository.save(saved);

        //then
        assertThat(updated.getName()).isEqualTo("changed");
        assertThat(updated.getCompleted()).isFalse();
        assertThat(updated.getCompletedAt()).isNull();
    }

    @Test
    @DisplayName("todo 저장 후 삭제")
    void deleteById() {
        //given
        CreateTodoRequest createReq = new CreateTodoRequest("hello");
        Todo todo = new Todo(createReq);

        //when
        Long targetId = todoRepository.save(todo).getId();
        todoRepository.deleteById(targetId);

        //then
        Todo selected = todoRepository.findById(targetId).orElse(null);
        assertThat(selected).isNull();
    }

    @Test
    @DisplayName("todo 저장 후 삭제 존재하지 않을 때")
    void deleteByIdNotExist() {
        //given
        Long targetId = 9999L;

        //when
        //then
        assertThrows(EmptyResultDataAccessException.class, () -> todoRepository.deleteById(targetId));
    }
}