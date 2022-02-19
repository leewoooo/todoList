package leewoooo.todo.dto.error;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;

    public  ErrorResponse(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
