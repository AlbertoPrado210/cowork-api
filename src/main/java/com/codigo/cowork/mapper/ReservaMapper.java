package com.codigo.cowork.mapper;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.model.Reserva;

public class ReservaMapper {

    private ReservaMapper() {
    }

    public static Reserva toModel(ReservaRequestDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setSalaId(dto.salaId());
        reserva.setResponsable(dto.responsable());
        reserva.setEmail(dto.email());
        reserva.setFecha(dto.fecha());
        reserva.setHoraInicio(dto.horaInicio());
        reserva.setHoraFin(dto.horaFin());
        return reserva;
    }

    public static ReservaResponseDTO toResponse(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getSalaId(),
                reserva.getResponsable(),
                reserva.getEmail(),
                reserva.getFecha(),
                reserva.getHoraInicio(),
                reserva.getHoraFin(),
                reserva.getEstado()
        );
    }
}
