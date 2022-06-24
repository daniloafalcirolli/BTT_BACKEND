package btt_telecom.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import btt_telecom.api.models.MasterUser;

public interface MasterUserRepository extends JpaRepository<MasterUser, Long>{
	@Query(value = "select * from master_users where username = ?1 and password = ?2", nativeQuery = true)
	MasterUser findByUsernameAndPassword(String username, String password);
}
