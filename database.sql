CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    identificacion VARCHAR(50) NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL,
    telefono VARCHAR(30) NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_clientes_identificacion (identificacion)
);

CREATE TABLE IF NOT EXISTS habitaciones (
    id BIGINT NOT NULL AUTO_INCREMENT,
    numero_habitacion INT NOT NULL,
    piso INT NOT NULL,
    tipo_habitacion VARCHAR(20) NOT NULL,
    valor_noche DECIMAL(19,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_habitacion_numero (numero_habitacion),
    CHECK (tipo_habitacion IN ('ESTANDAR', 'PREMIER', 'SUITE')),
    CHECK (estado IN ('DISPONIBLE', 'OCUPADA'))
);

CREATE TABLE IF NOT EXISTS hospedajes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    habitacion_id BIGINT NOT NULL,
    fecha_hora_ingreso DATETIME NOT NULL,
    fecha_hora_salida DATETIME NULL,
    activo BOOLEAN NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_hospedaje_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_hospedaje_habitacion FOREIGN KEY (habitacion_id) REFERENCES habitaciones(id)
);

CREATE TABLE IF NOT EXISTS facturas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    hospedaje_id BIGINT NOT NULL,
    fecha_generacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cantidad_noches INT NOT NULL,
    valor_noche DECIMAL(19,2) NOT NULL,
    valor_total DECIMAL(19,2) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_factura_hospedaje (hospedaje_id),
    CONSTRAINT fk_factura_hospedaje FOREIGN KEY (hospedaje_id) REFERENCES hospedajes(id)
);
