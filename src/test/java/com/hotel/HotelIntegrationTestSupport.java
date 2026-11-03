package com.hotel;

import com.hotel.dto.request.ClienteRequest;
import com.hotel.entity.EstadoHabitacion;
import com.hotel.entity.Habitacion;
import com.hotel.entity.TipoHabitacion;
import com.hotel.repository.ClienteRepository;
import com.hotel.repository.FacturaRepository;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HospedajeRepository;
import com.hotel.service.impl.CheckInServiceImpl;
import com.hotel.service.impl.CheckOutServiceImpl;
import com.hotel.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
abstract class HotelIntegrationTestSupport {

    @Autowired
    protected ClienteServiceImpl clienteService;

    @Autowired
    protected CheckInServiceImpl checkInService;

    @Autowired
    protected CheckOutServiceImpl checkOutService;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected HabitacionRepository habitacionRepository;

    @Autowired
    protected HospedajeRepository hospedajeRepository;

    @Autowired
    protected FacturaRepository facturaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUpDatabase() {
        jdbcTemplate.execute("DELETE FROM facturas");
        jdbcTemplate.execute("DELETE FROM hospedajes");
        jdbcTemplate.execute("DELETE FROM clientes");
        jdbcTemplate.execute("DELETE FROM habitaciones");

        habitacionRepository.saveAll(List.of(
                habitacion(101, 1, TipoHabitacion.ESTANDAR, "300000"),
                habitacion(102, 1, TipoHabitacion.ESTANDAR, "300000"),
                habitacion(103, 1, TipoHabitacion.ESTANDAR, "300000"),
                habitacion(201, 2, TipoHabitacion.PREMIER, "500000"),
                habitacion(202, 2, TipoHabitacion.PREMIER, "500000"),
                habitacion(301, 3, TipoHabitacion.SUITE, "1000000")
        ));
    }

    protected Long crearCliente(String identificacion) {
        return clienteService.crearCliente(new ClienteRequest(
                identificacion,
                "Cliente de prueba",
                identificacion + "@test.com",
                "3001234567"
        )).getId();
    }

    private Habitacion habitacion(int numero, int piso, TipoHabitacion tipo, String valorNoche) {
        return Habitacion.builder()
                .numeroHabitacion(numero)
                .piso(piso)
                .tipoHabitacion(tipo)
                .valorNoche(new BigDecimal(valorNoche))
                .estado(EstadoHabitacion.DISPONIBLE)
                .build();
    }
}
