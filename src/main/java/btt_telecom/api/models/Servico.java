package btt_telecom.api.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Servico {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Cliente cliente;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Funcionario funcionario;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Provedor provedor;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private ServicoProvedor servicoProvedor;
	
	private String contrato;
	
	private String protocolo;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Material> materiais;
	
	private String status;
	
	private String observacoes;
	
	private String cod_quebra;
	
	private Date data_finalizacao;

	public Servico() {
		
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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
