package leewoooo.todo.domain;

import leewoooo.todo.domain.common.TimeStamp;
import leewoooo.todo.dto.todo.reqeust.CreateTodoRequest;
import leewoooo.todo.dto.todo.reqeust.UpdateTodoRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = false)
    private String name;

    private LocalDateTime completedAt;

    public Todo(CreateTodoRequest req) {
        this.name = req.getName();
        this.completed = false;
    }

    public void updateTodo(UpdateTodoRequest req) {
        this.name = StringUtils.hasText(req.getName()) ? req.getName() : this.getName();
        this.completed = req.getCompleted() == null ? this.completed : req.getCompleted();
        this.completedAt = this.getCompleted() ? LocalDateTime.now() : null;
    }
}
