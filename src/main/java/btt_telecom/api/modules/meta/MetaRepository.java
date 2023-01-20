package btt_telecom.api.modules.meta;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MetaRepository extends JpaRepository<Meta, Long>{
	
	@Query(value = "SELECT * FROM META WHERE UPPER(META_KEY) LIKE UPPER(%?1%) OR UPPER(META_VALUE) LIKE UPPER(%?1%) OR UPPER(DESCRIPTION) LIKE UPPER(%?1%)", nativeQuery = true)
	List<Meta> search(String value);
	
	boolean existsByMetaKey(String meta_key);

	Optional<Meta> findByMetaKey(String key);
}
