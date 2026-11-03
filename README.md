# Taller Backend Hotel - Check-In y Check-Out

## Objetivo

Desarrollar una API REST para la administración de hospedajes de un hotel utilizando Java, Spring Boot, JPA e Hibernate.

El sistema deberá permitir registrar clientes, consultar disponibilidad de habitaciones, realizar procesos de Check-In y Check-Out, generar la factura correspondiente al hospedaje y almacenar toda la información en una base de datos relacional.

No se manejarán reservas previas.

La asignación de habitaciones se realizará únicamente durante el proceso de Check-In según la disponibilidad existente.

---

# Tecnologías Obligatorias

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Base de Datos Relacional
- Swagger/OpenAPI (solo dependencia)
- Lombok
- Bean Validation
- ResponseEntity

---

# Arquitectura Requerida

La solución deberá implementarse mediante una arquitectura desacoplada basada en contratos (interfaces).

## Estructura mínima

```text
controlador
dto
servicio
servicio.impl
repositorio
entidad
excepcion
handler
respuesta
configuracion
```

---

# Contexto del Negocio

El hotel dispone de tres tipos de habitaciones.

| Tipo | Piso | Valor por Noche |
| --- | --- | ---: |
| ESTANDAR | 1 | 300.000 |
| PREMIER | 2 | 500.000 |
| SUITE | 3 | 1.000.000 |

---

# Horarios

## Check-In

```text
04:00 PM
```

## Check-Out

```text
01:00 PM
```

---

# Distribución de Habitaciones

El hotel cuenta con un total de 15 habitaciones.

## Piso 1 - ESTANDAR

```text
101
102
103
104
105
```

## Piso 2 - PREMIER

```text
201
202
203
204
205
```

## Piso 3 - SUITE

```text
301
302
303
304
305
```

Las habitaciones deberán existir previamente en la base de datos mediante carga inicial por script SQL.

No se requiere CRUD para habitaciones.

---

# Reglas de Negocio

## Disponibilidad de Habitaciones

Para realizar un Check-In el sistema deberá buscar automáticamente una habitación disponible según el tipo solicitado.

Si existe disponibilidad:

```text
Se asigna la habitación y se registra el hospedaje.
```

Si no existe disponibilidad:

```text
Se deberá generar una excepción de negocio indicando que no existen habitaciones disponibles para el tipo solicitado.
```

---

## Hospedaje Activo

Un cliente solamente podrá tener un hospedaje activo al mismo tiempo.

---

## Historial de Hospedajes

Un cliente puede tener múltiples hospedajes históricos.

---

## Check-Out

El proceso de Check-Out solamente podrá realizarse sobre un hospedaje activo.

Al finalizar el Check-Out:

```text
Se genera la factura.
Se registra la fecha de salida.
La habitación queda disponible nuevamente.
El hospedaje cambia a estado inactivo.
```

---

## Facturación

Toda estadía finalizada mediante un proceso de Check-Out deberá generar una factura.

La factura será inmutable.

No podrá modificarse ni eliminarse.

---

## Cálculo de Noches

La cantidad de noches será calculada según las fechas y horas de ingreso y salida del hospedaje.

Toda estadía finalizada mediante Check-Out deberá generar como mínimo una (1) noche de facturación.

Si el cliente permanece hospedado después de la hora oficial de Check-Out (01:00 PM), se deberá facturar una noche adicional.

### Ejemplos

#### Ejemplo 1

```text
Ingreso:
10/10/2026 04:00 PM

Salida:
11/10/2026 12:30 PM

Resultado:
1 noche
```

#### Ejemplo 2

```text
Ingreso:
10/10/2026 04:00 PM

Salida:
11/10/2026 01:01 PM

Resultado:
2 noches
```

#### Ejemplo 3

```text
Ingreso:
10/10/2026 04:00 PM

Salida:
10/10/2026 08:00 PM

Resultado:
1 noche
```

