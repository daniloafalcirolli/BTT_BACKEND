package btt_telecom.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import btt_telecom.api.models.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	@Query(value = "select * from Funcionario where username = ?1 and cpf = ?2", nativeQuery = true)
	Funcionario findByUsernameCpf(String username, String cpf);
	
}
