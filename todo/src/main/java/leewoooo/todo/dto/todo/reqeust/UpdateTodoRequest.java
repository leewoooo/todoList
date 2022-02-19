package leewoooo.todo.dto.todo.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateTodoRequest {
    private String name;

    private Boolean completed;
}
