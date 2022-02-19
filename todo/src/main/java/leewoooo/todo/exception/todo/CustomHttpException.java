package leewoooo.todo.exception.todo;

import leewoooo.todo.dto.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomHttpException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomHttpException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
