package com.hotel;

import com.hotel.service.impl.FacturaServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelNightCalculationTest {

    private final FacturaServiceImpl facturaService = new FacturaServiceImpl(null);

    @Test
    void debeCalcularUnaNocheCuandoSaleAntesDeLas1300() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida = LocalDateTime.of(2026, 10, 11, 12, 30);

        assertEquals(1, facturaService.calcularCantidadNoches(ingreso, salida));
    }

    @Test
    void debeCalcularDosNochesCuandoSaleDespuesDeLas1300() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida = LocalDateTime.of(2026, 10, 11, 13, 1);

        assertEquals(2, facturaService.calcularCantidadNoches(ingreso, salida));
    }

    @Test
    void debeCalcularUnaNocheParaUnaEstadiaDePocasHoras() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida = LocalDateTime.of(2026, 10, 10, 20, 0);

        assertEquals(1, facturaService.calcularCantidadNoches(ingreso, salida));
    }
}
