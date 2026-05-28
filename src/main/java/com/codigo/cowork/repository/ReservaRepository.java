package com.codigo.cowork.repository;

import com.codigo.cowork.model.Reserva;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservaRepository {

    private final List<Reserva> reservas = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public List<Reserva> findAll() {
        return new ArrayList<>(reservas);
    }

    public Optional<Reserva> findById(Long id) {
        return reservas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public List<Reserva> findBySalaId(Long salaId) {
        return reservas.stream()
                .filter(r -> r.getSalaId().equals(salaId))
                .toList();
    }

    public Reserva save(Reserva reserva) {
        if (reserva.getId() == null) {
            reserva.setId(contadorId.getAndIncrement());
            reservas.add(reserva);
        } else {
            for (int i = 0; i < reservas.size(); i++) {
                if (reservas.get(i).getId().equals(reserva.getId())) {
                    reservas.set(i, reserva);
                    break;
                }
            }
        }
        return reserva;
    }

    public void deleteById(Long id) {
        reservas.removeIf(r -> r.getId().equals(id));
    }

    public void deleteBySalaId(Long salaId) {
        reservas.removeIf(r -> r.getSalaId().equals(salaId));
    }

    public List<Reserva> findByFiltros(String estado, LocalDate fecha, Long salaId) {
        return reservas.stream()
                .filter(r -> estado  == null || r.getEstado().equalsIgnoreCase(estado))
                .filter(r -> fecha   == null || r.getFecha().equals(fecha))
                .filter(r -> salaId  == null || r.getSalaId().equals(salaId))
                .toList();
    }
}
