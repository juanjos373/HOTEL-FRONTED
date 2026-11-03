package com.hotel.service;

import com.hotel.dto.request.CheckInRequest;
import com.hotel.dto.response.CheckInResponse;

public interface ServicioCheckIn {

    CheckInResponse realizarCheckIn(CheckInRequest request);
}
