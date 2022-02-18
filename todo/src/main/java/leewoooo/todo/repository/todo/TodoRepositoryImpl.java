package leewoooo.todo.repository.todo;

import leewoooo.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepositoryImpl extends JpaRepository<Todo, Long>, TodoRepository {
}
