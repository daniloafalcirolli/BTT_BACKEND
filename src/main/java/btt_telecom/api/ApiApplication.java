package btt_telecom.api;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import btt_telecom.api.external.FuncionarioComplete;
import btt_telecom.api.external.getConnection;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		
		getConnection c = new getConnection();
	}
	
	
}
