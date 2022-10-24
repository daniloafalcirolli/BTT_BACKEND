package btt_telecom.api.modules.provedores.dto;

import btt_telecom.api.modules.provedores.model.Provedor;

public class ProvedorDTO {
	private Long id;
	
	private String name;
	
	private String id_senior;
	
	private String identificador;
	
	public ProvedorDTO() {
	}

	public ProvedorDTO(Provedor provedor) {
		this.id = provedor.getId();
		this.name = provedor.getName();
		this.id_senior = provedor.getId_senior();
		this.identificador = provedor.getIdentificador();
	}

	public ProvedorDTO(Long id, String name, String identificador, String id_senior) {
		this.id = id;
		this.name = name;
		this.id_senior = id_senior;
		this.identificador = identificador;
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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getId_senior() {
		return id_senior;
	}

	public void setId_senior(String id_senior) {
		this.id_senior = id_senior;
	}
}
