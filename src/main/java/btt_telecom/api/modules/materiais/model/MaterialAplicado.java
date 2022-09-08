package btt_telecom.api.modules.materiais.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "materiais_aplicados_servico")
public class MaterialAplicado {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private MaterialAplicadoBase material_aplicado;

	private String value;
	
	public MaterialAplicado() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MaterialAplicadoBase getMaterial_aplicado() {
		return material_aplicado;
	}

	public void setMaterial_aplicado(MaterialAplicadoBase material_aplicado) {
		this.material_aplicado = material_aplicado;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
