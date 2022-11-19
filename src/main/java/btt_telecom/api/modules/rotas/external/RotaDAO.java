package btt_telecom.api.modules.rotas.external;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.modules.rotas.dto.RotaDTO;

public class RotaDAO extends AbstractMethods{
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private RotaDTO rota;
	
	public List<RotaDTO> getRotas(String data_inicio, String data_final, Long id_provedor, String nome_cidade, String cpf_funcionario){
		
		return null;
	}
}
