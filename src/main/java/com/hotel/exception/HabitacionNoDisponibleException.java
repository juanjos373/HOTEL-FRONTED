package com.hotel.exception;

import com.hotel.entity.TipoHabitacion;

public class HabitacionNoDisponibleException extends RuntimeException {

    public HabitacionNoDisponibleException(TipoHabitacion tipoHabitacion) {
        super("No hay habitaciones disponibles del tipo " + tipoHabitacion + ".");
    }
}
