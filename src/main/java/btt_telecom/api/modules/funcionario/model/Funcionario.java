package btt_telecom.api.modules.funcionario.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import btt_telecom.api.models.Cidade;
import btt_telecom.api.models.Empresa;
import btt_telecom.api.modules.imagens.model.Imagem;

@Entity
public class Funcionario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	private String nome;
	
	private String username;
	
	private String login_provedor;
	
	private String cpf;
	
	private String rg;
	
	private String telefone;
	
	private String endereco;
	
	private String latitude;
	
	private String longitude;
	
	private String placa;
	
	private String kilometragem_por_litro;
	
	private String id_senior;
	
	private String pis;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Imagem imagem;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Cidade cidade;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Empresa empresa;
	
	private boolean status;
	
	public Funcionario() {
		
	}
	
	public Funcionario(Long id, String nome, String username, String cpf, String rg,
			String telefone, String endereco, String placa, String kilometragem_por_litro, String avatar) {
		this.id = id;
		this.nome = nome;
		this.username = username;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
		this.endereco = endereco;
		this.placa = placa;
		this.kilometragem_por_litro = kilometragem_por_litro;
	}

	public String getLogin_provedor() {
		return login_provedor;
	}

	public void setLogin_provedor(String login_provedor) {
		this.login_provedor = login_provedor;
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

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getId_senior() {
		return id_senior;
	}

	public void setId_senior(String id_senior) {
		this.id_senior = id_senior;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}
}
