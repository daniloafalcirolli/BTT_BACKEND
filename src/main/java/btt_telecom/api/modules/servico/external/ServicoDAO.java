package btt_telecom.api.modules.servico.external;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.config.external.ConnectionDB;
import btt_telecom.api.modules.servico.dto.ServicoRubi;

public class ServicoDAO extends AbstractMethods{
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ServicoRubi servico;
	
	public List<ServicoRubi> getServicos(String data_inicio, String data_final, String cpf_funcionario, String id_provedor, String status) throws SQLException{
		String query = ""
				+ "SELECT "
				+ " DISTINCT"
				+ "	rubi_func.NOMFUN AS FUNCIONARIO,"
				+ "	rubi_emp.RAZSOC,"
				+ " b2ts.ID,"
				+ "	b2tc.NOME AS CLIENTE,"
				+ "	b2tc.CONTRATO,"
				+ "	b2ts.PROTOCOLO,"
				+ "	b2tp.NAME AS PROVEDOR,"
				+ "	b2tsp.SERVICO AS SERVICO,"
				+ "	b2ts.STATUS,"
				+ "	b2ts.\"DATA\" AS DATA_INICIO,"
				+ "	b2ts.HORA_FINALIZACAO AS HORA_FINALIZACAO"
				+ "	FROM B2TTELECOM_DB.SERVICO b2ts"
				+ "	INNER JOIN RUBI.R034FUN rubi_func ON "
				+ "		(rubi_func.NUMCPF = b2ts.CPF_FUNCIONARIO)"
				+ "	INNER JOIN RUBI.R030FIL rubi_emp ON "
				+ "		(rubi_emp.NUMEMP = rubi_func.NUMEMP)"
				+ "	INNER JOIN B2TTELECOM_DB.CLIENTE b2tc ON"
				+ "		(b2tc.ID = b2ts.CLIENTE_ID)"
				+ "	INNER JOIN B2TTELECOM_DB.PROVEDOR b2tp ON "
				+ "		(b2tp.ID = b2ts.PROVEDOR_ID)"
				+ "	INNER JOIN B2TTELECOM_DB.SERVICO_PROVEDOR b2tsp ON"
				+ "		(b2tsp.ID = b2ts.SERVICO_PROVEDOR_ID)";
		
				if(!data_inicio.equals("") || !data_final.equals("") || !cpf_funcionario.equals("") || !status.equals("") || !status.equals("")) {
					query += " WHERE ";
					
					if(!data_inicio.equals("") && !data_final.equals("")) {
						query += " b2ts.\"DATA\" >= TO_DATE('" + data_inicio + "', 'yyyy-MM-dd') AND ";
						query += " b2ts.\"DATA\" <= TO_DATE('" + data_final + "', 'yyyy-MM-dd') AND";
					} 
					
					if(!cpf_funcionario.equals("")) {
						query += " b2ts.CPF_FUNCIONARIO = '" + cpf_funcionario + "' AND";
					} 
					
					if(!status.equals("")) {
						query += " b2ts.STATUS = '" + status + "' AND";
					}
					
					if(!id_provedor.equals("")) {
						query += " b2tp.ID = " + id_provedor + " AND";
					}
				}
				
				if(query.endsWith("AND")) {
					query = replaceLast("AND", "", query);
				}
				
				query += " ORDER BY b2ts.\"DATA\" ASC, b2ts.HORA_FINALIZACAO ASC "; 
			
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		List<ServicoRubi> result = new ArrayList<>();
		while(rs.next()) {
			servico = new ServicoRubi();
			servico.setId(rs.getLong("ID"));
			servico.setFuncionario(rs.getString("FUNCIONARIO"));
			servico.setEmpresa(rs.getString("RAZSOC"));
			servico.setCliente(rs.getString("CLIENTE"));
			servico.setContrato(rs.getString("CONTRATO"));
			servico.setProtocolo(rs.getString("PROTOCOLO"));
			servico.setProvedor(rs.getString("PROVEDOR"));
			servico.setServico_provedor(rs.getString("SERVICO"));
			servico.setStatus(rs.getString("STATUS"));
			servico.setData_inicio(rs.getString("DATA_INICIO").split(" ")[0]);
			servico.setHora_finalizacao(rs.getString("HORA_FINALIZACAO") != null ? rs.getString("HORA_FINALIZACAO").split(" ")[1] : null);
			result.add(servico);
		}
		
		con.close();
		return result; 
	}
}
