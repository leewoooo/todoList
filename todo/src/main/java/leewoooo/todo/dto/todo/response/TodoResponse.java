package leewoooo.todo.dto.todo.response;

import leewoooo.todo.domain.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponse {
    private final Long id;
    private final String name;
    private final Boolean completed;
    private final LocalDateTime completedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.name = todo.getName();
        this.completed = todo.getCompleted();
        this.completedAt = todo.getCompletedAt();
        this.createdAt = todo.getCreatedAt();
        this.updatedAt = todo.getUpdatedAt();
    }
}
