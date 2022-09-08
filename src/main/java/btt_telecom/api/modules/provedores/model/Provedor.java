package btt_telecom.api.modules.provedores.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import btt_telecom.api.modules.materiais.model.MaterialAplicadoBase;
import btt_telecom.api.modules.materiais.model.MaterialRetiradoBase;
import btt_telecom.api.modules.provedores.dto.ProvedorDTO;

@Entity
public class Provedor {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	private String name;
	
	private String id_senior;
	
	private String identificador;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_provedor")
	private List<ServicoProvedor> servicos;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<CamposProvedorBase> campos;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<MaterialAplicadoBase> materiais_aplicados;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<MaterialRetiradoBase> materiais_retirados;
	
	public Provedor(){
		
	}

	public Provedor(ProvedorDTO provedorDTO) {
		this.id = provedorDTO.getId();
		this.name = provedorDTO.getName();
		this.identificador = provedorDTO.getIdentificador();
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

	public String getId_senior() {
		return id_senior;
	}

	public void setId_senior(String id_senior) {
		this.id_senior = id_senior;
	}

	public List<ServicoProvedor> getServicos() {
		return servicos;
	}

	public void setServicos(List<ServicoProvedor> servicos) {
		this.servicos = servicos;
	}

	public List<CamposProvedorBase> getCampos() {
		return campos;
	}

	public void setCampos(List<CamposProvedorBase> campos) {
		this.campos = campos;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public List<MaterialAplicadoBase> getMateriais_aplicados() {
		return materiais_aplicados;
	}

	public void setMateriais_aplicados(List<MaterialAplicadoBase> materiais_aplicados) {
		this.materiais_aplicados = materiais_aplicados;
	}

	public List<MaterialRetiradoBase> getMateriais_retirados() {
		return materiais_retirados;
	}

	public void setMateriais_retirados(List<MaterialRetiradoBase> materiais_retirados) {
		this.materiais_retirados = materiais_retirados;
	}
}
