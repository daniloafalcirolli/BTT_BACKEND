package btt_telecom.api.config.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import btt_telecom.api.config.external.ConnectionDB;
import btt_telecom.api.modules.servico.dto.ServicoSenior;

@Component
@Configuration
@EnableScheduling
public class TaskRelatorioSenior {

	private static Connection con;
	private static PreparedStatement ps;
	private static ResultSet rs;

	@Scheduled(fixedDelayString = "#{scheduleGetTimer.getTimer()}")
	public void execute() {
		System.out.println("chegamos");
	}

	static class ScheduleGetTimer {
		public String getTimer() throws SQLException {
			return setDelay();
		}
	}

	@Bean("scheduleGetTimer")
	public ScheduleGetTimer createScheduleGetTimer() {
		return new ScheduleGetTimer();
	}

	public static String setDelay() throws SQLException {
		String query = "" 
				+ " SELECT "
				+ " m.META_KEY," 
				+ " m.META_VALUE" 
				+ " FROM B2TTELECOM_DB.META m "
				+ " WHERE m.META_KEY = 'timer_relatorio_senior'";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();

		try {
			if (!rs.isBeforeFirst()) {
				return rs.getString("META_VALUE");
			} else {
				return "6000";
			}
		} finally {
			con.close();
		}
	}

	public List<ServicoSenior> getServicos() {
		return null;
	}
}
