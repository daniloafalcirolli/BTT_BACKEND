package btt_telecom.api.modules.funcionario.external;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import btt_telecom.api.config.external.ConnectionDB;
import btt_telecom.api.modules.funcionario.model.StatusFunc;

public class StatusFuncionarioDAO {
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private StatusFunc status;
	
	public List<StatusFunc> findAll() throws SQLException{
		String query = ""
				+ " SELECT "
				+ "	x.CODSIT, "
				+ " x.DESSIT, "
				+ " x.DESABR "
				+ " FROM RUBI.R010SIT x";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		List<StatusFunc> result = new ArrayList<>();
		while(rs.next()) {
			status = new StatusFunc();
			status.setCodigo(rs.getLong("CODSIT"));
			status.setDescricao(rs.getString("DESSIT"));
			status.setAbreviatura(rs.getString("DESABR"));
		}

		con.close();
		return result;
	}
}
