package btt_telecom.api.modules.materiais.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "materiais_aplicados")
public class MaterialAplicadoBase {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	private String material;
	
	private String id_senior;
	
	private boolean delt_flg;
	
	@Column(nullable = true)
	private boolean has_serial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getId_senior() {
		return id_senior;
	}

	public void setId_senior(String id_senior) {
		this.id_senior = id_senior;
	}
	
	public boolean isDelt_flg() {
		return delt_flg;
	}

	public void setDelt_flg(boolean delt_flg) {
		this.delt_flg = delt_flg;
	}

	public boolean isHas_serial() {
		return has_serial;
	}

	public void setHas_serial(boolean has_serial) {
		this.has_serial = has_serial;
	}
}
