package btt_telecom.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Funcionario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	private String primeiro_nome;
	
	private String ultimo_nome;
	
	private String username;
	
	private String cpf;
	
	private String rg;
	
	private String telefone;
	
	private String endereco;
	
	private String latitude;
	
	private String longitude;
	
	private String placa;
	
	private String kilometragem_por_litro;
	
	private String avatar;
	
	private boolean status;
	
	public Funcionario() {
		
	}
	
	public Funcionario(Long id, String primeiro_nome, String ultimo_nome, String username, String cpf, String rg,
			String telefone, String endereco, String placa, String kilometragem_por_litro, String avatar) {
		this.id = id;
		this.primeiro_nome = primeiro_nome;
		this.ultimo_nome = ultimo_nome;
		this.username = username;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
		this.endereco = endereco;
		this.placa = placa;
		this.kilometragem_por_litro = kilometragem_por_litro;
		this.avatar = avatar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimeiro_nome() {
		return primeiro_nome;
	}

	public void setPrimeiro_nome(String primeiro_nome) {
		this.primeiro_nome = primeiro_nome;
	}

	public String getUltimo_nome() {
		return ultimo_nome;
	}

	public void setUltimo_nome(String ultimo_nome) {
		this.ultimo_nome = ultimo_nome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
