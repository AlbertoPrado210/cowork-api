# 🏢 CoWork API — Sistema de Reservas de Sala

**Examen Práctico · Nivel 2 · CodiGo powered by TECSUP**

---

## 👤 Datos del Alumno

| Campo        | Detalle                          |
|--------------|----------------------------------|
| **Nombre**   | Alberto Jesús Prado Agurto       |
| **Email**    | albertopradoagurto@gmail.com     |
| **Curso**    | Fullstack Java — Grupo 16        |
| **Bootcamp** | CodiGo powered by TECSUP         |

---

## 📋 Descripción del Proyecto

API REST para la gestión de reservas de salas de reunión de la empresa **CoworkLima S.A.C.**  
Construida con **Spring Boot 3.x · Java 17 · Maven**, con persistencia en memoria (sin base de datos).

---

## 🚀 Cómo Ejecutar el Proyecto

### Requisitos previos
- Java 17 o superior instalado
- Maven (incluido en el proyecto vía Maven Wrapper)

### Comando de ejecución
```bash
./mvnw spring-boot:run
```
> En Windows usar: `mvnw.cmd spring-boot:run`

### Verificación de arranque
Al iniciar, el log debe mostrar:
```
Tomcat started on port 9090 (http) with context path '/'
```

### URL base
```
http://localhost:9090
```

---

## 📡 Listado de Endpoints

### 🔵 Info

| Método | Ruta        | Descripción                          | Status |
|--------|-------------|--------------------------------------|--------|
| GET    | `/api/info` | Información de la aplicación         | 200 OK |

### 🟢 Salas — `/api/salas`

| Método | Ruta              | Descripción                    | Status         |
|--------|-------------------|--------------------------------|----------------|
| GET    | `/api/salas`      | Listar todas las salas         | 200 OK         |
| GET    | `/api/salas/{id}` | Obtener una sala por ID        | 200 OK / 404   |
| POST   | `/api/salas`      | Crear una nueva sala           | 201 Created    |
| PUT    | `/api/salas/{id}` | Actualizar una sala existente  | 200 OK / 404   |
| DELETE | `/api/salas/{id}` | Eliminar una sala (+ cascada)  | 204 No Content |

### 🟡 Reservas — `/api/reservas`

| Método | Ruta                           | Descripción                                                   | Status         |
|--------|--------------------------------|---------------------------------------------------------------|----------------|
| POST   | `/api/reservas`                | Crear una reserva (estado inicial: PENDIENTE)                 | 201 Created    |
| GET    | `/api/reservas/{id}`           | Obtener reserva por ID                                        | 200 OK / 404   |
| GET    | `/api/reservas`                | Listar reservas con filtros opcionales: `estado`, `fecha`, `salaId` | 200 OK  |
| GET    | `/api/reservas/sala/{salaId}`  | Listar todas las reservas de una sala                         | 200 OK         |
| PUT    | `/api/reservas/{id}/estado`    | Cambiar estado (`?nuevoEstado=CONFIRMADA`)                    | 200 OK / 404   |
| DELETE | `/api/reservas/{id}`           | Eliminar una reserva                                          | 204 No Content |
| POST   | `/api/reservas/{id}/comprobante` | Subir archivo PDF comprobante (multipart/form-data)         | 200 OK         |

---

## 📨 Ejemplos de Request

### POST `/api/salas`
```json
{
  "codigo": "SALA-B2",
  "nombre": "Sala Pacífico",
  "capacidad": 8,
  "ubicacion": "Piso 5"
}
```

### POST `/api/reservas`
```json
{
  "salaId": 1,
  "responsable": "María López",
  "email": "maria@cowork.pe",
  "fecha": "2026-06-10",
  "horaInicio": "09:00",
  "horaFin": "10:30"
}
```

### PUT `/api/reservas/{id}/estado`
```
PUT /api/reservas/1/estado?nuevoEstado=CONFIRMADA
```

### POST `/api/reservas/{id}/comprobante`
```
POST /api/reservas/1/comprobante
Content-Type: multipart/form-data
Headers: X-Cliente-Id: CLI-001
Body: archivo = <archivo.pdf>
```

---

## 🏗️ Arquitectura en Capas

