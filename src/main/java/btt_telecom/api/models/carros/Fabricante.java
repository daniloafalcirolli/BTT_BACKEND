package btt_telecom.api.models.carros;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Fabricante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@OneToMany
	private List<Modelo> modelos;
	
	public Fabricante() {
		
	}
	
	public Fabricante(Long id, String nome, List<Modelo> modelos) {
		this.id = id;
		this.nome = nome;
		this.modelos = modelos;
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
	
	public List<Modelo> getModelos() {
		return modelos;
	}
	
	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}
}
