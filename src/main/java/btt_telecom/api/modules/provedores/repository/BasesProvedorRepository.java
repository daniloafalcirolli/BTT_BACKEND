package btt_telecom.api.modules.provedores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.provedores.model.BasesProvedor;

public interface BasesProvedorRepository extends JpaRepository<BasesProvedor, Long>{
	
	@Query(value = "select * from bases_provedor where UPPER(nome) like %?1% or UPPER(endereco) like %?1%", nativeQuery = true)
	List<BasesProvedor> search(String value);
}
