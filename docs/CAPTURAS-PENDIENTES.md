# 📸 Guía de Capturas Requeridas por el Examen

Guarda cada captura en formato PNG con el nombre exacto indicado.

## ✅ Lista de capturas

| # | Archivo PNG a guardar              | Qué debe mostrar                                                    | Herramienta     |
|---|------------------------------------|---------------------------------------------------------------------|-----------------|
| 1 | `01-arranque-puerto-9090.png`      | Terminal/IntelliJ con el log: "Tomcat started on port 9090"         | IntelliJ / CMD  |
| 2 | `02-get-info.png`                  | GET http://localhost:9090/api/info → JSON con nombre, versión, autor | Postman        |
| 3 | `03-post-sala.png`                 | POST /api/salas → respuesta 201 con descripcionCorta                | Postman         |
| 4 | `04-get-salas.png`                 | GET /api/salas → array JSON de todas las salas                      | Postman         |
| 5 | `05-get-sala-id.png`               | GET /api/salas/1 → sala con campo descripcionCorta                  | Postman         |
| 6 | `06-put-sala.png`                  | PUT /api/salas/1 → sala actualizada, status 200                     | Postman         |
| 7 | `07-post-reserva.png`              | POST /api/reservas → 201, estado "PENDIENTE" aunque no se envíe    | Postman         |
| 8 | `08-get-reserva-id.png`            | GET /api/reservas/1 → fechas formato yyyy-MM-dd y HH:mm             | Postman         |
| 9 | `09-get-reservas-filtros.png`      | GET /api/reservas?estado=CONFIRMADA (o con más filtros)             | Postman         |
|10 | `10-put-estado.png`                | PUT /api/reservas/1/estado?nuevoEstado=CONFIRMADA → 200             | Postman         |
|11 | `11-delete-reserva.png`            | DELETE /api/reservas/1 → 204 No Content (body vacío)                | Postman         |
|12 | `12-comprobante-pdf.png`           | POST /api/reservas/1/comprobante con PDF → nombre y tamaño          | Postman         |
|13 | `13-delete-sala-cascada.png`       | DELETE /api/salas/1 → 204, y GET reservas de esa sala da []         | Postman         |

## 🔑 Datos clave para las pruebas en Postman

### Headers necesarios para el comprobante:
- Header: `X-Cliente-Id` con valor: `CLI-001`
- Body: `form-data`, campo `archivo`, tipo File, seleccionar cualquier PDF

### Filtros de reservas (URL de ejemplo):
```
GET http://localhost:9090/api/reservas?estado=CONFIRMADA&fecha=2026-06-10
```

### Estados válidos para cambio de estado:
- `PENDIENTE`
- `CONFIRMADA`
- `CANCELADA`
