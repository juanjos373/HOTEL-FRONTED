package com.hotel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClienteIntegrationTest extends HotelIntegrationTestSupport {

    @Test
    void creaYConsultaCliente() {
        Long clienteId = crearCliente("123456");

        assertNotNull(clienteId);
        assertEquals("123456", clienteService.consultarPorId(clienteId).getIdentificacion());
    }
}
