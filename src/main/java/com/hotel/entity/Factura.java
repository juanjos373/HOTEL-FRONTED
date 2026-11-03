package com.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas", uniqueConstraints = @UniqueConstraint(columnNames = "hospedaje_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospedaje_id", nullable = false, unique = true, updatable = false)
    private Hospedaje hospedaje;

    @Column(name = "fecha_generacion", nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @Column(name = "cantidad_noches", nullable = false, updatable = false)
    private Integer cantidadNoches;

    @Column(name = "valor_noche", nullable = false, precision = 19, scale = 2, updatable = false)
    private BigDecimal valorNoche;

    @Column(name = "valor_total", nullable = false, precision = 19, scale = 2, updatable = false)
    private BigDecimal valorTotal;

    @PrePersist
    protected void onCreate() {
        if (fechaGeneracion == null) {
            fechaGeneracion = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        throw new IllegalStateException("La factura no puede ser modificada después de generarse.");
    }

    @PreRemove
    protected void onRemove() {
        throw new IllegalStateException("La factura no puede ser eliminada después de generarse.");
    }
}
