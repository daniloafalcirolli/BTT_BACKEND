package btt_telecom.api.modules.funcionario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.funcionario.model.StatusFunc;

public interface StatusFuncRepository extends JpaRepository<StatusFunc, Long>{
	boolean existsByCodigo(Long codigo);
	
	StatusFunc findByCodigo(Long codigo);
	
	@Query(value = "select * from status_func where UPPER(abreviatura) like %?1% or UPPER(descricao) like %?1% or codigo like %?1% order by codigo", nativeQuery = true)
	List<StatusFunc> search(String value);
	
	@Query(value = "select * from status_func order by codigo", nativeQuery = true)
	List<StatusFunc> findAll();
}
