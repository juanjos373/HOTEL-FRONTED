package com.hotel.repository;

import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.Habitacion;
import com.hotel.entity.TipoHabitacion;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNumeroHabitacion(Integer numeroHabitacion);

    List<Habitacion> findAllByOrderByNumeroHabitacionAsc();

    List<Habitacion> findByEstadoAndTipoHabitacionOrderByNumeroHabitacionAsc(EstadoHabitacion estado, TipoHabitacion tipoHabitacion);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT h FROM Habitacion h WHERE h.estado = :estado AND h.tipoHabitacion = :tipoHabitacion ORDER BY h.numeroHabitacion ASC")
    List<Habitacion> findByEstadoAndTipoHabitacionForUpdate(@Param("estado") EstadoHabitacion estado,
                                                           @Param("tipoHabitacion") TipoHabitacion tipoHabitacion);
}
