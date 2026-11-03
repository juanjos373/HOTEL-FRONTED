package com.hotel.dto.response;

import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionResponse {

    private Long id;
    private Integer numeroHabitacion;
    private Integer piso;
    private TipoHabitacion tipoHabitacion;
    private BigDecimal valorNoche;
    private EstadoHabitacion estado;
}
