package btt_telecom.api.modules.provedores.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.provedores.model.Provedor;

public interface ProvedorRepository extends JpaRepository<Provedor, Long> {
	
	Provedor findByName(String name);
	
	Optional<Provedor> findByIdentificador(String identificador);
	
	@Query(value = "SELECT id, name, identificador FROM provedor p inner join provedor_materiais_aplicados pma on pma.materiais_aplicados_id = ?1", nativeQuery = true)
	List<Provedor> findByMaterialAplicado(Long id_material);
	
	@Query(value = "select * from provedor where UPPER(name) like UPPER(%?1%) or UPPER(id_senior) like UPPER(%?1%)", nativeQuery = true)
	List<Provedor> search(String value);
}
