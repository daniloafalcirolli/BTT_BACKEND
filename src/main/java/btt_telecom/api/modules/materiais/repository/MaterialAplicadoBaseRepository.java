package btt_telecom.api.modules.materiais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.materiais.model.MaterialAplicadoBase;

public interface MaterialAplicadoBaseRepository extends JpaRepository<MaterialAplicadoBase, Long>{
	
	@Query(value = "select * from materiais_aplicados where UPPER(material) like %?1% or UPPER(id_senior) like %?1%", nativeQuery = true)
	List<MaterialAplicadoBase> search(String value);	

}
