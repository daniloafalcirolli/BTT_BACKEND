package btt_telecom.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import btt_telecom.api.models.MasterUser;

public interface MasterUserRepository extends JpaRepository<MasterUser, Long>{

}
