package btt_telecom.api.modules.funcionario.external;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.external.ConnectionDB;
import btt_telecom.api.modules.funcionario.dto.FuncionarioRubi;
import btt_telecom.api.modules.funcionario.dto.FuncionarioRubiList;

public class FuncionarioDAO extends AbstractMethods{

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private FuncionarioRubiList func;
	
	public List<FuncionarioRubiList> findAll() throws SQLException{
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
		
		List<FuncionarioRubiList> result = new ArrayList<>();
		while(rs.next()) {
			func = new FuncionarioRubiList();
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
				return false;
			}else {
				return true;
			}
		} finally {
			con.close();
		}
	}
	
	public FuncionarioRubi findByCpf(String cpf) throws SQLException, JSONException {
		String query = ""
				+ " SELECT "
				+ "	vr.RAZSOC,"
				+ "	vr.NOMFUN,"
				+ "	vr.NOMEXB,"
				+ "	vr.SITAFA,"
				+ "	vr.NUMCPF,"
				+ "	vr.NUMCID,"
				+ "	vr.NUMPIS,"
				+ "	vr.TIPLGR,"
				+ "	vr.ENDRUA,"
				+ "	vr.ENDNUM,"
				+ "	vr.NOMBAI,"
				+ "	vr.NOMCID,"
				+ "	vr.ESTCID,"
				+ "	vr.ENDCEP,"
				+ "	vr.NUMTEL,"
				+ " f2.PLACA,"
				+ "	(CASE"
				+ "		WHEN c2.PRECO_GASOLINA IS NULL THEN (SELECT m.META_VALUE FROM B2TTELECOM_DB.META m WHERE m.META_KEY = 'preco_gasolina_padrao')"
				+ "		WHEN c2.PRECO_GASOLINA IS NOT NULL THEN c2.PRECO_GASOLINA "
				+ "		END"
				+ "	) AS PRECO_GASOLINA,"
				+ "	(CASE "
				+ "		WHEN f2.KILOMETRAGEM_POR_LITRO IS NULL THEN (SELECT m.META_VALUE FROM B2TTELECOM_DB.META m WHERE m.META_KEY = 'consumo_padrao')"
				+ "		WHEN f2.KILOMETRAGEM_POR_LITRO IS NOT NULL THEN f2.KILOMETRAGEM_POR_LITRO"
				+ "		END"
				+ "	) AS CONSUMO"
				+ " FROM ("
				+ "	SELECT "
				+ "	DISTINCT"
				+ "		a.NUMEMP, h.RAZSOC, a.NOMFUN, a.SITAFA, "
				+ "		LPAD(a.NUMCPF, 11, '0') AS NUMCPF, a.NUMPIS, e.NUMCID, a.NUMCAD, "
				+ "		b.NOMEXB, c.DATCRE, e.TIPLGR, "
				+ "		e.ENDRUA, e.ENDNUM, e.ENDCPL, "
				+ "		'('|| e.DDDTEL || ') ' || e.NUMTEL AS NUMTEL, g.NOMBAI, "
				+ "		f.NOMCID, f.ESTCID, e.ENDCEP "
				+ "		FROM"
				+ "			RUBI.R034FUN a, "
				+ "			RUBI.R910ENT b, "
				+ "			RUBI.R910USU c, "
				+ "			RUBI.R034USU d, "
				+ "			RUBI.R034CPL e, "
				+ "			RUBI.R074CID f, "
				+ "			RUBI.R074BAI g,"
				+ "			RUBI.R030FIL h"
				+ "		WHERE"
				+ "			a.NUMEMP = h.NUMEMP and"
				+ "			d.NUMEMP = a.NUMEMP and"
				+ "			a.NUMEMP = e.NUMEMP and"
				+ "					a.NUMCAD = d.NUMCAD and"
				+ "					a.NUMCAD = e.NUMCAD and"
				+ "					d.CODUSU = b.CODENT and"
				+ "					c.CODENT = b.CODENT and"
				+ "					f.CODCID = e.CODCID and"
				+ "					f.CODCID = g.CODCID and"
				+ "					g.CODBAI = e.CODBAI and"
				+ "					f.CODEST = e.CODEST and"
				+ "					(a.NUMEMP = '3' or a.NUMEMP= '4') and"
				+ "					a.NUMCPF = '" + cpf + "' and"
				+ "					a.TIPCOL= '1') vr"
				+ "	LEFT JOIN B2TTELECOM_DB.CIDADES c2 ON"
				+ "		(UPPER(c2.CIDADE) = UPPER(NOMCID))"
				+ "	LEFT JOIN B2TTELECOM_DB.FUNCIONARIOS f2 ON"
				+ "		(f2.CPF = vr.NUMCPF)"
				+ "	ORDER BY NOMFUN";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		FuncionarioRubi funcionario = new FuncionarioRubi(); 
		
		try {
			if(rs.next()) {
				funcionario.setNome(rs.getString("NOMFUN"));
				funcionario.setUsername(rs.getString("NOMEXB"));
				funcionario.setEmpresa(rs.getString("RAZSOC"));
				funcionario.setCpf(rs.getString("NUMCPF"));
				funcionario.setPis(rs.getString("NUMPIS"));
				funcionario.setPreco_gasolina(rs.getString("PRECO_GASOLINA"));
				funcionario.setConsumo(rs.getString("CONSUMO"));
				funcionario.setPlaca(rs.getString("PLACA"));

				String formattedAddress = getFormattedAddress(
						rs.getString("TIPLGR"), 
						rs.getString("ENDRUA"), 
						rs.getString("ENDNUM"),
						rs.getString("NOMBAI"),
						rs.getString("NOMCID"),
						rs.getString("ESTCID"),
						rs.getString("ENDCEP"));
				JSONObject cords = getLatAndLng(formattedAddress);

				funcionario.setEndereco(formattedAddress);
				funcionario.setLatitude(cords.getString("lat"));
				funcionario.setLongitude(cords.getString("lng"));
			}
			
			return funcionario;
		} finally {
			con.close();
		}
	}
	
	public FuncionarioRubi getConsumos(String cpf) throws SQLException {
		String query = ""
				+ " SELECT "
				+ "	vr.RAZSOC,"
				+ "	vr.NOMFUN,"
				+ "	vr.NOMEXB,"
				+ "	vr.SITAFA,"
				+ "	vr.NUMCPF,"
				+ "	vr.NUMCID,"
				+ "	vr.NUMPIS,"
				+ "	vr.TIPLGR,"
				+ "	vr.ENDRUA,"
				+ "	vr.ENDNUM,"
				+ "	vr.NOMBAI,"
				+ "	vr.NOMCID,"
				+ "	vr.ESTCID,"
				+ "	vr.ENDCEP,"
				+ "	vr.NUMTEL,"
				+ " f2.PLACA,"
				+ "	(CASE"
				+ "		WHEN c2.PRECO_GASOLINA IS NULL THEN (SELECT m.META_VALUE FROM B2TTELECOM_DB.META m WHERE m.META_KEY = 'preco_gasolina_padrao')"
				+ "		WHEN c2.PRECO_GASOLINA IS NOT NULL THEN c2.PRECO_GASOLINA "
				+ "		END"
				+ "	) AS PRECO_GASOLINA,"
				+ "	(CASE "
				+ "		WHEN f2.KILOMETRAGEM_POR_LITRO IS NULL THEN (SELECT m.META_VALUE FROM B2TTELECOM_DB.META m WHERE m.META_KEY = 'consumo_padrao')"
				+ "		WHEN f2.KILOMETRAGEM_POR_LITRO IS NOT NULL THEN f2.KILOMETRAGEM_POR_LITRO"
				+ "		END"
				+ "	) AS CONSUMO"
				+ " FROM ("
				+ "	SELECT "
				+ "	DISTINCT"
				+ "		a.NUMEMP, h.RAZSOC, a.NOMFUN, a.SITAFA, "
				+ "		LPAD(a.NUMCPF, 11, '0') AS NUMCPF, a.NUMPIS, e.NUMCID, a.NUMCAD, "
				+ "		b.NOMEXB, c.DATCRE, e.TIPLGR, "
				+ "		e.ENDRUA, e.ENDNUM, e.ENDCPL, "
				+ "		'('|| e.DDDTEL || ') ' || e.NUMTEL AS NUMTEL, g.NOMBAI, "
				+ "		f.NOMCID, f.ESTCID, e.ENDCEP "
				+ "		FROM"
				+ "			RUBI.R034FUN a, "
				+ "			RUBI.R910ENT b, "
				+ "			RUBI.R910USU c, "
				+ "			RUBI.R034USU d, "
				+ "			RUBI.R034CPL e, "
				+ "			RUBI.R074CID f, "
				+ "			RUBI.R074BAI g,"
				+ "			RUBI.R030FIL h"
				+ "		WHERE"
				+ "			a.NUMEMP = h.NUMEMP and"
				+ "			d.NUMEMP = a.NUMEMP and"
				+ "			a.NUMEMP = e.NUMEMP and"
				+ "					a.NUMCAD = d.NUMCAD and"
				+ "					a.NUMCAD = e.NUMCAD and"
				+ "					d.CODUSU = b.CODENT and"
				+ "					c.CODENT = b.CODENT and"
				+ "					f.CODCID = e.CODCID and"
				+ "					f.CODCID = g.CODCID and"
				+ "					g.CODBAI = e.CODBAI and"
				+ "					f.CODEST = e.CODEST and"
				+ "					(a.NUMEMP = '3' or a.NUMEMP= '4') and"
				+ "					a.NUMCPF = '" + cpf + "' and"
				+ "					a.TIPCOL= '1') vr"
				+ "	LEFT JOIN B2TTELECOM_DB.CIDADES c2 ON"
				+ "		(UPPER(c2.CIDADE) = UPPER(NOMCID))"
				+ "	LEFT JOIN B2TTELECOM_DB.FUNCIONARIOS f2 ON"
				+ "		(f2.CPF = vr.NUMCPF)"
				+ "	ORDER BY NOMFUN";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		FuncionarioRubi funcionario = new FuncionarioRubi(); 
		
		try {
			if(rs.next()) {
				funcionario.setPreco_gasolina(rs.getString("PRECO_GASOLINA"));
				funcionario.setConsumo(rs.getString("CONSUMO"));
			}
			
			return funcionario;
		} finally {
			con.close();
		}
	}
}
