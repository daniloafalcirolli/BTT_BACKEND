package btt_telecom.api.external;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import btt_telecom.api.models.Cidade;
import btt_telecom.api.models.Empresa;

public class getConnection {
	private String url;
	private String user;
	private String password;
	private String sql;
	
	public getConnection() {

	}
	
//	public ArrayList<FuncionarioDTO> pegarfuncs(){
//		url = "jdbc:oracle:thin:@192.168.15.6:1521/imagem";
//		user = "B2TTELECOM_DB";
//		password = "b2tcom#22";
//
//		sql = "select a.NUMEMP, a.NOMFUN, a.SITAFA, a.NUMCPF, e.NUMCID, a.NUMCAD, b.NOMEXB, c.DATCRE, e.TIPLGR, e.ENDRUA, e.ENDNUM, e.ENDCPL, e.DDDTEL, e.NUMTEL, g.NOMBAI, f.NOMCID, f.ESTCID, e.ENDCEP from\r\n"
//				+ "RUBI.R034FUN a, RUBI.R910ENT b, RUBI.R910USU c, RUBI.R034USU d, RUBI.R034CPL e, RUBI.R074CID f, RUBI.R074BAI g where\r\n"
//				+ "d.NUMEMP = a.NUMEMP and\r\n"
//				+ "a.NUMEMP = e.NUMEMP and\r\n"
//				+ "a.NUMCAD = d.NUMCAD and\r\n"
//				+ "a.NUMCAD = e.NUMCAD and\r\n"
//				+ "d.CODUSU = b.CODENT and\r\n"
//				+ "c.CODENT = b.CODENT and\r\n"
//				+ "f.CODCID = e.CODCID and\r\n"
//				+ "f.CODCID = g.CODCID and\r\n"
//				+ "g.CODBAI = e.CODBAI and\r\n"
//				+ "f.CODEST = e.CODEST and\r\n"
////				+ "a.SITAFA = '1'and\r\n"
//				+ "(a.NUMEMP = '3' or a.NUMEMP= '4') and\r\n"
//				+ "a.TIPCOL= '1' order by a.NOMFUN asc";
//		ArrayList<FuncionarioDTO> funcs = new ArrayList<>();
//		FuncionarioDTO f;
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection con = DriverManager.getConnection(url, user, password);
//			
//			Statement stmt = con.createStatement();  
//			
//			ResultSet rs = stmt.executeQuery(sql);
//			while(rs.next()) {
//				f = new FuncionarioDTO();
//				
//				String cpf = rs.getString("NUMCPF");
//				switch(cpf.length()) {
//					case 9:
//						cpf = "00" + cpf;
//						break;
//					case 10:
//						cpf = "0" + cpf;
//						break;
//				}
//				
//				String endereco = "";
//				String tiplgr = rs.getString("TIPLGR");
//				switch(tiplgr) {
//					case "R":
//						endereco += "RUA ";
//						break;
//					case "AV":
//						endereco += "AVENIDA ";
//						break;
//					case "EST":
//						endereco += "ESTRADA ";
//						break;
//				}
//				
//				endereco += rs.getString("ENDRUA") + ", " + rs.getString("ENDNUM") + " " + rs.getString("NOMBAI") + ", " + rs.getString("NOMCID") + " - " + rs.getString("ESTCID");
//				f.setEndereco(endereco);
//
//				if(!rs.getString("NOMFUN").split(" ")[rs.getString("NOMFUN").split(" ").length - 1].equals("TROCA")) funcs.add(f);
//
//				String telefone = "(" + rs.getString("DDDTEL") + ") " + rs.getString("NUMTEL");
//				f.setTelefone(telefone);
//
//				f.setNome(rs.getString("NOMFUN"));
//				f.setUsername(rs.getString("NOMEXB"));
//				f.setRg(rs.getString("NUMCID"));
//				f.setCpf(cpf);
//				f.setCodigo_status(rs.getString("SITAFA"));
//				
//				Cidade c = new Cidade();
//				c.setCidade(rs.getString("NOMCID"));
//				f.setCidade(c);
//	
//				Empresa e = new Empresa();
//				e.setId(Long.parseLong(rs.getString("NUMEMP")));
//				f.setEmpresa(e);
//				
//				f.setStatus(true);
//			}
//			
//			con.close();
//			return funcs;
//		}catch(Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	} 
}
