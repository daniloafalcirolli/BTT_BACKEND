package btt_telecom.api.modules.servico.dto;

import btt_telecom.api.modules.servico.model.Servico;

public class ServicoDTO {
	private String cliente_nome;
	
	private String cliente_endereco;
	
	private String cliente_contrato;
	
	private String cliente_cpf;
	
	private String cliente_cnpj;
	
	private String cliente_latitude;
	
	private String cliente_longitude;
	
	private String provedor;
	
	private String provedor_servico;

	public ServicoDTO(Servico servico) {
		this.cliente_nome = servico.getCliente().getNome();
		this.cliente_endereco = servico.getCliente().getEndereco();
		this.cliente_contrato = servico.getCliente().getContrato();
		this.cliente_cpf = servico.getCliente().getCpf();
		this.cliente_cnpj = servico.getCliente().getCnpj();
		this.cliente_latitude = servico.getCliente().getLatitude();
		this.cliente_longitude = servico.getCliente().getLongitude();
		this.provedor = servico.getProvedor().getName();
		this.provedor_servico = servico.getServicoProvedor().getServico();
	}
	
	public String getCliente_nome() {
		return cliente_nome;
	}

	public void setCliente_nome(String cliente_nome) {
		this.cliente_nome = cliente_nome;
	}

	public String getCliente_endereco() {
		return cliente_endereco;
	}

	public void setCliente_endereco(String cliente_endereco) {
		this.cliente_endereco = cliente_endereco;
	}

	public String getCliente_contrato() {
		return cliente_contrato;
	}

	public void setCliente_contrato(String cliente_contrato) {
		this.cliente_contrato = cliente_contrato;
	}

	public String getCliente_cpf() {
		return cliente_cpf;
	}

	public void setCliente_cpf(String cliente_cpf) {
		this.cliente_cpf = cliente_cpf;
	}

	public String getCliente_cnpj() {
		return cliente_cnpj;
	}

	public void setCliente_cnpj(String cliente_cnpj) {
		this.cliente_cnpj = cliente_cnpj;
	}

	public String getCliente_latitude() {
		return cliente_latitude;
	}

	public void setCliente_latitude(String cliente_latitude) {
		this.cliente_latitude = cliente_latitude;
	}

	public String getCliente_longitude() {
		return cliente_longitude;
	}

	public void setCliente_longitude(String cliente_longitude) {
		this.cliente_longitude = cliente_longitude;
	}

	public String getProvedor() {
		return provedor;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}

	public String getProvedor_servico() {
		return provedor_servico;
	}

	public void setProvedor_servico(String provedor_servico) {
		this.provedor_servico = provedor_servico;
	}
}
