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
				+ "	x.DESSIT, "
				+ "	x.DESABR "
				+ "	FROM RUBI.R010SIT x "
				+ "	WHERE "
				+ "	x.CODSIT NOT IN (SELECT b2tsf.CODIGO FROM B2TTELECOM_DB.STATUS_FUNC b2tsf)";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		List<StatusFunc> result = new ArrayList<>();
		while(rs.next()) {
			status = new StatusFunc();
			status.setCodigo(rs.getLong("CODSIT"));
			status.setDescricao(rs.getString("DESSIT"));
			status.setAbreviatura(rs.getString("DESABR"));

			result.add(status);
		}

		con.close();
		return result;
	}
}
