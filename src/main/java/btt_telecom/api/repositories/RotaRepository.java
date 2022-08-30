package btt_telecom.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.models.Rota;

public interface RotaRepository extends JpaRepository<Rota, Long>{
	@Query(value = "select * from rotas where id_servico = ?1", nativeQuery = true)
	Rota findRotaById_serv(Long id_servico);
	
	@Query(value = "select * from rotas where funcionario_id = ?1 order by data, hora", nativeQuery = true)
	List<Rota> findRotasByFunc(Long funcionario_id);
	
	@Query(value = "select * from rotas WHERE data = to_date(?1, 'yyyy-MM-dd') AND funcionario_id = ?2 order by hora asc", nativeQuery = true)
	List<Rota> findRotasByFuncAndData(String data, Long id);
	
	@Query(value = "select * from rotas where data >= to_date(?1, 'yyyy-MM-dd') AND data <= to_date(?2, 'yyyy-MM-dd') order by funcionario_id, data asc, hora asc", nativeQuery = true)
	List<Rota> findRotasOfAllFuncsInInterval(String data_inicio, String data_final);
	
	@Query(value = "select * from rotas where data >= to_date(?1, 'yyyy-MM-dd') AND data <= to_date(?2, 'yyyy-MM-dd') and funcionario_id = ?3 order by funcionario_id, data asc, hora asc ", nativeQuery = true)
	List<Rota> findRotasOfSingleFuncInInterval(String data_inicio, String data_final, Long id);
	
	@Query(value = "select * from rotas where data >= to_date(?1, 'yyyy-MM-dd') AND data <= to_date(?2, 'yyyy-MM-dd') and id_cidade = ?3 order by funcionario_id, data asc, hora asc ", nativeQuery = true)
	List<Rota> findRotasOfAllFuncsByCityInInterval(String data_inicio, String data_final, Long id);
}
