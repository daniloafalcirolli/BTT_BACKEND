package btt_telecom.api.modules.provedores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.provedores.model.ServicoProvedor;

public interface ServicoProvedorRepository extends JpaRepository<ServicoProvedor, Long>{

	ServicoProvedor findByServico(String servico);
	
	@Query(value = "select * from servico_provedor where identificador = ?1", nativeQuery = true)
	List<ServicoProvedor> findByIdentificador(String identificador);
	
	@Query(value = "SELECT * FROM (SELECT * FROM SERVICO_PROVEDOR WHERE ID_PROVEDOR = ?1) WHERE UPPER(ID_SENIOR) LIKE %?2% OR UPPER(IDENTIFICADOR) LIKE %?2% OR UPPER(SERVICO) LIKE %?2%", nativeQuery = true)
	List<ServicoProvedor> search(Long id_provedor, String value);
}
