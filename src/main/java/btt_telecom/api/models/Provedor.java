package btt_telecom.api.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import btt_telecom.api.dto.ProvedorDTO;

@Entity
public class Provedor {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ServicoProvedor> servicos;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<CamposProvedor> campos;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Material> materiais;
	
	public Provedor(){
		
	}

	public Provedor(ProvedorDTO provedorDTO) {
		this.id = provedorDTO.getId();
		this.name = provedorDTO.getName();
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

	public List<ServicoProvedor> getServicos() {
		return servicos;
	}

	public void setServicos(List<ServicoProvedor> servicos) {
		this.servicos = servicos;
	}

	public List<CamposProvedor> getCampos() {
		return campos;
	}

	public void setCampos(List<CamposProvedor> campos) {
		this.campos = campos;
	}

	public List<Material> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<Material> materiais) {
		this.materiais = materiais;
	}
}
