package btt_telecom.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.models.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	
	boolean existsByCidade(String cidade);
	
	Cidade findByCidade(String cidade);
	
	@Query(value = "select * from cidades where UPPER(cidade) like %?1%", nativeQuery = true)
	List<Cidade> search(String value);
}
