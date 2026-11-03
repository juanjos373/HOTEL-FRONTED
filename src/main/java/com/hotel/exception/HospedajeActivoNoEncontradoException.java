package com.hotel.exception;

public class HospedajeActivoNoEncontradoException extends RuntimeException {

    public HospedajeActivoNoEncontradoException(Long clienteId) {
        super("No se encontró un hospedaje activo para el cliente con id: " + clienteId);
    }
}
