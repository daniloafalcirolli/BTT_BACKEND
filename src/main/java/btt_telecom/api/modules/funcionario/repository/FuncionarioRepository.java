package btt_telecom.api.modules.funcionario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import btt_telecom.api.modules.funcionario.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	@Query(value = "select * from Funcionario where username = ?1 and cpf = ?2", nativeQuery = true)
	Funcionario findByUsernameCpf(String username, String cpf);
	
	@Query(value = "select * from Funcionario where login_provedor = ?1", nativeQuery = true)
	Optional<Funcionario> findByNomeProvedor(String login);
	
	boolean existsByCpf(String cpf);
	
	Optional<Funcionario> findByCpf(String cpf);
	
	Optional<Funcionario> findByNome(String nome);
	
	@Query(value = "select * from funcionario where UPPER(nome) like %?1% or UPPER(cpf) like %?1% or UPPER(rg) like %?1% or UPPER(telefone) like %?1%", nativeQuery = true)
	List<Funcionario> search(String value);

}
