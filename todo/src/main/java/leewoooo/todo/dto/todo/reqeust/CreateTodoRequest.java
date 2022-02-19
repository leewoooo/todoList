package leewoooo.todo.dto.todo.reqeust;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateTodoRequest {
    @NotEmpty(message = "name should not be empty")
    private String name;
}
