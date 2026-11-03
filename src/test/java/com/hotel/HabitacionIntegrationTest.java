package com.hotel;

import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.TipoHabitacion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HabitacionIntegrationTest extends HotelIntegrationTestSupport {

    @Test
    void consultaHabitacionesYDisponibilidadPorTipo() {
        assertEquals(6, habitacionRepository.findAll().size());

        assertEquals(3, habitacionRepository.findByEstadoAndTipoHabitacionOrderByNumeroHabitacionAsc(
                EstadoHabitacion.DISPONIBLE,
                TipoHabitacion.ESTANDAR
        ).size());
    }
}
