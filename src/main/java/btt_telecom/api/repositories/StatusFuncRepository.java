package btt_telecom.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import btt_telecom.api.models.StatusFunc;

public interface StatusFuncRepository extends JpaRepository<StatusFunc, Long>{
	boolean existsByCodigo(Long codigo);
	
	StatusFunc findByCodigo(Long codigo);
}
