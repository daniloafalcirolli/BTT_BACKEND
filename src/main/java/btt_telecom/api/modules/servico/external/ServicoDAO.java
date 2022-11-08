package btt_telecom.api.modules.servico.external;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import btt_telecom.api.external.ConnectionDB;
import btt_telecom.api.modules.servico.dto.ServicoRubi;

public class ServicoDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ServicoRubi servico;
	
	public List<ServicoRubi> getServicos(String data_inicio, String data_final, String cpf_funcionario, String status) throws SQLException{
		String query = "";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		List<ServicoRubi> result = new ArrayList<>();
		while(rs.next()) {
			
		}
		
		con.close();
		return result; 
	}
}
