package btt_telecom.api.external;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class getConnection {
	private String url;
	private String user;
	private String password;
	private String sql;
	
	public getConnection() {
		url = "jdbc:oracle:thin:@192.168.15.6:1521/imagem";
		user = "B2TTELECOM_DB";
		password = "b2tcom#22";
		sql = "select a.NOMFUN, a.NUMCPF, a.NUMCAD, b.NOMEXB, c.DATCRE, e.TIPLGR, e.ENDRUA, e.ENDNUM, e.ENDCPL, g.NOMBAI, f.NOMCID, f.ESTCID, e.ENDCEP from\r\n"
				+ "RUBI.R034FUN a, RUBI.R910ENT b, RUBI.R910USU c, RUBI.R034USU d, RUBI.R034CPL e, RUBI.R074CID f, RUBI.R074BAI g where\r\n"
				+ "d.NUMEMP = a.NUMEMP and\r\n"
				+ "a.NUMEMP = e.NUMEMP and\r\n"
				+ "a.NUMCAD = d.NUMCAD and\r\n"
				+ "a.NUMCAD = e.NUMCAD and\r\n"
				+ "d.CODUSU = b.CODENT and\r\n"
				+ "c.CODENT = b.CODENT and\r\n"
				+ "f.CODCID = e.CODCID and\r\n"
				+ "f.CODCID = g.CODCID and\r\n"
				+ "g.CODBAI = e.CODBAI and\r\n"
				+ "f.CODEST = e.CODEST and\r\n"
				+ "a.SITAFA = '1'and\r\n"
				+ "(a.NUMEMP = '3' or a.NUMEMP= '4') and\r\n"
				+ "a.TIPCOL= '1'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			Statement stmt = con.createStatement();  
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
			}
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
