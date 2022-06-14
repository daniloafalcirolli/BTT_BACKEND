package btt_telecom.api.dto;

import java.util.Date;

import btt_telecom.api.models.Rota;

public class RotaDTO {
	private Long id;
	
	private Date data;
	
	private Date hora;
	
	private String latitude;
	
	private String longitude;
	
	public RotaDTO(Rota rota) {
		this.id = rota.getId();
		this.data = rota.getData();
		this.hora = rota.getHora();
		this.latitude = rota.getLatitude();
		this.longitude = rota.getLongitude();
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
}
