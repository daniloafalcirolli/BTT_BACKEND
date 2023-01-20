package btt_telecom.api.modules.materiais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.materiais.model.MaterialRetiradoBase;

public interface MaterialRetiradoBaseRepository extends JpaRepository<MaterialRetiradoBase, Long>{
	@Query(value = "select * from materiais_retirados where UPPER(material) like UPPER(%?1%) or UPPER(id_senior) like UPPER(%?1%)", nativeQuery = true)
	List<MaterialRetiradoBase> search(String value);	
}
