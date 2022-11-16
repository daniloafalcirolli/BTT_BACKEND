package btt_telecom.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.models.Rota;

public interface RotaRepository extends JpaRepository<Rota, Long>{
	@Query(value = "select * from rotas where id_servico = ?1", nativeQuery = true)
	Rota findRotaById_serv(Long id_servico);
	
	@Query(value = "SELECT * FROM rotas r where r.CPF_FUNCIONARIO = ?1 and r.\"DATA\" >= to_date(?2, 'yyyy-MM-dd') AND r.\"DATA\" <= to_date(?3, 'yyyy-MM-dd') order by data, hora", nativeQuery = true)
	List<Rota> findAllByFuncInInterval(String cpf, String data_inicio, String data_fim);
	
	@Query(value = "SELECT * FROM ROTAS WHERE data = to_date(?1, 'yyyy-MM-dd') AND CPF_FUNCIONARIO = ?2 order by hora asc", nativeQuery = true)
	List<Rota> findRotasByFuncAndData(String data, String cpf);
	
	@Query(value = "select * from rotas where data >= to_date(?1, 'yyyy-MM-dd') AND data <= to_date(?2, 'yyyy-MM-dd') order by funcionario_id, data asc, hora asc", nativeQuery = true)
	List<Rota> findRotasOfAllFuncsInInterval(String data_inicio, String data_final);
	
	@Query(value = "select * from rotas where data >= to_date(?1, 'yyyy-MM-dd') AND data <= to_date(?2, 'yyyy-MM-dd') and funcionario_id = ?3 order by funcionario_id, data asc, hora asc ", nativeQuery = true)
	List<Rota> findRotasOfSingleFuncInInterval(String data_inicio, String data_final, Long id);
	
	@Query(value = "SELECT * FROM rotas r where r.NOME_CIDADE = ?1 and r.\"DATA\" >= to_date(?2, 'yyyy-MM-dd') AND r.\"DATA\" <= to_date(?3, 'yyyy-MM-dd') order by data, hora", nativeQuery = true)
	List<Rota> findAllFuncsByCityInInterval(String nome_cidade, String data_inicio,  String data_final);
}