---

## Estado de la Habitación

Cuando se realiza un Check-In:

```text
DISPONIBLE → OCUPADA
```

Cuando se realiza un Check-Out:

```text
OCUPADA → DISPONIBLE
```

---

# Alcance Funcional

## Clientes

Permitir:

```text
Crear cliente.
Consultar cliente por Id.
Consultar cliente por identificación.
Listar clientes.
```

No se requiere:

```text
Actualizar clientes.
Eliminar clientes.
```

---

## Habitaciones

Permitir:

```text
Consultar todas las habitaciones.
Consultar habitación por número.
Consultar disponibilidad por tipo.
```

No se requiere:

```text
Crear habitaciones.
Actualizar habitaciones.
Eliminar habitaciones.
```

---

## Check-In

Permitir registrar el ingreso de un cliente al hotel.

---

## Check-Out

Permitir registrar la salida de un cliente y generar automáticamente la factura.

---

## Facturas

Permitir consultar facturas generadas.

---

# Entidades Requeridas

> El estudiante deberá crear las entidades y sus relaciones.

## Cliente

### Campos

```text
id
identificacion
nombreCompleto
correoElectronico
telefono
fechaCreacion
```

---

## Habitacion

### Campos

```text
id
numeroHabitacion
piso
tipoHabitacion
valorNoche
estado
```

---

## Hospedaje

Representa la estadía del cliente.

### Campos

```text
id
cliente
habitacion
fechaHoraIngreso
fechaHoraSalida
activo
fechaCreacion
```

---

## Factura

### Campos

```text
id
hospedaje
fechaGeneracion
cantidadNoches
valorNoche
valorTotal
```

---

# Relaciones Esperadas

```text
Cliente
    1 ---- N
       Hospedaje

Habitacion
    1 ---- N
       Hospedaje

Hospedaje
    1 ---- 1
       Factura
```

---

# Contratos de Servicios

> El estudiante deberá crear las interfaces y sus implementaciones.

## ServicioCliente

### Operaciones

```text
crearCliente
consultarPorId
consultarPorIdentificacion
consultarTodos
```

---

## ServicioHabitacion

### Operaciones

```text
consultarTodas
consultarPorNumero
buscarDisponiblePorTipo
```

---

## ServicioCheckIn

### Operaciones

```text
realizarCheckIn
```

---

## ServicioCheckOut

### Operaciones

```text
realizarCheckOut
```

---

## ServicioFactura

### Operaciones

```text
consultarPorId
consultarTodas
```

---

# DTO Request

> El estudiante deberá crear los DTO de solicitud.

## ClienteRequest

### Campos

```text
identificacion
nombreCompleto
correoElectronico
telefono
```

---

## CheckInRequest

### Campos

```text
clienteId
tipoHabitacion
```

---

## CheckOutRequest

### Campos

```text
clienteId
```

---

# DTO Response

> El estudiante deberá crear los DTO de respuesta.

## ClienteResponse

### Campos

```text
id
identificacion
nombreCompleto
correoElectronico
telefono
fechaCreacion
```

---

## HabitacionResponse

### Campos

```text
id
numeroHabitacion
piso
tipoHabitacion
valorNoche
estado
```

---

## CheckInResponse

### Campos

```text
hospedajeId
clienteId
cliente
numeroHabitacion
tipoHabitacion
fechaHoraIngreso
mensaje
```

---

## FacturaResponse

### Campos

```text
facturaId
clienteId
cliente
numeroHabitacion
tipoHabitacion
fechaHoraIngreso
fechaHoraSalida
cantidadNoches
valorNoche
valorTotal
fechaGeneracion
```

---

# Respuesta Estándar de Error

## ErrorResponse

### Campos

```text
codigoHttp
error
mensaje
fechaHora
```

---

# Excepciones Personalizadas Requeridas

> El estudiante deberá crear e implementar las excepciones.

