package com.hotel.dto.response;

import com.hotel.entity.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaResponse {

    private Long facturaId;
    private Long clienteId;
    private String cliente;
    private Integer numeroHabitacion;
    private TipoHabitacion tipoHabitacion;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private Integer cantidadNoches;
    private BigDecimal valorNoche;
    private BigDecimal valorTotal;
    private LocalDateTime fechaGeneracion;
}
