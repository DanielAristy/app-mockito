package org.example.services;

import org.example.models.Examen;
import org.example.repositories.ExamenRepository;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {
    private ExamenRepository repository;

    public ExamenServiceImpl(ExamenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return repository.findAll()
                .stream()
                .filter(examen -> nombre.equals(examen.getNombre()))
                .findFirst();

    }
}
