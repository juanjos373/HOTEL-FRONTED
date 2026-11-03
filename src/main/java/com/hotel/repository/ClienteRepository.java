package com.hotel.repository;

import com.hotel.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByIdentificacion(String identificacion);

    List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}
