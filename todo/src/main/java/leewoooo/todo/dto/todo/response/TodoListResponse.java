package leewoooo.todo.dto.todo.response;

import leewoooo.todo.domain.Todo;
import lombok.Getter;

@Getter
public class TodoListResponse {
    private final Long id;
    private final String name;
    private final Boolean completed;

    public TodoListResponse(Todo todo) {
        this.id = todo.getId();
        this.name = todo.getName();
        this.completed = todo.getCompleted();
    }
}
