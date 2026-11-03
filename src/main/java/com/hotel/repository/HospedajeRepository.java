package com.hotel.repository;

import com.hotel.entity.Hospedaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospedajeRepository extends JpaRepository<Hospedaje, Long> {

    Optional<Hospedaje> findByClienteIdAndActivoTrue(Long clienteId);

    List<Hospedaje> findByActivoTrueOrderByFechaHoraIngresoDesc();

    List<Hospedaje> findByClienteIdOrderByFechaHoraIngresoDesc(Long clienteId);

    List<Hospedaje> findAllByOrderByFechaHoraIngresoDesc();
}
