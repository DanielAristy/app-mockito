package org.example.services;

import org.example.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {

    public final static List<Examen> EXAMENES = List.of(new Examen(1L, "Español"),
            new Examen(2L, "Matematicas"), new Examen(3L, "Ciencias Naturales"),
            new Examen(4L, "Religion"), new Examen(5L, "Sociales"),
            new Examen(6L, "Ingles"), new Examen(7L, "Informatica")
    );

    public final static List<String> PREGUNTAS = Arrays.asList("Aritmetica", "Integrales", "Derivadas", "Geometria");
}
