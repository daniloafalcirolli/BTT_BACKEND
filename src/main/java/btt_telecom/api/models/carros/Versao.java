package btt_telecom.api.models.carros;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Versao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;
	
	private String km_por_litro;
	
	public Versao() {
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

	public String getKm_por_litro() {
		return km_por_litro;
	}

	public void setKm_por_litro(String km_por_litro) {
		this.km_por_litro = km_por_litro;
	}
}
