package btt_telecom.api.modules.funcionario.dto;

public class FuncionarioRubi {
	private String nome;
	
	private String username;
	
	private String cpf;
	
	private String empresa;
	
	private String consumo;
	
	private String preco_gasolina;

	private String latitude;
	
	private String longitude;
	
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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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
}
