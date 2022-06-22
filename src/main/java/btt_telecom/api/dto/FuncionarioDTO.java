package btt_telecom.api.dto;

import btt_telecom.api.models.Cidade;
import btt_telecom.api.models.Empresa;
import btt_telecom.api.models.Funcionario;

public class FuncionarioDTO {
	private Long id;
	
	private String primeiro_nome;
	
	private String ultimo_nome;
	
	private String username;
	
	private String rg;
	
	private String cpf;
	
	private String telefone;
	
	private boolean status;
	
	private Cidade cidade;
	
	private Empresa empresa;
	
	public FuncionarioDTO(Long id, String primeiro_nome, String ultimo_nome, String username, String rg, String cpf, String telefone, boolean status, Cidade cidade, Empresa empresa) {
		this.id = id;
		this.primeiro_nome = primeiro_nome;
		this.ultimo_nome = ultimo_nome;
		this.username = username;
		this.rg = rg;
		this.cpf = cpf;
		this.telefone = telefone;
		this.status = status;
		this.cidade = cidade;
		this.empresa = empresa;
	}
	
	public FuncionarioDTO(Funcionario func) {
		this.id = func.getId();
		this.primeiro_nome = func.getPrimeiro_nome();
		this.ultimo_nome = func.getUltimo_nome();
		this.username = func.getUsername();
		this.rg = func.getRg();
		this.cpf = func.getCpf();
		this.telefone = func.getTelefone();
		this.status = func.getStatus();
		this.cidade = func.getCidade();
		this.empresa = func.getEmpresa();
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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
