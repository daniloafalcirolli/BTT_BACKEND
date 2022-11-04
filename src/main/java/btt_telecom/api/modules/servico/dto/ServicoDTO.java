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
	
	private String status;
		
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
		if(servico.getHora_finalizacao() != (null)) {
			this.hora_finalizacao = servico.getHora_finalizacao().toString();
		}
	}

	public Long getId() {
		return id;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public String getEmpresa() {
		return empresa;
	}

	public String getCliente() {
		return cliente;
	}

	public String getContrato() {
		return contrato;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public String getProvedor() {
		return provedor;
	}

	public String getStatus() {
		return status;
	}

	public String getHora_finalizacao() {
		return hora_finalizacao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setHora_finalizacao(String hora_finalizacao) {
		this.hora_finalizacao = hora_finalizacao;
	}
}
