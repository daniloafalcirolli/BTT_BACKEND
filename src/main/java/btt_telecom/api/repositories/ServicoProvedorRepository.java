package btt_telecom.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.models.ServicoProvedor;

public interface ServicoProvedorRepository extends JpaRepository<ServicoProvedor, Long>{

	ServicoProvedor findByServico(String servico);
	
	@Query(value = "select * from servico_provedor where identificador = ?1", nativeQuery = true)
	List<ServicoProvedor> findByIdentificador(String identificador);
}
