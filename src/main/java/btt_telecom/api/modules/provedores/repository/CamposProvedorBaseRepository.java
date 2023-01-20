package btt_telecom.api.modules.provedores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.provedores.model.CamposProvedorBase;

public interface CamposProvedorBaseRepository extends JpaRepository<CamposProvedorBase, Long>{
	
	@Query(value = "select * from campos_provedor where UPPER(campo) like UPPER(%?1%)", nativeQuery = true)
	List<CamposProvedorBase> search(String value);
}
