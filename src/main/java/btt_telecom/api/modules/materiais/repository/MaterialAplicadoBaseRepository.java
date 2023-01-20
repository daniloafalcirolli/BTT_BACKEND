package btt_telecom.api.modules.materiais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.materiais.model.MaterialAplicadoBase;

public interface MaterialAplicadoBaseRepository extends JpaRepository<MaterialAplicadoBase, Long>{
	
	@Query(value = "SELECT * FROM MATERIAIS_APLICADOS WHERE DELT_FLG = 0", nativeQuery = true)
	List<MaterialAplicadoBase> findAll();	

	
	@Query(value = "SELECT * FROM MATERIAIS_APLICADOS WHERE (UPPER(MATERIAL) LIKE %?1% OR UPPER(ID_SENIOR) LIKE %?1%) AND DELT_FLG = 0", nativeQuery = true)
	List<MaterialAplicadoBase> search(String value);	

}
