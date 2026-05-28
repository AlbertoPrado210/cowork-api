package com.codigo.cowork.service;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.mapper.SalaMapper;
import com.codigo.cowork.model.Sala;
import com.codigo.cowork.repository.ReservaRepository;
import com.codigo.cowork.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;

    public SalaService(SalaRepository salaRepository, ReservaRepository reservaRepository) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
    }

    public List<SalaResponseDTO> listarTodas() {
        return salaRepository.findAll()
                .stream()
                .map(SalaMapper::toResponse)
                .toList();
    }

    public Optional<SalaResponseDTO> obtenerPorId(Long id) {
        return salaRepository.findById(id)
                .map(SalaMapper::toResponse);
    }

    public SalaResponseDTO crear(SalaRequestDTO dto) {
        Sala sala = SalaMapper.toModel(dto);

        sala.setActiva(true);

        Sala guardada = salaRepository.save(sala);
        return SalaMapper.toResponse(guardada);
    }

    public Optional<SalaResponseDTO> actualizar(Long id, SalaRequestDTO dto) {
        return salaRepository.findById(id).map(salaExistente -> {
            salaExistente.setCodigo(dto.codigo());
            salaExistente.setNombre(dto.nombre());
            salaExistente.setCapacidad(dto.capacidad());
            salaExistente.setUbicacion(dto.ubicacion());
            Sala actualizada = salaRepository.save(salaExistente);
            return SalaMapper.toResponse(actualizada);
        });
    }

    public boolean eliminar(Long id) {
        if (!salaRepository.existsById(id)) {
            return false;
        }
        reservaRepository.deleteBySalaId(id);
        salaRepository.deleteById(id);
        return true;
    }
}
