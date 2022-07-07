package btt_telecom.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.models.Rota;

public interface RotaRepository extends JpaRepository<Rota, Long>{
	@Query(value = "select * from rotas WHERE data = to_date(?1, 'yyyy-MM-dd') AND funcionario_id = ?2 order by hora asc", nativeQuery = true)
	List<Rota> findRotasByFuncAndData(String data, Long id);
}
