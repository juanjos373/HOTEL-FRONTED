package com.hotel.repository;

import com.hotel.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByHospedajeId(Long hospedajeId);

    List<Factura> findAllByOrderByFechaGeneracionDesc();
}
