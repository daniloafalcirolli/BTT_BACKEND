package btt_telecom.api.modules.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.servico.model.Servico;


public interface ServicoRepository extends JpaRepository<Servico, Long>{
	@Query(value = "select * from servico s where s.protocolo = ?1 and s.data = to_date(?2, 'yyyy-MM-dd') order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findServiceByProtocolAndDate(String protocolo, String data);
	
	@Query(value = "select * from servico s where s.funcionario_id = ?1 and s.status = ?2 and s.data >= to_date(?3, 'yyyy-MM-dd') and s.data <= to_date(?4, 'yyyy-MM-dd') order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findServicesInProgressByFuncAndDate(Long id, String status, String data_inicio, String data_final);
	
	@Query(value = "select * from servico s where s.funcionario_id = ?1 order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findByFunc(Long id);
	
	@Query(value = "select * from servico s where s.funcionario_id = ?1 and s.data = to_date(?2, 'yyyy-MM-dd') and s.status = ?3 order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findByFuncAndExactlyDate(Long id, String data, String status);
	
	@Query(value = "select * from servico s where s.status = ?1 order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findServiceByStatus(String status);
	
	@Query(value = "select * from servico s where s.data >= to_date(?1, 'yyyy-MM-dd') and s.data <= to_date(?2, 'yyyy-MM-dd') order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findServicesInInterval(String data_inicio, String data_final);
	
	@Query(value = "select * from servico s where s.funcionario_id = ?1 and s.status = ?2 order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findServicesByFuncAndStatus(Long id, String status);
	
	@Query(value = "select * from servico s where s.data >= to_date(?1, 'yyyy-MM-dd') and s.data <= to_date(?2, 'yyyy-MM-dd') and s.status = ?3 order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findServicesInProgressByStatus(String data_inicio, String data_final, String status);
	
	@Query(value = "select * from servico s where s.data >= to_date(?1, 'yyyy-MM-dd') and s.data <= to_date(?2, 'yyyy-MM-dd') and s.funcionario_id = ?3 order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findServicesInProgressByFunc(String data_inicio, String data_final, Long id);
	
	@Query(value = "select * from servico s where s.data >= to_date(?1, 'yyyy-MM-dd') and s.data <= to_date(?2, 'yyyy-MM-dd') and s.funcionario_id = ?3 and s.status = ?4 order by s.data asc, s.hora asc", nativeQuery = true)
	List<Servico> findServicesInIntervalAndFuncAndStatus(String data_inicio, String data_final, Long id, String status);
	
	@Query(value = "SELECT * FROM SERVICO s ORDER BY s.DATA ASC, s.HORA ASC", nativeQuery = true)
	List<Servico> findAllServicos();
	
	boolean existsByFuncionario_id(Long funcionario_id);
}
