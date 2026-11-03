package com.hotel.exception;

public class CheckOutInvalidoException extends RuntimeException {

    public CheckOutInvalidoException(String mensaje) {
        super(mensaje);
    }
}
