package com.hotel.controller;

import com.hotel.dto.request.CheckOutRequest;
import com.hotel.dto.response.FacturaResponse;
import com.hotel.service.ServicioCheckOut;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CheckOutController {

    private final ServicioCheckOut servicioCheckOut;

    public CheckOutController(ServicioCheckOut servicioCheckOut) {
        this.servicioCheckOut = servicioCheckOut;
    }

    @PostMapping("/checkout")
    public ResponseEntity<FacturaResponse> realizarCheckOut(@Valid @RequestBody CheckOutRequest request) {
        return ResponseEntity.ok(servicioCheckOut.realizarCheckOut(request));
    }
}
