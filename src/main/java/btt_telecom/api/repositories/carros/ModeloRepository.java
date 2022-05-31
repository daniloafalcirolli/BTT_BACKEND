package btt_telecom.api.repositories.carros;

import org.springframework.data.jpa.repository.JpaRepository;

import btt_telecom.api.models.carros.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

}
