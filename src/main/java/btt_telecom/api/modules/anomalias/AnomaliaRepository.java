package btt_telecom.api.modules.anomalias;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomaliaRepository extends JpaRepository<Anomalia, Long>{
	
	boolean existsByIdServico(Long id);
	
	Anomalia findByIdServico(Long id);
	
	@Query(value = "select * from anomalia where status_servico = ?1 and id_funcionario = ?2 and data = to_date(?3, 'yyyy-MM-dd')", nativeQuery = true)
	boolean existsByFuncAndStatus(String status, Long id, String data);
	
	@Query(value = "select * from anomalia where status_servico = ?1 and id_funcionario = ?2 and data = to_date(?3, 'yyyy-MM-dd')", nativeQuery = true)
	Anomalia findByFuncAndStatus(String status, Long id, String data);
	
	@Query(value = "select * from anomalia where id_servico = ?1 and status_servico = ?2", nativeQuery = true)
	List<Anomalia> findByIdServiceAndStatus(Long id, String status);
	
	@Query(value = "select * from anomalia where data >= to_date(?1, 'yyyy-MM-dd') AND data <= to_date(?2, 'yyyy-MM-dd') order by data", nativeQuery = true)
	List<Anomalia> findAnomaliasOfAllFuncsInInterval(String data_inicio, String data_final);
	
	@Query(value = "select * from anomalia where data >= to_date(?1, 'yyyy-MM-dd') AND data <= to_date(?2, 'yyyy-MM-dd') and id_funcionario = ?3 order by data", nativeQuery = true)
	List<Anomalia> findAnomaliasOfSingleFuncInInterval(String data_inicio, String data_final, Long id);
}
