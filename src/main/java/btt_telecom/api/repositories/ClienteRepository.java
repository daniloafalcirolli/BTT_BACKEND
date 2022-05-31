package btt_telecom.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import btt_telecom.api.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