### `controller/`
Recibe las peticiones HTTP entrantes y devuelve respuestas JSON. **No contiene lógica de negocio.**
Delega toda la operación al Service correspondiente. Usa `ResponseEntity` para controlar los códigos
HTTP de respuesta (201 Created, 204 No Content, 404 Not Found).

### `service/`
Capa donde viven todas las **reglas de negocio** del sistema: estado inicial en PENDIENTE al crear
una reserva, validación de estados permitidos, campo `activa` en `true` por defecto al crear una sala,
y eliminación en cascada de reservas al borrar una sala. Coordina el Repository con el Mapper.

### `repository/`
Responsable de la **persistencia en memoria** usando `List<>` para almacenar los datos y `AtomicLong`
para generar IDs únicos y autoincrementales. Simula el comportamiento de una capa de acceso a datos
real (equivalente a un JPA Repository) sin usar base de datos.

### `model/`
Contiene las **entidades de dominio** puras (`Sala` y `Reserva`) con todos sus atributos,
incluyendo campos sensibles como `passwordInterno`. Estas clases **nunca** son devueltas
directamente por los controllers; siempre se convierten a DTOs mediante los Mappers.

### `dto/`
Objetos de transferencia de datos implementados como **Java Records** (inmutables). Separan
el contrato público de la API del modelo interno: `RequestDTO` define qué acepta la API como
entrada, y `ResponseDTO` define exactamente qué se devuelve como JSON (sin campos sensibles).

### `mapper/`
Clases con **métodos estáticos** que realizan la conversión entre entidades de dominio y DTOs.
Centralizan toda la lógica de transformación, incluyendo el cálculo del campo derivado
`descripcionCorta` en `SalaMapper`.

---

## 📁 Estructura del Proyecto

```
src/main/java/com/codigo/cowork/
├── CoworkApiApplication.java
├── controller/
│   ├── InfoController.java
│   ├── SalaController.java
│   └── ReservaController.java
├── service/
│   ├── SalaService.java
│   └── ReservaService.java
├── repository/
│   ├── SalaRepository.java
│   └── ReservaRepository.java
├── model/
│   ├── Sala.java
│   └── Reserva.java
├── dto/
│   ├── SalaRequestDTO.java
│   ├── SalaResponseDTO.java
│   ├── ReservaRequestDTO.java
│   └── ReservaResponseDTO.java
└── mapper/
    ├── SalaMapper.java
    └── ReservaMapper.java
```

---

## 🔧 Configuración

| Parámetro          | Valor              |
|--------------------|--------------------|
| Puerto del servidor | `9090`            |
| GroupId            | `com.codigo`       |
| ArtifactId         | `cowork-api`       |
| Versión Java       | `17+`              |
| Framework          | `Spring Boot 3.x`  |
| Persistencia       | En memoria (RAM)   |

---

## 📸 Evidencias (capturas en `/docs/`)

| Archivo                        | Contenido                                      |
|--------------------------------|------------------------------------------------|
| `01-arranque-puerto-9090.png`  | App arrancando en puerto 9090                  |
| `02-get-info.png`              | GET /api/info                                  |
| `03-post-sala.png`             | POST /api/salas → 201 Created                  |
| `04-get-salas.png`             | GET /api/salas → listado                       |
| `05-get-sala-id.png`           | GET /api/salas/{id} con descripcionCorta       |
| `06-put-sala.png`              | PUT /api/salas/{id} → actualización            |
| `07-post-reserva.png`          | POST /api/reservas → 201, estado PENDIENTE     |
| `08-get-reserva-id.png`        | GET /api/reservas/{id} con formato de fechas   |
| `09-get-reservas-filtros.png`  | GET /api/reservas?estado=CONFIRMADA            |
| `10-put-estado.png`            | PUT /api/reservas/{id}/estado                  |
| `11-delete-reserva.png`        | DELETE /api/reservas/{id} → 204                |
| `12-comprobante-pdf.png`       | POST /api/reservas/{id}/comprobante (PDF)      |
| `13-delete-sala-cascada.png`   | DELETE /api/salas/{id} → cascada reservas      |

---

## ⚙️ Dependencias Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
> Única dependencia obligatoria según la ficha técnica del examen.

---

*"La mejor manera de aprender Spring es construir algo con Spring." — Equipo CodiGo*
