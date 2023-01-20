package btt_telecom.api.modules.provedores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.provedores.model.CategoriaServicoProvedor;

public interface CategoriaServicoProvedorRepository extends JpaRepository<CategoriaServicoProvedor, Long>{
	
	@Query(value = "SELECT * FROM CATEGORIAS_SERVICO_PROVEDOR WHERE UPPER(CATEGORIA) LIKE %?1%", nativeQuery = true)
	List<CategoriaServicoProvedor> search(String value);
}
