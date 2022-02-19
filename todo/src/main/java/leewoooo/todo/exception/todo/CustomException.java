package leewoooo.todo.exception.todo;

import leewoooo.todo.dto.error.ErrorCode;
import leewoooo.todo.dto.error.ErrorResponse;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorResponse errorResponse;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorResponse = new ErrorResponse(errorCode);
    }
}
