package btt_telecom.api.modules.servico.dto;

import btt_telecom.api.modules.servico.model.Servico;

public class ServicoDTO {
	private Long id;
	
	private String funcionario;
	
	private String empresa;
	
	private String cliente;
	
	private String contrato;
	
	private String protocolo;

	private String provedor;	

	private String servico;
	
	private String status;
	
	private String data;
		
	private String hora_finalizacao;

	public ServicoDTO(Servico servico) {
		this.id = servico.getId();
		this.funcionario = servico.getFuncionario().getNome();
		this.empresa = servico.getFuncionario().getEmpresa().getEmpresa();
		this.cliente = servico.getCliente().getNome();
		this.contrato = servico.getCliente().getContrato();
		this.protocolo = servico.getProtocolo();
		this.status = servico.getStatus();
		this.provedor = servico.getProvedor().getName();
		this.servico = servico.getServicoProvedor().getServico();
		this.data = servico.getData().toString();
		if(servico.getHora_finalizacao() != (null)) {
			this.hora_finalizacao = servico.getHora_finalizacao().toString();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getProvedor() {
		return provedor;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora_finalizacao() {
		return hora_finalizacao;
	}

	public void setHora_finalizacao(String hora_finalizacao) {
		this.hora_finalizacao = hora_finalizacao;
	}
}
