package com.hotel.exception;

public class HabitacionNoEncontradaException extends RuntimeException {

    public HabitacionNoEncontradaException(Long habitacionId) {
        super("No se encontró la habitación con id: " + habitacionId);
    }

    public HabitacionNoEncontradaException(Integer numeroHabitacion) {
        super("No se encontró la habitación con número: " + numeroHabitacion);
    }
}
