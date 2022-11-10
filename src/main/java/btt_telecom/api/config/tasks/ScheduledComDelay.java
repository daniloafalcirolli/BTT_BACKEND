package btt_telecom.api.config.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledComDelay {

	@Scheduled(fixedDelay = 1000)
	public void execute() {
		System.out.println("chegamos");
	}
}
