package com.codigo.cowork.controller;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO creada = reservaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return reservaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ReservaResponseDTO> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) Long salaId) {
        return reservaService.listarConFiltros(estado, fecha, salaId);
    }

    @GetMapping("/sala/{salaId}")
    public List<ReservaResponseDTO> listarPorSala(@PathVariable Long salaId) {
        return reservaService.listarPorSala(salaId);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        return reservaService.cambiarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = reservaService.eliminar(id);
        return eliminado
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


    @PostMapping("/{id}/comprobante")
    public ResponseEntity<Map<String, Object>> subirComprobante(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestHeader("X-Cliente-Id") String clienteId) {

        return ResponseEntity.ok(Map.of(
                "reservaId",   id,
                "clienteId",   clienteId,
                "nombreArchivo", archivo.getOriginalFilename(),
                "tamañoBytes",   archivo.getSize()
        ));
    }
}
