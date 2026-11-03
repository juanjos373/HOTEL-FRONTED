package com.hotel.service.impl;

import com.hotel.dto.request.ClienteRequest;
import com.hotel.dto.response.ClienteResponse;
import com.hotel.entity.Cliente;
import com.hotel.exception.ClienteNoEncontradoException;
import com.hotel.repository.ClienteRepository;
import com.hotel.service.ServicioCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ServicioCliente {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponse crearCliente(ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .identificacion(request.getIdentificacion())
                .nombreCompleto(request.getNombreCompleto())
                .correoElectronico(request.getCorreoElectronico())
                .telefono(request.getTelefono())
                .build();

        Cliente guardado = clienteRepository.save(cliente);
        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse consultarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException(id));
        return mapToResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse consultarPorIdentificacion(String identificacion) {
        Cliente cliente = clienteRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ClienteNoEncontradoException(identificacion));
        return mapToResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> consultarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ClienteResponse mapToResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .identificacion(cliente.getIdentificacion())
                .nombreCompleto(cliente.getNombreCompleto())
                .correoElectronico(cliente.getCorreoElectronico())
                .telefono(cliente.getTelefono())
                .fechaCreacion(cliente.getFechaCreacion())
                .build();
    }
}
