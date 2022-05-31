package btt_telecom.api.dto;

import btt_telecom.api.models.carros.Modelo;

public class ModeloDTO {
	private Long id;
	
	private String modelo;
	
	public ModeloDTO() {
		
	}

	public ModeloDTO(Long id, String modelo) {
		this.id = id;
		this.modelo = modelo;
	}
	
	public ModeloDTO(Modelo modelo) {
		this.id = modelo.getId();
		this.modelo = modelo.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}
