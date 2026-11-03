package com.hotel.service.impl;

import com.hotel.dto.response.FacturaResponse;
import com.hotel.entity.Factura;
import com.hotel.entity.Hospedaje;
import com.hotel.exception.FacturaNoEncontradaException;
import com.hotel.repository.FacturaRepository;
import com.hotel.service.ServicioFactura;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements ServicioFactura {

    private static final LocalTime HORA_CHECKOUT_OFICIAL = LocalTime.of(13, 0);
    private final FacturaRepository facturaRepository;

    @Override
    @Transactional(readOnly = true)
    public FacturaResponse consultarPorId(Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new FacturaNoEncontradaException(facturaId));
        return mapToResponse(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturaResponse> consultarTodas() {
        return facturaRepository.findAllByOrderByFechaGeneracionDesc().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public int calcularCantidadNoches(LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida) {
        /*
         * La regla del hotel se basa en la fecha y hora de salida respecto al horario oficial de check-out (13:00).
         * Si el cliente sale el mismo día de ingreso, se factura una noche mínima.
         * Si sale al siguiente día antes o exactamente a las 13:00, la cuenta sigue siendo una noche.
         * Si sale después de las 13:00, se suma una noche adicional.
         * Para estadías prolongadas, el cálculo continúa con la diferencia de días y la hora de salida.
         */
        if (!fechaHoraSalida.isAfter(fechaHoraIngreso)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de ingreso.");
        }

        LocalDate fechaInicio = fechaHoraIngreso.toLocalDate();
        LocalDate fechaFin = fechaHoraSalida.toLocalDate();
        LocalTime horaSalida = fechaHoraSalida.toLocalTime();

        long diferenciaDias = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        int noches = (int) diferenciaDias;

        if (diferenciaDias == 0) {
            return 1;
        }

        if (horaSalida.isAfter(HORA_CHECKOUT_OFICIAL)) {
            noches += 1;
        }

        return Math.max(1, noches);
    }

    public Factura crearFactura(Hospedaje hospedaje, LocalDateTime fechaHoraSalida) {
        int cantidadNoches = calcularCantidadNoches(hospedaje.getFechaHoraIngreso(), fechaHoraSalida);
        BigDecimal valorNoche = hospedaje.getHabitacion().getValorNoche();
        BigDecimal valorTotal = valorNoche.multiply(BigDecimal.valueOf(cantidadNoches));

        Factura factura = Factura.builder()
                .hospedaje(hospedaje)
                .cantidadNoches(cantidadNoches)
                .valorNoche(valorNoche)
                .valorTotal(valorTotal)
                .build();
        return facturaRepository.save(factura);
    }

    private FacturaResponse mapToResponse(Factura factura) {
        return FacturaResponse.builder()
                .facturaId(factura.getId())
                .clienteId(factura.getHospedaje().getCliente().getId())
                .cliente(factura.getHospedaje().getCliente().getNombreCompleto())
                .numeroHabitacion(factura.getHospedaje().getHabitacion().getNumeroHabitacion())
                .tipoHabitacion(factura.getHospedaje().getHabitacion().getTipoHabitacion())
                .fechaHoraIngreso(factura.getHospedaje().getFechaHoraIngreso())
                .fechaHoraSalida(factura.getHospedaje().getFechaHoraSalida())
                .cantidadNoches(factura.getCantidadNoches())
                .valorNoche(factura.getValorNoche())
                .valorTotal(factura.getValorTotal())
                .fechaGeneracion(factura.getFechaGeneracion())
                .build();
    }
}
