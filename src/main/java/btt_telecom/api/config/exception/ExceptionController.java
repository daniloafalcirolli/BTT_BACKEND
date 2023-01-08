package btt_telecom.api.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<Object> customExceptionHandler(ApplicationException exception){
        return new ResponseEntity<>(exception.getError(), exception.getError().getStatus());
    }
    
}
