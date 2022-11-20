package btt_telecom.api.modules.clientes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	boolean existsByCpf(String cpf);
	
	boolean existsByCnpj(String cnpj);
	
	boolean existsByContrato(String contrato);
	
	Cliente findByCpf(String cpf);
	
	Cliente findByCnpj(String cnpj);
	
	Cliente findByContrato(String contrato);
}
