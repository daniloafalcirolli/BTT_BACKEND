package btt_telecom.api.modules.funcionario.external;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import btt_telecom.api.external.ConnectionDB;
import btt_telecom.api.modules.funcionario.dto.FuncionarioRubi;

public class FuncionarioDAO {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private FuncionarioRubi func;
	
	public List<FuncionarioRubi> findAll() throws SQLException{
		String query = ""
				+ " SELECT "
				+ " DISTINCT "
				+ " a.NOMFUN,"
				+ " a.SITAFA, "
				+ " LPAD(a.NUMCPF, 11, '0') AS NUMCPF, "
				+ " e.NUMCID, "
				+ " b.NOMEXB, "
				+ " '('|| e.DDDTEL || ') ' || e.NUMTEL AS NUMTEL "
				+ " FROM "
				+ "	RUBI.R034FUN a, "
				+ "	RUBI.R910ENT b, "
				+ "	RUBI.R910USU c, "
				+ "	RUBI.R034USU d, "
				+ "	RUBI.R034CPL e, "
				+ "	RUBI.R074CID f, "
				+ "	RUBI.R074BAI g"
				+ "	WHERE"
				+ "		d.NUMEMP = a.NUMEMP and"
				+ "		a.NUMEMP = e.NUMEMP and"
				+ "				a.NUMCAD = d.NUMCAD and"
				+ "				a.NUMCAD = e.NUMCAD and"
				+ "				d.CODUSU = b.CODENT and"
				+ "				c.CODENT = b.CODENT and"
				+ "				f.CODCID = e.CODCID and"
				+ "				f.CODCID = g.CODCID and"
				+ "				g.CODBAI = e.CODBAI and"
				+ "				f.CODEST = e.CODEST and"
				+ "				(a.NUMEMP = '3' or a.NUMEMP= '4') and"
				+ "				a.TIPCOL= '1' "
				+ "				ORDER BY a.NOMFUN ASC";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		List<FuncionarioRubi> result = new ArrayList<>();
		while(rs.next()) {
			func = new FuncionarioRubi();
			func.setNome(rs.getString("NOMFUN"));
			func.setUsername(rs.getString("NOMEXB"));
			func.setCpf(rs.getString("NUMCPF"));
			func.setRg(rs.getString("NUMCID"));
			func.setTelefone(rs.getString("NUMTEL"));
			result.add(func);
		}

		con.close();
		return result;
	}
	
	public boolean existsFuncionarioByCpf(String cpf) throws SQLException {
		String query = ""
				+ " SELECT "
				+ " DISTINCT "
				+ " a.NOMFUN, "
				+ "	b.NOMEXB, "
				+ "	e.NUMCID, "
				+ "	LPAD(a.NUMCPF, 11, '0') AS NUMCPF, "
				+ "	'('|| e.DDDTEL || ') ' || e.NUMTEL AS NUMTEL, "
				+ "	a.SITAFA "
				+ " FROM "
				+ "	RUBI.R034FUN a, "
				+ "	RUBI.R910ENT b, "
				+ "	RUBI.R910USU c, "
				+ "	RUBI.R034USU d, "
				+ "	RUBI.R034CPL e, "
				+ "	RUBI.R074CID f, "
				+ "	RUBI.R074BAI g"
				+ "	WHERE"
				+ "		d.NUMEMP = a.NUMEMP and"
				+ "		a.NUMEMP = e.NUMEMP and"
				+ "				a.NUMCAD = d.NUMCAD and"
				+ "				a.NUMCAD = e.NUMCAD and"
				+ "				d.CODUSU = b.CODENT and"
				+ "				c.CODENT = b.CODENT and"
				+ "				f.CODCID = e.CODCID and"
				+ "				f.CODCID = g.CODCID and"
				+ "				g.CODBAI = e.CODBAI and"
				+ "				f.CODEST = e.CODEST and"
				+ "				(a.NUMEMP = '3' or a.NUMEMP= '4') and"
				+ "				a.TIPCOL = '1' and"
				+ "				a.NUMCPF = '" + cpf + "'"
				+ "				ORDER BY a.NOMFUN ASC";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		try {
			if(!rs.isBeforeFirst()) {
				return true;
			}else {
				return false;
			}
		} finally {
			con.close();
		}
	}
	
	public FuncionarioRubi findByCpf(String cpf) {
		return null;
	}
}
