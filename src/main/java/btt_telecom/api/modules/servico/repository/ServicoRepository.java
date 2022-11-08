package btt_telecom.api.modules.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.servico.model.Servico;


public interface ServicoRepository extends JpaRepository<Servico, Long>{
	@Query(value = "select * from servico s where s.cpf_funcionario = ?1 and s.\"DATA\" = TO_DATE(?2, 'YYYY-MM-DD') order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findByFuncAndStatus(String cpf, String data);
	
}
