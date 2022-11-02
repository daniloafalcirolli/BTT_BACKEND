package btt_telecom.api.modules.funcionario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.funcionario.model.StatusFunc;

public interface StatusFuncRepository extends JpaRepository<StatusFunc, Long>{
	boolean existsByCodigo(Long codigo);
	
	StatusFunc findByCodigo(Long codigo);
	
	@Query(value = "select * from status_func where abreviatura like %?1% or descricao like %?1% or codigo like %?1%", nativeQuery = true)
	List<StatusFunc> search(String value);
}
