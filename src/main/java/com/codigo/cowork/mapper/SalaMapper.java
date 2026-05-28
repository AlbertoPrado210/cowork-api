package com.codigo.cowork.mapper;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.model.Sala;

public class SalaMapper {

    private SalaMapper() {
    }

    public static Sala toModel(SalaRequestDTO dto) {
        Sala sala = new Sala();
        sala.setCodigo(dto.codigo());
        sala.setNombre(dto.nombre());
        sala.setCapacidad(dto.capacidad());
        sala.setUbicacion(dto.ubicacion());
        return sala;
    }

    public static SalaResponseDTO toResponse(Sala sala) {
        String descripcionCorta = sala.getCodigo() + " - " + sala.getNombre()
                + " (cap. " + sala.getCapacidad() + ")";

        return new SalaResponseDTO(
                sala.getId(),
                sala.getCodigo(),
                sala.getNombre(),
                sala.getCapacidad(),
                sala.getUbicacion(),
                sala.isActiva(),
                descripcionCorta
        );
    }
}
