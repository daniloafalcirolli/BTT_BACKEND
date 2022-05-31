package btt_telecom.api.dto;

import btt_telecom.api.models.Provedor;

public class ProvedorDTO {
	private Long id;
	
	private String name;
	
	public ProvedorDTO() {
	}

	public ProvedorDTO(Provedor provedor) {
		this.id = provedor.getId();
		this.name = provedor.getName();
	}

	public ProvedorDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
