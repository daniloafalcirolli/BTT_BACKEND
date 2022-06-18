package btt_telecom.api.dto;

import java.util.Date;
import java.util.List;

import btt_telecom.api.models.Cliente;
import btt_telecom.api.models.Material;
import btt_telecom.api.models.Servico;
import btt_telecom.api.models.ServicoProvedor;

public class ServicoidDTO {
	private Long id;
	
	private Cliente cliente;
	
	private ProvedorDTO provedor;
	
	private ServicoProvedor servicoProvedor;
	
	private List<Material> materiais;
		
	private String status;
	
	private String protocolo;
	
	private String observacoes;
	
	private String cod_quebra;
	
	private Date data_finalizacao;
	
	public ServicoidDTO(Servico servico) {
		this.id = servico.getId();
		this.cliente = servico.getCliente();
		this.provedor = new ProvedorDTO(servico.getProvedor());
		this.servicoProvedor = servico.getServicoProvedor();		
		this.materiais = servico.getMateriais();
		this.protocolo = servico.getProtocolo();
		this.status = servico.getStatus();
		this.observacoes = servico.getObservacoes();
		this.cod_quebra = servico.getCod_quebra();
		this.data_finalizacao = servico.getData_finalizacao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ProvedorDTO getProvedor() {
		return provedor;
	}

	public void setProvedor(ProvedorDTO provedor) {
		this.provedor = provedor;
	}

	public ServicoProvedor getServicoProvedor() {
		return servicoProvedor;
	}

	public void setServicoProvedor(ServicoProvedor servicoProvedor) {
		this.servicoProvedor = servicoProvedor;
	}

	public List<Material> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<Material> materiais) {
		this.materiais = materiais;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
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
}
