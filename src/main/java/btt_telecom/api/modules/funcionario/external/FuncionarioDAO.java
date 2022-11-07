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
		String query = "SELECT \r\n"
				+ "DISTINCT\r\n"
				+ "	a.NUMEMP, a.NOMFUN, a.NUMPIS, a.SITAFA, \r\n"
				+ "	LPAD(a.NUMCPF, 11, '0') AS NUMCPF, LPAD(e.NUMCID, 9, '0') AS NUMCID, a.NUMCAD, \r\n"
				+ "	b.NOMEXB, c.DATCRE, e.TIPLGR, \r\n"
				+ "	e.ENDRUA, e.ENDNUM, e.ENDCPL, \r\n"
				+ "	'('|| e.DDDTEL || ') ' || e.NUMTEL AS NUMTEL, g.NOMBAI, \r\n"
				+ "	f.NOMCID, f.ESTCID, e.ENDCEP \r\n"
				+ "	FROM\r\n"
				+ "		RUBI.R034FUN a, \r\n"
				+ "		RUBI.R910ENT b, \r\n"
				+ "		RUBI.R910USU c, \r\n"
				+ "		RUBI.R034USU d, \r\n"
				+ "		RUBI.R034CPL e, \r\n"
				+ "		RUBI.R074CID f, \r\n"
				+ "		RUBI.R074BAI g	\r\n"
				+ "	WHERE\r\n"
				+ "		d.NUMEMP = a.NUMEMP and\r\n"
				+ "		a.NUMEMP = e.NUMEMP and\r\n"
				+ "				a.NUMCAD = d.NUMCAD and\r\n"
				+ "				a.NUMCAD = e.NUMCAD and\r\n"
				+ "				d.CODUSU = b.CODENT and\r\n"
				+ "				c.CODENT = b.CODENT and\r\n"
				+ "				f.CODCID = e.CODCID and\r\n"
				+ "				f.CODCID = g.CODCID and\r\n"
				+ "				g.CODBAI = e.CODBAI and\r\n"
				+ "				f.CODEST = e.CODEST and\r\n"
				+ "				(a.NUMEMP = '3' or a.NUMEMP= '4') and\r\n"
				+ "				a.TIPCOL= '1' order by a.NOMFUN ASC";
		
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
			func.setPis(rs.getString("NUMPIS"));
			result.add(func);
		}

		con.close();
		return result;
	}
}
