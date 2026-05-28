package com.codigo.cowork.repository;

import com.codigo.cowork.model.Sala;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SalaRepository {

    private final List<Sala> salas = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public List<Sala> findAll() {
        return new ArrayList<>(salas);
    }

    public Optional<Sala> findById(Long id) {
        return salas.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public Sala save(Sala sala) {
        if (sala.getId() == null) {

            sala.setId(contadorId.getAndIncrement());
            salas.add(sala);
        } else {

            for (int i = 0; i < salas.size(); i++) {
                if (salas.get(i).getId().equals(sala.getId())) {
                    salas.set(i, sala);
                    break;
                }
            }
        }
        return sala;
    }

    public void deleteById(Long id) {
        salas.removeIf(s -> s.getId().equals(id));
    }

    public boolean existsById(Long id) {
        return salas.stream().anyMatch(s -> s.getId().equals(id));
    }
}
