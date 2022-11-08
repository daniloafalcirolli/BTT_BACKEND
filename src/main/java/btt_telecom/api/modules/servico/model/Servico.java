package btt_telecom.api.modules.servico.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import btt_telecom.api.models.Cliente;
import btt_telecom.api.modules.imagens.model.Imagem;
import btt_telecom.api.modules.materiais.model.MaterialAplicado;
import btt_telecom.api.modules.materiais.model.MaterialRetirado;
import btt_telecom.api.modules.provedores.model.CamposProvedor;
import btt_telecom.api.modules.provedores.model.Provedor;
import btt_telecom.api.modules.provedores.model.ServicoProvedor;

@Entity
public class Servico {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Cliente cliente;
	
	private String cpf_funcionario;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Provedor provedor;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private ServicoProvedor servicoProvedor;
		
	private String protocolo;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<CamposProvedor> campos_aplicados;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<MaterialAplicado> materiais_aplicados;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<MaterialRetirado> materiais_retirados;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Imagem> imagens;
	
	private String status;
	
	private String observacoes;
	
	private String cod_quebra;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Temporal(TemporalType.TIME)
	private Date hora;
	
	@Temporal(TemporalType.DATE)
	private Date data_finalizacao;

	@Temporal(TemporalType.TIME)
	private Date hora_finalizacao;
	
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

	public String getCpf_funcionario() {
		return cpf_funcionario;
	}

	public void setCpf_funcionario(String cpf_funcionario) {
		this.cpf_funcionario = cpf_funcionario;
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
