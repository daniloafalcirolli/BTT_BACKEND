package btt_telecom.api.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import btt_telecom.api.models.Servico;

public class ServicoDTO {
	private Long id;
	
	private String func;
	
	private String contrato;
	
	private String protocolo;
	
	private String empresa;
	
	private String descricao;
	
	private String materiais;
	
	private String status;
	
	private String observacoes;
	
	private String cod_quebra;
		
	private Date data_finalizacao;
	
	private String hora_finalizacao;
	
	private String cliente;
	
	private String provedor;
	
	private Long id_cliente;
	
	private Long id_funcionario;
	
	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public ServicoDTO(Servico servico) {
		this.id = servico.getId();
		this.func = servico.getFuncionario().getNome();
		this.empresa = servico.getFuncionario().getEmpresa().getEmpresa();
		this.cliente = servico.getCliente().getNome();
		this.id_cliente = servico.getCliente().getId();
		this.id_funcionario = servico.getFuncionario().getId();
		this.observacoes = servico.getObservacoes();
		this.contrato = servico.getCliente().getContrato();
		this.protocolo = servico.getProtocolo();
		this.data_finalizacao = servico.getData_finalizacao();
		this.status = servico.getStatus();
		this.materiais = servico.getRelatorio();
		this.descricao = servico.getServicoProvedor().getServico();
		this.provedor = servico.getProvedor().getName();
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

	public String getContrato() {
		return contrato;
	}
	
	public String getProvedor() {
		return provedor;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMateriais() {
		return materiais;
	}

	public void setMateriais(String materiais) {
		this.materiais = materiais;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getCod_quebra() {
		return cod_quebra;
	}

	public void setCod_quebra(String cod_quebra) {
		this.cod_quebra = cod_quebra;
	}

	public Date getData_finalizacao() {
		return data_finalizacao;
	}

	public void setData_finalizacao(Date data_finalizacao) {
		this.data_finalizacao = data_finalizacao;
	}

	public Long getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Long id_cliente) {
		this.id_cliente = id_cliente;
	}

	public Long getId_funcionario() {
		return id_funcionario;
	}

	public void setId_funcionario(Long id_funcionario) {
		this.id_funcionario = id_funcionario;
	}

	public String getHora_finalizacao() {
		return hora_finalizacao;
	}

	public void setHora_finalizacao(String hora_finalizacao) {
		this.hora_finalizacao = hora_finalizacao;
	}
}
