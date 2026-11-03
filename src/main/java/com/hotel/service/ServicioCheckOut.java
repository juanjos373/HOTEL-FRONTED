package com.hotel.service;

import com.hotel.dto.request.CheckOutRequest;
import com.hotel.dto.response.FacturaResponse;

public interface ServicioCheckOut {

    FacturaResponse realizarCheckOut(CheckOutRequest request);
}
