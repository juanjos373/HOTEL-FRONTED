package com.hotel.dto.request;

import com.hotel.entity.TipoHabitacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInRequest {

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser positivo")
    private Long clienteId;

    @NotNull(message = "El tipo de habitación es obligatorio")
    private TipoHabitacion tipoHabitacion;
}
