package btt_telecom.api.modules.provedores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.provedores.model.ImagemProvedor;

public interface ImagensProvedorRepository extends JpaRepository<ImagemProvedor, Long>{

	@Query(value = "SELECT * FROM IMAGENS_PROVEDOR WHERE UPPER(NAME) LIKE %?1% OR UPPER(DESCRICAO) LIKE %?1% OR UPPER(STATUS_SERVICO) LIKE %?1%", nativeQuery = true)
	List<ImagemProvedor> search(String value);
}
