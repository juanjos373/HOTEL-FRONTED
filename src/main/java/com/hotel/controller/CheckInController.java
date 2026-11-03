package com.hotel.controller;

import com.hotel.dto.request.CheckInRequest;
import com.hotel.dto.response.CheckInResponse;
import com.hotel.service.ServicioCheckIn;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CheckInController {

    private final ServicioCheckIn servicioCheckIn;

    public CheckInController(ServicioCheckIn servicioCheckIn) {
        this.servicioCheckIn = servicioCheckIn;
    }

    @PostMapping("/checkin")
    public ResponseEntity<CheckInResponse> realizarCheckIn(@Valid @RequestBody CheckInRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioCheckIn.realizarCheckIn(request));
    }
}
