package btt_telecom.api.external;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	private static String dns = "jdbc:oracle:thin:@192.168.15.6:1521/imagem";
	private static String user = "B2TTELECOM_DB";
	private static String password = "b2tcom#22";
	private static Connection con;
	
	public static Connection getConnection() {
		try {
			if(con == null || con.isClosed()) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(dns, user, password);
			}
			return con;
		} catch(SQLException | ClassNotFoundException e) {
			System.out.println("Erro ao conectar ao Rubi: " + e);
			return null;
		}
	}
}
