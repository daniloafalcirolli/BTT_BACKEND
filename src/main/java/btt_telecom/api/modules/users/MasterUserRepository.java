package btt_telecom.api.modules.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterUserRepository extends JpaRepository<MasterUser, Long>{
	@Query(value = "select * from master_users where email = ?1 and password = ?2", nativeQuery = true)
	MasterUser findByEmailAndPassword(String email, String password);
	
	@Query(value = "select * from master_users where UPPER(username) like %?1% or UPPER(email) like %?1%", nativeQuery = true)
	List<MasterUser> search(String value);
}
