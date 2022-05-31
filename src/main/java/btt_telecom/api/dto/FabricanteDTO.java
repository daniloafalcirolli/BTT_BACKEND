package btt_telecom.api.dto;

import btt_telecom.api.models.carros.Fabricante;

public class FabricanteDTO {
	private Long id;
	
	private String nome;
	
	public FabricanteDTO() {
		
	}
	
	public FabricanteDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public FabricanteDTO(Fabricante fabri) {
		this.id = fabri.getId();
		this.nome = fabri.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