## Obligatorias

```text
ClienteNoEncontradoException

ClienteConHospedajeActivoException

HabitacionNoEncontradaException

HabitacionNoDisponibleException

HospedajeActivoNoEncontradoException

FacturaNoEncontradaException

CheckOutInvalidoException
```

---

# Validaciones Obligatorias

Utilizar Bean Validation.

Como mínimo:

```text
@NotNull
@NotBlank
@Email
@Positive
```

Validar además:

```text
Identificación obligatoria.
Nombre completo obligatorio.
Correo electrónico válido.
Teléfono obligatorio.
```

---

# Endpoints Requeridos

## Clientes

### Crear Cliente

```http
POST /api/clientes
```

### Consultar Todos

```http
GET /api/clientes
```

### Consultar por Id

```http
GET /api/clientes/{id}
```

### Consultar por Identificación

```http
GET /api/clientes/documento/{identificacion}
```

---

## Habitaciones

### Consultar Todas

```http
GET /api/habitaciones
```

### Consultar por Número

```http
GET /api/habitaciones/{numero}
```

### Consultar Disponibilidad por Tipo

```http
GET /api/habitaciones/disponibles/{tipo}
```

---

## Check-In

### Registrar Ingreso

```http
POST /api/checkin
```

---

## Check-Out

### Registrar Salida

```http
POST /api/checkout
```

---

## Facturas

### Consultar Todas

```http
GET /api/facturas
```

### Consultar Por Id

```http
GET /api/facturas/{id}
```

---

# Códigos HTTP Requeridos

| Código | Descripción |
| --- | --- |
| 200 | Consulta Exitosa |
| 201 | Creación Exitosa |
| 400 | Solicitud Inválida |
| 404 | Recurso No Encontrado |
| 409 | Conflicto de Negocio |
| 500 | Error Interno del Servidor |

---

# Documentación API

La API deberá incluir la dependencia de Swagger/OpenAPI para documentar y probar los endpoints.

No se requiere configuración avanzada adicional.

---

# 

````sql

---

# Datos Iniciales de Habitaciones

```sql
INSERT INTO habitacion
(numero_habitacion, piso, tipo_habitacion, valor_noche, estado)
VALUES
(101,1,'ESTANDAR',300000,'DISPONIBLE'),
(102,1,'ESTANDAR',300000,'DISPONIBLE'),
(103,1,'ESTANDAR',300000,'DISPONIBLE'),
(104,1,'ESTANDAR',300000,'DISPONIBLE'),
(105,1,'ESTANDAR',300000,'DISPONIBLE'),

(201,2,'PREMIER',500000,'DISPONIBLE'),
(202,2,'PREMIER',500000,'DISPONIBLE'),
(203,2,'PREMIER',500000,'DISPONIBLE'),
(204,2,'PREMIER',500000,'DISPONIBLE'),
(205,2,'PREMIER',500000,'DISPONIBLE'),

(301,3,'SUITE',1000000,'DISPONIBLE'),
(302,3,'SUITE',1000000,'DISPONIBLE'),
(303,3,'SUITE',1000000,'DISPONIBLE'),
(304,3,'SUITE',1000000,'DISPONIBLE'),
(305,3,'SUITE',1000000,'DISPONIBLE');
````

---

# Entregables

El estudiante deberá entregar:

```text
Proyecto Spring Boot funcional.

Repositorio en Git

Minimo 7 Commits por Entidades, DTO, Repositorios, Interfaces, Servicios, Controladores, Excepciones.

Entidades.

DTO Request y Response.

Repositorios.

Interfaces de servicio.

Implementaciones de servicio.

Controladores.

Manejo de excepciones.

Validaciones.

Documentación Swagger.

Script SQL de creación de base de datos.

Script SQL de carga inicial de habitaciones.

Script SQL con los datos de prueba. (Minimo 1 por cada Tipo de habitación).
```