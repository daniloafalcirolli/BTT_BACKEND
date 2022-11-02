package btt_telecom.api.modules.servico.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import btt_telecom.api.models.Cliente;
import btt_telecom.api.modules.funcionario.model.Funcionario;
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
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Funcionario funcionario;
	
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
	
	private String status;
	
	private String observacoes;
	
	private String cod_quebra;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Temporal(TemporalType.TIME)
	private Date hora;

	@Lob
	@Column(name = "relatorio", length = 2048)
	private String relatorio;
	
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

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}
}