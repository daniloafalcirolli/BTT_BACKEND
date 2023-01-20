package btt_telecom.api.modules.materiais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.materiais.model.MaterialRetiradoBase;

public interface MaterialRetiradoBaseRepository extends JpaRepository<MaterialRetiradoBase, Long>{
	@Query(value = "SELECT * FROM MATERIAIS_RETIRADOS WHERE DELT_FLG = 0", nativeQuery = true)
	List<MaterialRetiradoBase> findAll();	
	
	@Query(value = "SELECT * FROM MATERIAIS_RETIRADOS WHERE (UPPER(MATERIAL) LIKE UPPER(%?1%) OR UPPER(ID_SENIOR) LIKE UPPER(%?1%)) AND DELT_FLG = 0", nativeQuery = true)
	List<MaterialRetiradoBase> search(String value);	
}
