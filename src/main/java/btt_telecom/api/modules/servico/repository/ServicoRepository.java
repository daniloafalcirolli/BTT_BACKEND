package btt_telecom.api.modules.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.servico.model.Servico;


public interface ServicoRepository extends JpaRepository<Servico, Long>{
	@Query(value = "select * from servico where protocolo = ?1 and data_finalizacao = to_date(?2, 'yyyy-MM-dd') order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findServiceByProtocolAndDate(String protocolo, String data);
	
	@Query(value = "select * from servico where funcionario_id = ?1 and status = ?2 and data_finalizacao >= to_date(?3, 'yyyy-MM-dd') and data_finalizacao <= to_date(?4, 'yyyy-MM-dd') order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findServicesInProgressByFuncAndDate(Long id, String status, String data_inicio, String data_final);
	
	@Query(value = "select * from servico where funcionario_id = ?1 order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findByFunc(Long id);
	
	@Query(value = "select * from servico where funcionario_id = ?1 and data_finalizacao = to_date(?2, 'yyyy-MM-dd') and status = ?3 order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findByFuncAndExactlyDate(Long id, String data, String status);
	
	@Query(value = "select * from servico where status = ?1 order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findServiceByStatus(String status);
	
	@Query(value = "select * from servico where data_finalizacao >= to_date(?1, 'yyyy-MM-dd') and data_finalizacao <= to_date(?2, 'yyyy-MM-dd') order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findServicesInInterval(String data_inicio, String data_final);
	
	@Query(value = "select * from servico where funcionario_id = ?1 and status = ?2 order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findServicesByFuncAndStatus(Long id, String status);
	
	@Query(value = "select * from servico where data_finalizacao >= to_date(?1, 'yyyy-MM-dd') and data_finalizacao <= to_date(?2, 'yyyy-MM-dd') and status = ?3 order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findServicesInProgressByStatus(String data_inicio, String data_final, String status);
	
	@Query(value = "select * from servico where data_finalizacao >= to_date(?1, 'yyyy-MM-dd') and data_finalizacao <= to_date(?2, 'yyyy-MM-dd') and funcionario_id = ?3 order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findServicesInProgressByFunc(String data_inicio, String data_final, Long id);
	
	@Query(value = "select * from servico where data_finalizacao >= to_date(?1, 'yyyy-MM-dd') and data_finalizacao <= to_date(?2, 'yyyy-MM-dd') and funcionario_id = ?3 and status = ?4 order by data_finalizacao asc, hora_finalizacao asc", nativeQuery = true)
	List<Servico> findServicesInIntervalAndFuncAndStatus(String data_inicio, String data_final, Long id, String status);
	
	
	boolean existsByFuncionario_id(Long funcionario_id);
	
	
	
//	@Query(value = "select * from servico where data >= to_date(?1, 'yyyy-MM-dd') ", nativeQuery = true)
//	List<Servico> findServicesByDate
}
