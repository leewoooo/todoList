package leewoooo.todo.controller.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import leewoooo.todo.domain.Todo;
import leewoooo.todo.dto.error.ErrorCode;
import leewoooo.todo.dto.todo.reqeust.CreateTodoRequest;
import leewoooo.todo.dto.todo.reqeust.UpdateTodoRequest;
import leewoooo.todo.exception.todo.CustomHttpException;
import leewoooo.todo.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TodoService todoService;

    @Autowired
    ObjectMapper objectMapper; //for json

    String todoResponseContent = "{\"id\":null,\"name\":\"hello\",\"completed\":false,\"completedAt\":null,\"createdAt\":null,\"updatedAt\":null}";
    String badRequestContent = "{\"status\":400,\"message\":\"Bad Request\"}";
    String notFoundContent = "{\"status\":404,\"message\":\"Not Found\"}";

    @Test
    @DisplayName("todo register")
    void register() throws Exception {
        //given
        CreateTodoRequest req = new CreateTodoRequest("hello");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "Hello");

        given(todoService.register(any(CreateTodoRequest.class))).willReturn(new Todo(req));
        //when
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                )
                //then
                .andExpect(status().isCreated())
                .andExpect(content().string(todoResponseContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo register - RequestBody 없음")
    void registerWithOutRequestBody() throws Exception {
        //given

        //when
        mockMvc.perform(post("/todos"))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(badRequestContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo register - name값이 Empty")
    void registerEmptyName() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "");

        //when
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                )
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(badRequestContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo Id값으로 조회 - PathVariable 타입이 맞지 않을 경우")
    void registerMissMatch() throws Exception {
        //given

        //when
        mockMvc.perform(get("/todos/missMatch"))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(badRequestContent))
                .andReturn();
    }

    @Test
    @DisplayName("todo Id값으로 조회")
    void findTodo() throws Exception {
        //given
        Long givenId = 1L;
        given(todoService.findOne(givenId)).willReturn(new Todo(new CreateTodoRequest("hello")));


        //when
        mockMvc.perform(get("/todos/" + givenId))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(todoResponseContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo Id값으로 조회 - 존재하지 않을 때")
    void findTodoNotExist() throws Exception {
        //given
        Long givenId = 999L;
        given(todoService.findOne(givenId)).willThrow(new CustomHttpException(ErrorCode.NOT_FOUND));

        //when
        mockMvc.perform(get("/todos/" + givenId))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().string(notFoundContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo 전체 조회")
    void findTodos() throws Exception {
        //given
        List<Todo> todos = new ArrayList<>();
        CreateTodoRequest[] createTodoRequests = {
                new CreateTodoRequest("todo1"),
                new CreateTodoRequest("todo2")
        };

        for (CreateTodoRequest req : createTodoRequests) {
            todos.add(new Todo(req));
        }

        String content = "[{\"id\":null,\"name\":\"todo1\",\"completed\":false},{\"id\":null,\"name\":\"todo2\",\"completed\":false}]";

        given(todoService.findAll()).willReturn(todos);

        //when
        mockMvc.perform(get("/todos"))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(content))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo 전체 조회 - 빈 배열")
    void findTodosEmpty() throws Exception {
        //given
        given(todoService.findAll()).willReturn(Collections.EMPTY_LIST);

        String content = "[]";

        //when
        mockMvc.perform(get("/todos"))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(content))
                .andDo(print())
                .andReturn();
    }


    @Test
    @DisplayName("todo Update - 조회 결과 없을 때")
    void updateTodoNotExist() throws Exception {
        //given
        Long givenId = 999L;
        UpdateTodoRequest req = new UpdateTodoRequest("change", true);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "change");
        requestBody.put("completed", true);

        given(todoService.update(eq(givenId), any(UpdateTodoRequest.class))).willThrow(new CustomHttpException(ErrorCode.NOT_FOUND));

        //when
        mockMvc.perform(put("/todos/" + givenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                )
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().string(notFoundContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo update - PathVariable 타입이 맞지 않을 경우")
    void updateTodoMissMatch() throws Exception {
        //given
        String givenId = "foobar";

        //when
        mockMvc.perform(put("/todos/" + givenId))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(badRequestContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo remove - RequestBody 없음")
    void updateTodoWithOutRequestBody() throws Exception {
        //given
        Long givenId = 1L;

        //when
        mockMvc.perform(put("/todos/" + givenId))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(badRequestContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo Update")
    void updateTodo() throws Exception {
        //given
        Long givenId = 1L;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "hello");
        requestBody.put("completed", false);

        given(todoService.update(eq(givenId), any(UpdateTodoRequest.class))).willReturn(new Todo(new CreateTodoRequest("hello")));

        //when
        mockMvc.perform(put("/todos/" + givenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                )
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(todoResponseContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo remove - 조회 결과 없을 때")
    void removeTodoNotExist() throws Exception {
        //given
        Long givenId = 999L;

        doThrow(new CustomHttpException(ErrorCode.NOT_FOUND)).when(todoService).removeById(givenId);

        //when
        mockMvc.perform((delete("/todos/" + givenId)))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().string(notFoundContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo remove - PathVariable 타입이 맞지 않을 경우")
    void removeTodoMissMatch() throws Exception {
        //given
        String givenId = "foobar";

        //when
        mockMvc.perform(delete("/todos/" + givenId))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(badRequestContent))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("todo remove")
    void removeTodo() throws Exception {
        //given
        Long givenId = 1L;

        doNothing().when(todoService).removeById(givenId);

        //when
        mockMvc.perform(delete("/todos/" + givenId))
                //then
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }
}