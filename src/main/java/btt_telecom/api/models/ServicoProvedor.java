package btt_telecom.api.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ServicoProvedor {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	private Long id_prov;
	
	private String servico;
	
	public ServicoProvedor(){
		
	}
	
	public ServicoProvedor(String servico, Long id_prov){
		this.servico = servico;
		this.id_prov = id_prov;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId_prov() {
		return id_prov;
	}

	public void setId_prov(Long id_prov) {
		this.id_prov = id_prov;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicoProvedor other = (ServicoProvedor) obj;
		return Objects.equals(id, other.id);
	}
}
