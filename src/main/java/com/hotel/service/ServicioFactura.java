package com.hotel.service;

import com.hotel.dto.response.FacturaResponse;

import java.util.List;

public interface ServicioFactura {

    FacturaResponse consultarPorId(Long facturaId);

    List<FacturaResponse> consultarTodas();
}
