package btt_telecom.api.dto;

import btt_telecom.api.models.carros.Ano;

public class AnoDTO {
	private Long id;
	
	private String ano;

	public AnoDTO() {
		
	}
	
	public AnoDTO(Long id, String ano) {
		this.id = id;
		this.ano = ano;
	}
	
	public AnoDTO(Ano ano) {
		this.id = ano.getId();
		this.ano = ano.getAno();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
}
