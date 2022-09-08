package btt_telecom.api.modules.materiais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.materiais.model.MaterialAplicado;

public interface MaterialAplicadoRepository extends JpaRepository<MaterialAplicado, Long>{
	
	@Query(value = "SELECT * FROM `materiais_aplicados_servico` WHERE material_aplicado_id = ?1", nativeQuery = true)
	List<MaterialAplicado> findByMaterialId(Long id);

}
