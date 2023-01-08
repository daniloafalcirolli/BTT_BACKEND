package btt_telecom.api.config.exception;

import org.springframework.http.HttpStatus;

public class ExceptionModel {

    private String message;

    private HttpStatus status;

    private int code;

    public ExceptionModel() { }

    public ExceptionModel(String message){
        this.message = message;
    }

    public ExceptionModel(HttpStatus status){
        this.status = status;
        this.code = status.value();
    }

    public ExceptionModel(HttpStatus status, String message){
        this.status = status;
        this.code = status.value();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
        this.code = status.value();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
