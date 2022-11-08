package btt_telecom.api.modules.servico.dto;

public class ServicoRubi {
	private String funcionario;
	
	private String empresa;
	
	private String cliente;
	
	private String contrato;
	
	private String protocolo;
	
	private String provedor;
	
	private String servico_provedor;
	
	private String status;
	
	private String data_inicio;
	
	private String hora_finalizacao;

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

	public String getServico_provedor() {
		return servico_provedor;
	}

	public void setServico_provedor(String servico_provedor) {
		this.servico_provedor = servico_provedor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(String data_inicio) {
		this.data_inicio = data_inicio;
	}

	public String getHora_finalizacao() {
		return hora_finalizacao;
	}

	public void setHora_finalizacao(String hora_finalizacao) {
		this.hora_finalizacao = hora_finalizacao;
	}
}
