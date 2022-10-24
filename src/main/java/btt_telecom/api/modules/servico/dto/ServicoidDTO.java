package btt_telecom.api.modules.servico.dto;

import java.util.Date;

import btt_telecom.api.models.Cliente;
import btt_telecom.api.modules.funcionario.dto.FuncionarioDTO;
import btt_telecom.api.modules.provedores.dto.ProvedorDTO;
import btt_telecom.api.modules.provedores.model.ServicoProvedor;
import btt_telecom.api.modules.servico.model.Servico;

public class ServicoidDTO {
	private Long id;
	
	private Cliente cliente;
	
	private ProvedorDTO provedor;
	
	private FuncionarioDTO funcionario;
	
	private ServicoProvedor servicoProvedor;
	
		
	private String status;
	
	private String protocolo;
	
	private String observacoes;
	
	private String cod_quebra;
	
	private Date data_finalizacao;
	
	private String relatorio;
	
	private String hora_finalizacao;
	
	public ServicoidDTO(Servico servico) {
		this.id = servico.getId();
		this.cliente = servico.getCliente();
		this.funcionario = new FuncionarioDTO(servico.getFuncionario());
		this.provedor = new ProvedorDTO(servico.getProvedor());
		this.servicoProvedor = servico.getServicoProvedor();		
//		this.materiais = servico.getMateriais();
		this.protocolo = servico.getProtocolo();
		this.status = servico.getStatus();
		this.observacoes = servico.getObservacoes();
		this.cod_quebra = servico.getCod_quebra();
		this.data_finalizacao = servico.getData();
		this.relatorio = servico.getRelatorio();
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

	public FuncionarioDTO getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioDTO funcionario) {
		this.funcionario = funcionario;
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

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public String getHora_finalizacao() {
		return hora_finalizacao;
	}

	public void setHora_finalizacao(String hora_finalizacao) {
		this.hora_finalizacao = hora_finalizacao;
	}
}
