package btt_telecom.api.config.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends Exception{
	private static final long serialVersionUID = 1L;
	private ExceptionModel error = new ExceptionModel();

	    public ApplicationException(String message){
	        error.setMessage(message);
	        error.setStatus(HttpStatus.BAD_REQUEST);
	    }

	    public ApplicationException(HttpStatus status){
	        error.setMessage("");
	        error.setStatus(status);
	    }

	    public ApplicationException(HttpStatus status, String message){
	        error.setMessage(message);
	        error.setStatus(status);
	    }

	    public ExceptionModel getError() {
	        return error;
	    }

	    public void setError(ExceptionModel error) {
	        this.error = error;
	    }
}
