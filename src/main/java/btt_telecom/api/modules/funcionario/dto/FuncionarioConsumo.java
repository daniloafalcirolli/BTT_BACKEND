package btt_telecom.api.modules.funcionario.dto;

public class FuncionarioConsumo {
	private String nome;
	
	private String username;
	
	private String cpf;
	
	private String pis;
	
	private String empresa;

	private String placa; 

	private String consumo;
	
	private String preco_gasolina;
	
	private Long id_cidade;

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

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getConsumo() {
		return consumo;
	}

	public void setConsumo(String consumo) {
		this.consumo = consumo;
	}

	public String getPreco_gasolina() {
		return preco_gasolina;
	}

	public void setPreco_gasolina(String preco_gasolina) {
		this.preco_gasolina = preco_gasolina;
	}

	public Long getId_cidade() {
		return id_cidade;
	}

	public void setId_cidade(Long id_cidade) {
		this.id_cidade = id_cidade;
	}
}
