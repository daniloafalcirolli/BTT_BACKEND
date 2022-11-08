package btt_telecom.api.dto;

import java.util.Date;

import btt_telecom.api.models.Rota;

public class RotaDTO {
	private Long id;
	
	private Date data;
	
	private Date hora;
	
	private String latitude;
	
	private String longitude;
	
	private String descricao;
	
	private String gasolina;
	
	private String consumo;
	
	private Long id_cidade;
	
	private Long id_funcionario;
	
	public RotaDTO(Rota rota) {
		this.id = rota.getId();
		this.data = rota.getData();
		this.hora = rota.getHora();
		this.latitude = rota.getLatitude();
		this.longitude = rota.getLongitude();
		this.descricao = rota.getDescricao();
		this.gasolina = rota.getGasolina();
		this.consumo = rota.getConsumo();
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

	public Long getId_cidade() {
		return id_cidade;
	}

	public void setId_cidade(Long id_cidade) {
		this.id_cidade = id_cidade;
	}

	public Long getId_funcionario() {
		return id_funcionario;
	}

	public void setId_funcionario(Long id_funcionario) {
		this.id_funcionario = id_funcionario;
	}
}
