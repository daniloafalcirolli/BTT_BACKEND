package btt_telecom.api.modules.servico.dto;

import java.util.Date;
import java.util.List;

import btt_telecom.api.modules.clientes.Cliente;
import btt_telecom.api.modules.funcionario.dto.FuncionarioRubi;
import btt_telecom.api.modules.imagens.Imagem;
import btt_telecom.api.modules.materiais.model.MaterialAplicado;
import btt_telecom.api.modules.materiais.model.MaterialRetirado;
import btt_telecom.api.modules.provedores.model.CamposProvedor;
import btt_telecom.api.modules.provedores.model.Provedor;
import btt_telecom.api.modules.provedores.model.ServicoProvedor;
import btt_telecom.api.modules.servico.model.Servico;

public class ServicoResponse {
	private Long id;
	
	private FuncionarioRubi funcionario;
	
	private Cliente cliente;
	
	private Provedor provedor;
	
	private ServicoProvedor servicoProvedor;
	
	private String protocolo;
	
	private List<CamposProvedor> campos_aplicados;
	
	private List<MaterialAplicado> materiais_aplicados;

	private List<MaterialRetirado> materiais_retirados;

	private List<Imagem> imagens;

	private String status;
	
	private String observacoes;
	
	private String cod_quebra;
	
	private Date data;

	private Date hora;

	private Date data_finalizacao;

	private Date hora_finalizacao;
	
	public ServicoResponse(Servico servico) {
		this.id = servico.getId();
		this.cliente = servico.getCliente();
		this.provedor = servico.getProvedor();
		this.servicoProvedor = servico.getServicoProvedor();
		this.protocolo = servico.getProtocolo();
		this.campos_aplicados = servico.getCampos_aplicados();
		this.materiais_aplicados = servico.getMateriais_aplicados();
		this.materiais_retirados = servico.getMateriais_retirados();
		this.imagens = servico.getImagens();
		this.status = servico.getStatus();
		this.observacoes = servico.getObservacoes();
		this.cod_quebra = servico.getCod_quebra();
		this.data = servico.getData();
		this.hora = servico.getHora();
		this.data_finalizacao = servico.getData_finalizacao();
		this.hora_finalizacao = servico.getHora_finalizacao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioRubi getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioRubi funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Provedor getProvedor() {
		return provedor;
	}

	public void setProvedor(Provedor provedor) {
		this.provedor = provedor;
	}

	public ServicoProvedor getServicoProvedor() {
		return servicoProvedor;
	}

	public void setServicoProvedor(ServicoProvedor servicoProvedor) {
		this.servicoProvedor = servicoProvedor;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public List<CamposProvedor> getCampos_aplicados() {
		return campos_aplicados;
	}

	public void setCampos_aplicados(List<CamposProvedor> campos_aplicados) {
		this.campos_aplicados = campos_aplicados;
	}

	public List<MaterialAplicado> getMateriais_aplicados() {
		return materiais_aplicados;
	}

	public void setMateriais_aplicados(List<MaterialAplicado> materiais_aplicados) {
		this.materiais_aplicados = materiais_aplicados;
	}

	public List<MaterialRetirado> getMateriais_retirados() {
		return materiais_retirados;
	}

	public void setMateriais_retirados(List<MaterialRetirado> materiais_retirados) {
		this.materiais_retirados = materiais_retirados;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Date getData_finalizacao() {
		return data_finalizacao;
	}

	public void setData_finalizacao(Date data_finalizacao) {
		this.data_finalizacao = data_finalizacao;
	}

	public Date getHora_finalizacao() {
		return hora_finalizacao;
	}

	public void setHora_finalizacao(Date hora_finalizacao) {
		this.hora_finalizacao = hora_finalizacao;
	}
}
