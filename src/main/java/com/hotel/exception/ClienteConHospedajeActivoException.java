package com.hotel.exception;

public class ClienteConHospedajeActivoException extends RuntimeException {

    public ClienteConHospedajeActivoException(Long clienteId) {
        super("El cliente con id " + clienteId + " ya tiene un hospedaje activo.");
    }
}
