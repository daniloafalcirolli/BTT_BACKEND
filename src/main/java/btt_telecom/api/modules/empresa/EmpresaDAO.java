package btt_telecom.api.modules.empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import btt_telecom.api.config.external.ConnectionDB;

public class EmpresaDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private EmpresaRubi empresa;
	
	public List<EmpresaRubi> findAll() throws SQLException{
		String query = " SELECT x.RAZSOC, x.NOMFIL FROM RUBI.R030FIL x ORDER BY x.RAZSOC";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		List<EmpresaRubi> result = new ArrayList<>();
		while(rs.next()) {
			empresa = new EmpresaRubi();
			empresa.setNome(rs.getString("NOMFIL"));
			empresa.setRazao_social(rs.getString("RAZSOC"));
			result.add(empresa);
		}

		con.close();
		return result;
	}
	
	public List<EmpresaRubi> search(String value) throws SQLException{
		String query = " "
				+ " SELECT "
				+ "	x.RAZSOC, "
				+ "	x.NOMFIL "
				+ " FROM RUBI.R030FIL x "
				+ "	WHERE "
				+ " UPPER(x.RAZSOC) LIKE '%" + value.toUpperCase() + "%' OR"
				+ " UPPER(x.NOMFIL) LIKE '%" + value.toUpperCase() + "%'"
				+ "	ORDER BY x.RAZSOC";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		List<EmpresaRubi> result = new ArrayList<>();
		while(rs.next()) {
			empresa = new EmpresaRubi();
			empresa.setNome(rs.getString("NOMFIL"));
			empresa.setRazao_social(rs.getString("RAZSOC"));
			result.add(empresa);
		}

		con.close();
		return result;
	}
}
