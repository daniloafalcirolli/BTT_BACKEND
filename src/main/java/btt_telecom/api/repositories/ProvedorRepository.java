package btt_telecom.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import btt_telecom.api.models.Provedor;

public interface ProvedorRepository extends JpaRepository<Provedor, Long> {
	
	Provedor findByName(String name);
	
	Optional<Provedor> findByIdentificador(String identificador);
}
