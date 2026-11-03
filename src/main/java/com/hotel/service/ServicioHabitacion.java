package com.hotel.service;

import com.hotel.dto.response.HabitacionResponse;
import com.hotel.entity.TipoHabitacion;

import java.util.List;

public interface ServicioHabitacion {

    List<HabitacionResponse> consultarTodas();

    HabitacionResponse consultarPorNumero(Integer numeroHabitacion);

    List<HabitacionResponse> buscarDisponiblePorTipo(TipoHabitacion tipoHabitacion);
}
