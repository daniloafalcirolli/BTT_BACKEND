package btt_telecom.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	
	@Query(value = "select * from empresas where empresa like %?1%", nativeQuery = true)
	List<Empresa> search(String value);
}
