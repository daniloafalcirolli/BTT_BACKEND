package btt_telecom.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.models.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long>{
	@Query(value = "select * from servico where funcionario_id = ?1 and status = ?2 and data_finalizacao = to_date(?3, 'yyyy-MM-dd')", nativeQuery = true)
	List<Servico> findServicesInProgressByFuncAndDate(Long id, String status, String data);
	
	@Query(value = "select * from servico where funcionario_id = ?1 and status = ?2", nativeQuery = true)
	List<Servico> findServicesInProgressByFunc(Long id, String status);
	
	boolean existsByFuncionario_id(Long funcionario_id);
}
