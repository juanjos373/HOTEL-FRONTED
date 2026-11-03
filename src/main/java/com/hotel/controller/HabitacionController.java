package com.hotel.controller;

import com.hotel.dto.response.HabitacionResponse;
import com.hotel.entity.TipoHabitacion;
import com.hotel.service.ServicioHabitacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final ServicioHabitacion servicioHabitacion;

    public HabitacionController(ServicioHabitacion servicioHabitacion) {
        this.servicioHabitacion = servicioHabitacion;
    }

    @GetMapping
    public ResponseEntity<List<HabitacionResponse>> consultarTodas() {
        return ResponseEntity.ok(servicioHabitacion.consultarTodas());
    }

    @GetMapping("/{numero}")
    public ResponseEntity<HabitacionResponse> consultarPorNumero(@PathVariable Integer numero) {
        return ResponseEntity.ok(servicioHabitacion.consultarPorNumero(numero));
    }

    @GetMapping("/disponibles/{tipo}")
    public ResponseEntity<List<HabitacionResponse>> consultarDisponiblesPorTipo(@PathVariable TipoHabitacion tipo) {
        return ResponseEntity.ok(servicioHabitacion.buscarDisponiblePorTipo(tipo));
    }
}
