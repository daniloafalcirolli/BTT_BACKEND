package btt_telecom.api.modules.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterUserRepository extends JpaRepository<MasterUser, Long>{
	@Query(value = "select * from master_users where username = ?1 and password = ?2", nativeQuery = true)
	MasterUser findByUsernameAndPassword(String username, String password);
	
	@Query(value = "select * from master_users where username like %?1% or usuario like %?1%", nativeQuery = true)
	List<MasterUser> search(String value);
}
