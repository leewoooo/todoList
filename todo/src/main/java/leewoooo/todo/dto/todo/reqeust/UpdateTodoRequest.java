package leewoooo.todo.dto.todo.reqeust;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateTodoRequest {
    private String name;
    private Boolean completed;
}
