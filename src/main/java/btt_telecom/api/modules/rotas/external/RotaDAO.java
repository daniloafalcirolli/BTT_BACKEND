package btt_telecom.api.modules.rotas.external;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import btt_telecom.api.config.external.ConnectionDB;
import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.modules.rotas.dto.RotaDTO;

public class RotaDAO extends AbstractMethods{
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private RotaDTO rota;
	
	public List<RotaDTO> getRotas(String data_inicio, String data_final, Long id_provedor, String nome_cidade, String cpf_funcionario) throws SQLException{
		String query = ""
				+ " SELECT * FROM ROTAS r";
		
		if(!cpf_funcionario.equals("")) {
			query += " WHERE r.CPF_FUNCIONARIO = '" + cpf_funcionario + "' AND";

		} else if(!nome_cidade.equals("")) {
			query += " WHERE r.NOME_CIDADE = '" + nome_cidade + "' AND";
		} 

		if(!data_inicio.equals("") && !data_final.equals("")) {
			query += " r.\"DATA\" >= TO_DATE('" + data_inicio + "', 'yyyy-MM-dd') AND ";
			query += " r.\"DATA\" <= TO_DATE('" + data_final + "', 'yyyy-MM-dd') AND";
		} 
		
		if(query.endsWith("AND")) {
			query = replaceLast("AND", "", query);
		}
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		try {
			List<RotaDTO> result = new ArrayList<>();
			
			while(rs.next()) {
				rota = new RotaDTO();
				rota.setId(rs.getLong("ID"));
				rota.setCpf_funcionario(rs.getString("CPF_FUNCIONARIO"));
				rota.setLatitude(rs.getString("LATITUDE"));
				rota.setLongitude(rs.getString("LONGITUDE"));
				rota.setId_cidade(rs.getLong("ID_CIDADE"));
				rota.setNome_cidade(rs.getString("NOME_CIDADE"));
				rota.setConsumo(rs.getString("CONSUMO"));
				rota.setGasolina(rs.getString("GASOLINA"));
				rota.setData(rs.getDate("DATA"));
				result.add(rota);
			}
			
			return result;
		} finally {
			con.close();
		}
	}
}
