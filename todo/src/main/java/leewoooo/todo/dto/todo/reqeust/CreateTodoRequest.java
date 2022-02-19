package leewoooo.todo.dto.todo.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateTodoRequest {
    @NotEmpty(message = "name should not be empty")
    private String name;
}
