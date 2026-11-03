package com.hotel;

import com.hotel.dto.request.CheckInRequest;
import com.hotel.dto.request.CheckOutRequest;
import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.TipoHabitacion;
import com.hotel.exception.ClienteConHospedajeActivoException;
import com.hotel.exception.HabitacionNoDisponibleException;
import com.hotel.exception.HospedajeActivoNoEncontradoException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HospedajeIntegrationTest extends HotelIntegrationTestSupport {

    @Test
    void realizaCheckInYAsignaHabitacionDisponible() {
        Long clienteId = crearCliente("1111");

        var response = checkInService.realizarCheckIn(new CheckInRequest(clienteId, TipoHabitacion.ESTANDAR));

        assertNotNull(response.getHospedajeId());
        assertEquals(EstadoHabitacion.OCUPADA,
                habitacionRepository.findByNumeroHabitacion(101).orElseThrow().getEstado());
    }

    @Test
    void noPermiteCheckInSiClienteYaTieneHospedajeActivo() {
        Long clienteId = crearCliente("2222");
        checkInService.realizarCheckIn(new CheckInRequest(clienteId, TipoHabitacion.ESTANDAR));

        assertThrows(ClienteConHospedajeActivoException.class,
                () -> checkInService.realizarCheckIn(new CheckInRequest(clienteId, TipoHabitacion.ESTANDAR)));
    }

    @Test
    void rechazaCheckInSiNoHayHabitacionesDisponibles() {
        Long clienteId = crearCliente("3333");
        List<com.hotel.entity.Habitacion> habitaciones = habitacionRepository
                .findByEstadoAndTipoHabitacionOrderByNumeroHabitacionAsc(
                        EstadoHabitacion.DISPONIBLE, TipoHabitacion.ESTANDAR);
        habitaciones.forEach(habitacion -> habitacion.setEstado(EstadoHabitacion.OCUPADA));
        habitacionRepository.saveAll(habitaciones);

        assertThrows(HabitacionNoDisponibleException.class,
                () -> checkInService.realizarCheckIn(new CheckInRequest(clienteId, TipoHabitacion.ESTANDAR)));
    }

    @Test
    void rechazaCheckOutSiClienteNoTieneHospedajeActivo() {
        Long clienteId = crearCliente("5555");

        assertThrows(HospedajeActivoNoEncontradoException.class,
                () -> checkOutService.realizarCheckOut(new CheckOutRequest(clienteId)));
    }
}
