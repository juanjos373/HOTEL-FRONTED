package com.hotel.service;

import com.hotel.dto.request.ClienteRequest;
import com.hotel.dto.response.ClienteResponse;

import java.util.List;

public interface ServicioCliente {

    ClienteResponse crearCliente(ClienteRequest request);

    ClienteResponse consultarPorId(Long id);

    ClienteResponse consultarPorIdentificacion(String identificacion);

    List<ClienteResponse> consultarTodos();
}
