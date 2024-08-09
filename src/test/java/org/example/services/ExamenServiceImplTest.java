package org.example.services;

import org.example.models.Examen;
import org.example.repositories.ExamenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamenServiceImplTest {

    ExamenRepository repository;
    ExamenService service;

    @BeforeEach
    void setUp() {
        repository = mock(ExamenRepository.class);
        service = new ExamenServiceImpl(repository);
    }

    @Test
    void findExamenPorNombre() throws InterruptedException {

        List<Examen> datos = List.of(new Examen(1L, "Español"),
                new Examen(2L, "Matematicas"), new Examen(3L, "Ciencias Naturales"),
                new Examen(4L, "Religion"), new Examen(5L, "Sociales"),
                new Examen(6L, "Ingles"), new Examen(7L, "Informatica")
        );

        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        assertEquals(2L, examen.orElseThrow().getId());
        assertEquals("Matematicas", examen.orElseThrow().getNombre());
    }

    @Test
    void findExamenPorNombreListaVacia() {
        List<Examen> datos = Collections.emptyList();
        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        assertFalse(examen.isPresent());
    }

}