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
@Table(name = "materiais_retirados_servico")
public class MaterialRetirado {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private MaterialRetiradoBase material_retirado;

	private String value;
	
	public MaterialRetirado() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MaterialRetiradoBase getMaterial_retirado() {
		return material_retirado;
	}

	public void setMaterial_retirado(MaterialRetiradoBase material_retirado) {
		this.material_retirado = material_retirado;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}