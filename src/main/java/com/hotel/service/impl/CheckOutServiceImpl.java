package com.hotel.service.impl;

import com.hotel.dto.request.CheckOutRequest;
import com.hotel.dto.response.FacturaResponse;
import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.Factura;
import com.hotel.entity.Hospedaje;
import com.hotel.exception.CheckOutInvalidoException;
import com.hotel.exception.ClienteNoEncontradoException;
import com.hotel.exception.HospedajeActivoNoEncontradoException;
import com.hotel.repository.ClienteRepository;
import com.hotel.repository.FacturaRepository;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HospedajeRepository;
import com.hotel.service.ServicioCheckOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckOutServiceImpl implements ServicioCheckOut {

    private final ClienteRepository clienteRepository;
    private final HospedajeRepository hospedajeRepository;
    private final FacturaRepository facturaRepository;
    private final HabitacionRepository habitacionRepository;
    private final FacturaServiceImpl facturaService;

    @Override
    @Transactional
    public FacturaResponse realizarCheckOut(CheckOutRequest request) {
        var cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNoEncontradoException(request.getClienteId()));

        Hospedaje hospedaje = hospedajeRepository.findByClienteIdAndActivoTrue(cliente.getId())
                .orElseThrow(() -> new HospedajeActivoNoEncontradoException(cliente.getId()));

        if (hospedaje.getFechaHoraSalida() != null) {
            throw new CheckOutInvalidoException("El hospedaje ya fue cerrado previamente.");
        }

        LocalDateTime fechaHoraSalida = LocalDateTime.now();
        if (fechaHoraSalida.isBefore(hospedaje.getFechaHoraIngreso())) {
            throw new CheckOutInvalidoException("La fecha de salida no puede ser anterior a la fecha de ingreso.");
        }

        if (facturaRepository.findByHospedajeId(hospedaje.getId()).isPresent()) {
            throw new CheckOutInvalidoException("Ya existe una factura generada para este hospedaje.");
        }

        hospedaje.setFechaHoraSalida(fechaHoraSalida);
        Factura factura = facturaService.crearFactura(hospedaje, fechaHoraSalida);
        hospedaje.setActivo(false);
        hospedaje.getHabitacion().setEstado(EstadoHabitacion.DISPONIBLE);

        hospedajeRepository.save(hospedaje);
        habitacionRepository.save(hospedaje.getHabitacion());

        return FacturaResponse.builder()
                .facturaId(factura.getId())
                .clienteId(cliente.getId())
                .cliente(cliente.getNombreCompleto())
                .numeroHabitacion(hospedaje.getHabitacion().getNumeroHabitacion())
                .tipoHabitacion(hospedaje.getHabitacion().getTipoHabitacion())
                .fechaHoraIngreso(hospedaje.getFechaHoraIngreso())
                .fechaHoraSalida(factura.getHospedaje().getFechaHoraSalida())
                .cantidadNoches(factura.getCantidadNoches())
                .valorNoche(factura.getValorNoche())
                .valorTotal(factura.getValorTotal())
                .fechaGeneracion(factura.getFechaGeneracion())
                .build();
    }
}
