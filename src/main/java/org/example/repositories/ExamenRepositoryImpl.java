package org.example.repositories;

import org.example.models.Examen;

import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository {
    @Override
    public List<Examen> findAll() {
        return List.of(
                new Examen(1L, "Español"),
                new Examen(2L, "Matematicas"),
                new Examen(3L, "Ciencias Naturales"),
                new Examen(4L, "Religion"),
                new Examen(5L, "Sociales"),
                new Examen(6L, "Ingles"),
                new Examen(7L, "Informatica")
        );
    }
}
