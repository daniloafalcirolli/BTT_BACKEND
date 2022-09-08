package btt_telecom.api.modules.funcionario.dto;

import btt_telecom.api.models.Cidade;
import btt_telecom.api.models.Empresa;
import btt_telecom.api.modules.funcionario.model.Funcionario;

public class FuncionarioDTO {
	private Long id;
	
	private String nome;
		
	private String username;
	
	private String rg;
	
	private String cpf;
	
	private String telefone;
	
	private boolean status;
	
	private String codigo_status;
	
	private Cidade cidade;
	
	private Empresa empresa;
	
	private String endereco;
	
	public FuncionarioDTO() {
		
	}
	public FuncionarioDTO(Long id, String nome, String username, String rg, String cpf, String telefone, boolean status, Cidade cidade, Empresa empresa) {
		this.id = id;
		this.nome = nome;
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
		this.nome = func.getNome();
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

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCodigo_status() {
		return codigo_status;
	}
	public void setCodigo_status(String codigo_status) {
		this.codigo_status = codigo_status;
	}	
}
