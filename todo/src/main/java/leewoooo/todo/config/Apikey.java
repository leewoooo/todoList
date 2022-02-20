package leewoooo.todo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Apikey {
    @Value("${api-key}")
    private String apikey;
}
