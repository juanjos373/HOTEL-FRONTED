package com.hotel;

import com.hotel.dto.request.CheckInRequest;
import com.hotel.dto.request.CheckOutRequest;
import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.TipoHabitacion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FacturaIntegrationTest extends HotelIntegrationTestSupport {

    @Test
    void generaFacturaAlCerrarHospedajeYLiberarHabitacion() {
        Long clienteId = crearCliente("4444");
        checkInService.realizarCheckIn(new CheckInRequest(clienteId, TipoHabitacion.ESTANDAR));

        var factura = checkOutService.realizarCheckOut(new CheckOutRequest(clienteId));

        assertNotNull(factura.getFacturaId());
        assertEquals(1, factura.getCantidadNoches());
        assertEquals(EstadoHabitacion.DISPONIBLE,
                habitacionRepository.findByNumeroHabitacion(101).orElseThrow().getEstado());
        assertFalse(hospedajeRepository.findByClienteIdAndActivoTrue(clienteId).isPresent());
    }
}
