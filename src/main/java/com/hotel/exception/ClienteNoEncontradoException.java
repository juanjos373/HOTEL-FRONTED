package com.hotel.exception;

public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(Long clienteId) {
        super("No se encontró el cliente con id: " + clienteId);
    }

    public ClienteNoEncontradoException(String identificacion) {
        super("No se encontró el cliente con identificación: " + identificacion);
    }
}
