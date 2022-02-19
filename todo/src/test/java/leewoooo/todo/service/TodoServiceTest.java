package leewoooo.todo.service;

import leewoooo.todo.domain.Todo;
import leewoooo.todo.dto.todo.reqeust.CreateTodoRequest;
import leewoooo.todo.dto.todo.reqeust.UpdateTodoRequest;
import leewoooo.todo.exception.todo.CustomHttpException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TodoServiceTest {

    TodoService todoService = new TodoServiceImpl(new TodoRepositoryStub());

    @Test
    @DisplayName("Todo 등록")
    void register() {
        //given
        CreateTodoRequest req = new CreateTodoRequest("Hello");
        Todo todo = new Todo(req);

        //when
        Todo saved = todoService.register(req);

        //then
        assertThat(saved.getName()).isEqualTo("Hello");
    }

    @Test
    @DisplayName("id로 Todo 찾기 - 존재할 때")
    void findOne(){
        //given
        Long givenId = TodoRepositoryStub.EXIST_ID;

        //when
        Todo selected = todoService.findOne(givenId);

        //then
        assertThat(selected).isNotNull();
    }

    @Test
    @DisplayName("id로 Todo찾기 - 존재하지 않을 때")
    void findOneNotExist(){
        //given
        Long givenId = TodoRepositoryStub.NOT_EXIST_ID;

        //when
        //then
        assertThrows(CustomHttpException.class, () -> todoService.findOne(givenId));
    }

    @Test
    @DisplayName("Todo 전부 찾기")
    void findAll(){
        //given

        //when
        List<Todo> todos = todoService.findAll();

        //then
        assertThat(todos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Todo Update - Todo가 없을 때")
    void updateNotExist(){
        //given
        Long givenId = TodoRepositoryStub.NOT_EXIST_ID;

        UpdateTodoRequest req = new UpdateTodoRequest("hello", false);

        //when
        //then
        assertThrows(CustomHttpException.class, () -> todoService.update(givenId, req));
    }

    @Test
    @DisplayName("Todo Update - Todo가 존재할 때")
    void update(){
        //given
        Long givenId = TodoRepositoryStub.EXIST_ID;

        //when
        UpdateTodoRequest req = new UpdateTodoRequest("change", false);

        Todo updated = todoService.update(givenId, req);

        //then
        assertThat(updated.getName()).isEqualTo("change");
    }

    @Test
    @DisplayName("Todo remove - 존재하지 않을 때")
    void removeByIdNotExist() {
        //given
        Long givenId = TodoRepositoryStub.NOT_EXIST_ID;

        //when
        //then
        assertThrows(CustomHttpException.class, () -> todoService.removeById(givenId));
    }

    @Test
    @DisplayName("Todo remove - 존재할 때")
    void removeById(){
        //given
        Long givenId = TodoRepositoryStub.EXIST_ID;

        //when
        try {
            todoService.removeById(givenId);
        }catch (CustomHttpException e){
            fail("무조건 성공해야 한다.");
        }
    }

}