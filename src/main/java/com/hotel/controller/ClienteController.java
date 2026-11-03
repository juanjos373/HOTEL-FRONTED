package com.hotel.controller;

import com.hotel.dto.request.ClienteRequest;
import com.hotel.dto.response.ClienteResponse;
import com.hotel.service.ServicioCliente;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ServicioCliente servicioCliente;

    public ClienteController(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = servicioCliente.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> consultarTodos() {
        return ResponseEntity.ok(servicioCliente.consultarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioCliente.consultarPorId(id));
    }

    @GetMapping("/documento/{identificacion}")
    public ResponseEntity<ClienteResponse> consultarPorIdentificacion(@PathVariable String identificacion) {
        return ResponseEntity.ok(servicioCliente.consultarPorIdentificacion(identificacion));
    }
}
