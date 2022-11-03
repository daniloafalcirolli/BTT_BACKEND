package btt_telecom.api.modules.meta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.modules.meta.model.Meta;

public interface MetaRepository extends JpaRepository<Meta, Long>{
	
	@Query(value = "SELECT * FROM META WHERE UPPER(META_KEY) LIKE %?1% OR UPPER(META_VALUE) LIKE %?1% OR UPPER(DESCRIPTION) LIKE %?1%", nativeQuery = true)
	List<Meta> search(String value);
	
	boolean existsByMeta_key(String key);
	
	Optional<Meta> findByMeta_key(String key);
}
