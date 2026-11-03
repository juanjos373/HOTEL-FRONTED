package com.hotel.service.impl;

import com.hotel.dto.response.HabitacionResponse;
import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.Habitacion;
import com.hotel.entity.TipoHabitacion;
import com.hotel.exception.HabitacionNoEncontradaException;
import com.hotel.repository.HabitacionRepository;
import com.hotel.service.ServicioHabitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements ServicioHabitacion {

    private final HabitacionRepository habitacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> consultarTodas() {
        return habitacionRepository.findAllByOrderByNumeroHabitacionAsc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse consultarPorNumero(Integer numeroHabitacion) {
        Habitacion habitacion = habitacionRepository.findByNumeroHabitacion(numeroHabitacion)
                .orElseThrow(() -> new HabitacionNoEncontradaException(numeroHabitacion));
        return toResponse(habitacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> buscarDisponiblePorTipo(TipoHabitacion tipoHabitacion) {
        return habitacionRepository.findByEstadoAndTipoHabitacionOrderByNumeroHabitacionAsc(
                        EstadoHabitacion.DISPONIBLE, tipoHabitacion)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private HabitacionResponse toResponse(Habitacion habitacion) {
        return HabitacionResponse.builder()
                .id(habitacion.getId())
                .numeroHabitacion(habitacion.getNumeroHabitacion())
                .piso(habitacion.getPiso())
                .tipoHabitacion(habitacion.getTipoHabitacion())
                .valorNoche(habitacion.getValorNoche())
                .estado(habitacion.getEstado())
                .build();
    }
}
