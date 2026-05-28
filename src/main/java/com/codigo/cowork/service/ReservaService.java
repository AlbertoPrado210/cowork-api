package com.codigo.cowork.service;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.mapper.ReservaMapper;
import com.codigo.cowork.model.Reserva;
import com.codigo.cowork.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private static final List<String> ESTADOS_VALIDOS = List.of("PENDIENTE", "CONFIRMADA", "CANCELADA");

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        Reserva reserva = ReservaMapper.toModel(dto);

        reserva.setEstado("PENDIENTE");

        Reserva guardada = reservaRepository.save(reserva);
        return ReservaMapper.toResponse(guardada);
    }

    public Optional<ReservaResponseDTO> obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .map(ReservaMapper::toResponse);
    }

    public List<ReservaResponseDTO> listarConFiltros(String estado, LocalDate fecha, Long salaId) {
        return reservaRepository.findByFiltros(estado, fecha, salaId)
                .stream()
                .map(ReservaMapper::toResponse)
                .toList();
    }

    public List<ReservaResponseDTO> listarPorSala(Long salaId) {
        return reservaRepository.findBySalaId(salaId)
                .stream()
                .map(ReservaMapper::toResponse)
                .toList();
    }

    public Optional<ReservaResponseDTO> cambiarEstado(Long id, String nuevoEstado) {
        String estadoUpper = nuevoEstado.toUpperCase();

        if (!ESTADOS_VALIDOS.contains(estadoUpper)) {
            throw new RuntimeException(
                "Estado inválido: '" + nuevoEstado + "'. Los valores permitidos son: "
                + String.join(", ", ESTADOS_VALIDOS)
            );
        }

        return reservaRepository.findById(id).map(reserva -> {
            reserva.setEstado(estadoUpper);
            reservaRepository.save(reserva);
            return ReservaMapper.toResponse(reserva);
        });
    }

    public boolean eliminar(Long id) {
        if (reservaRepository.findById(id).isEmpty()) {
            return false;
        }
        reservaRepository.deleteById(id);
        return true;
    }
}
