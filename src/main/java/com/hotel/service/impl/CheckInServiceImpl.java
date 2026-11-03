package com.hotel.service.impl;

import com.hotel.dto.request.CheckInRequest;
import com.hotel.dto.response.CheckInResponse;
import com.hotel.entity.Cliente;
import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.Habitacion;
import com.hotel.entity.Hospedaje;
import com.hotel.exception.ClienteConHospedajeActivoException;
import com.hotel.exception.ClienteNoEncontradoException;
import com.hotel.exception.HabitacionNoDisponibleException;
import com.hotel.repository.ClienteRepository;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HospedajeRepository;
import com.hotel.service.ServicioCheckIn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInServiceImpl implements ServicioCheckIn {

    private final ClienteRepository clienteRepository;
    private final HabitacionRepository habitacionRepository;
    private final HospedajeRepository hospedajeRepository;

    @Override
    @Transactional
    public CheckInResponse realizarCheckIn(CheckInRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNoEncontradoException(request.getClienteId()));

        if (hospedajeRepository.findByClienteIdAndActivoTrue(cliente.getId()).isPresent()) {
            throw new ClienteConHospedajeActivoException(cliente.getId());
        }

        List<Habitacion> habitacionesDisponibles = habitacionRepository
                .findByEstadoAndTipoHabitacionForUpdate(EstadoHabitacion.DISPONIBLE, request.getTipoHabitacion());

        if (habitacionesDisponibles.isEmpty()) {
            throw new HabitacionNoDisponibleException(request.getTipoHabitacion());
        }

        Habitacion habitacion = habitacionesDisponibles.get(0);
        habitacion.setEstado(EstadoHabitacion.OCUPADA);

        Hospedaje hospedaje = Hospedaje.builder()
                .cliente(cliente)
                .habitacion(habitacion)
                .fechaHoraIngreso(LocalDateTime.now())
                .activo(true)
                .build();

        Hospedaje guardado = hospedajeRepository.save(hospedaje);
        habitacionRepository.save(habitacion);

        return CheckInResponse.builder()
                .hospedajeId(guardado.getId())
                .clienteId(cliente.getId())
                .cliente(cliente.getNombreCompleto())
                .numeroHabitacion(habitacion.getNumeroHabitacion())
                .tipoHabitacion(habitacion.getTipoHabitacion())
                .fechaHoraIngreso(guardado.getFechaHoraIngreso())
                .mensaje("Check-in realizado correctamente.")
                .build();
    }
}
