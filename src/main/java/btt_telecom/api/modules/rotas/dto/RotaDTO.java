package btt_telecom.api.modules.rotas.dto;

import java.util.Date;

import btt_telecom.api.modules.rotas.model.Rota;

public class RotaDTO {
	private Long id;
	
	private String cpf_funcionario;
	
	private String nome_funcionario;
	
	private Date data;
	
	private Date hora;
	
	private String latitude;
	
	private String longitude;
	
	private String descricao;
	
	private String gasolina;
	
	private String consumo;
	
	private Long id_cidade;
	
	private String nome_cidade;
	
	public RotaDTO() {
	}
	
	public RotaDTO(Rota rota) {
		this.id = rota.getId();
		this.data = rota.getData();
		this.hora = rota.getHora();
		this.latitude = rota.getLatitude();
		this.longitude = rota.getLongitude();
		this.descricao = rota.getDescricao();
		this.gasolina = rota.getGasolina();
		this.consumo = rota.getConsumo();
		this.nome_cidade = rota.getNome_cidade();
		this.id_cidade = rota.getId_cidade();
		this.cpf_funcionario = rota.getCpf_funcionario();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getGasolina() {
		return gasolina;
	}

	public void setGasolina(String gasolina) {
		this.gasolina = gasolina;
	}

	public String getConsumo() {
		return consumo;
	}

	public void setConsumo(String consumo) {
		this.consumo = consumo;
	}

	public String getNome_cidade() {
		return nome_cidade;
	}

	public void setNome_cidade(String nome_cidade) {
		this.nome_cidade = nome_cidade;
	}

	public Long getId_cidade() {
		return id_cidade;
	}

	public void setId_cidade(Long id_cidade) {
		this.id_cidade = id_cidade;
	}

	public String getCpf_funcionario() {
		return cpf_funcionario;
	}

	public void setCpf_funcionario(String cpf_funcionario) {
		this.cpf_funcionario = cpf_funcionario;
	}

	public String getNome_funcionario() {
		return nome_funcionario;
	}

	public void setNome_funcionario(String nome_funcionario) {
		this.nome_funcionario = nome_funcionario;
	}
}
