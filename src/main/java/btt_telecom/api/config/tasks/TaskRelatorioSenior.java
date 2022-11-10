package btt_telecom.api.config.tasks;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TaskRelatorioSenior {

	@Scheduled(cron = "0 30 22 * * *")
	public void getPersonsAfterFifty() {
		System.out.println("teste task agendada");
	}

//	private static Connection con;
//	private static PreparedStatement ps;
//	private static ResultSet rs;
//
//	@Scheduled(fixedDelayString = "#{scheduleGetTimer.getTimer()}")
//	public void execute() {
//		System.out.println("chegamos");
//	}
//
//	static class ScheduleGetTimer {
//		public String getTimer() throws SQLException {
//			return setDelay();
//		}
//	}
//
//	@Bean("scheduleGetTimer")
//	public ScheduleGetTimer createScheduleGetTimer() {
//		return new ScheduleGetTimer();
//	}
//
//	public static String setDelay() throws SQLException {
//		String query = "" 
//				+ " SELECT "
//				+ " m.META_KEY," 
//				+ " m.META_VALUE" 
//				+ " FROM B2TTELECOM_DB.META m "
//				+ " WHERE m.META_KEY = 'timer_relatorio_senior'";
//		con = ConnectionDB.getConnection();
//		ps = con.prepareStatement(query);
//		rs = ps.executeQuery();
//
//		try {
//			if (!rs.isBeforeFirst()) {
//				return rs.getString("META_VALUE");
//			} else {
//				return "6000";
//			}
//		} finally {
//			con.close();
//		}
//	}
//
//	public List<ServicoSenior> getServicos() {
//		return null;
//	}
}
