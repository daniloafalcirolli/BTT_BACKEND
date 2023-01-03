package btt_telecom.api.modules.funcionario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class Funcionario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	private String cpf;
		
	private String placa;
	
	private String kilometragem_por_litro;

	private String password;
	
	public Funcionario() {
		
	}
	
	public Funcionario(Long id, String cpf, String placa, String kilometragem_por_litro) {
		this.id = id;
		this.cpf = cpf;
		this.placa = placa;
		this.kilometragem_por_litro = kilometragem_por_litro;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getKilometragem_por_litro() {
		return kilometragem_por_litro;
	}

	public void setKilometragem_por_litro(String kilometragem_por_litro) {
		this.kilometragem_por_litro = kilometragem_por_litro;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean hasPassword() {
		if(this.password != null && !this.password.equals("")) {
			return true;
		}
		return false;
	}
}
