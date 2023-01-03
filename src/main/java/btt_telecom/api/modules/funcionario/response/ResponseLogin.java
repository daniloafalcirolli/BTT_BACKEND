package btt_telecom.api.modules.funcionario.response;

import btt_telecom.api.modules.funcionario.dto.FuncionarioRubi;

public class ResponseLogin {
	private Long code;
	
	private String message;
	
	private FuncionarioRubi response;
	
	public ResponseLogin() {
		
	}
	
	public ResponseLogin(Long code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public ResponseLogin(Long code, String message, FuncionarioRubi response) {
		this.code = code;
		this.message = message;
		this.response = response;
	}

	public Long getCode() {
		return code;
	}
	
	public void setCode(Long code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getResponse() {
		return response;
	}
	
	public void setResponse(FuncionarioRubi response) {
		this.response = response;
	}
}
