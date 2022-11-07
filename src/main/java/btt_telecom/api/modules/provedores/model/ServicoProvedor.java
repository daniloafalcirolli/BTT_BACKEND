package btt_telecom.api.modules.provedores.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ServicoProvedor {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
		
	private String servico;
	
	private String id_senior;
	
	private String identificador;
	
	@OneToOne
	private CategoriaServicoProvedor categoria;
	
	public ServicoProvedor(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getId_senior() {
		return id_senior;
	}

	public void setId_senior(String id_senior) {
		this.id_senior = id_senior;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public CategoriaServicoProvedor getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaServicoProvedor categoria) {
		this.categoria = categoria;
	}
}
