package com.hotel.exception;

public class FacturaNoEncontradaException extends RuntimeException {

    public FacturaNoEncontradaException(Long facturaId) {
        super("No se encontró la factura con id: " + facturaId);
    }
}
