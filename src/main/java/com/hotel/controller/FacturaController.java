package com.hotel.controller;

import com.hotel.dto.response.FacturaResponse;
import com.hotel.service.ServicioFactura;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final ServicioFactura servicioFactura;

    public FacturaController(ServicioFactura servicioFactura) {
        this.servicioFactura = servicioFactura;
    }

    @GetMapping
    public ResponseEntity<List<FacturaResponse>> consultarTodos() {
        return ResponseEntity.ok(servicioFactura.consultarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioFactura.consultarPorId(id));
    }
}
