package org.example.services;

import org.example.models.Examen;
import org.example.repositories.ExamenRepository;
import org.example.repositories.PreguntasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

    @Mock
    ExamenRepository repository;
    @Mock
    PreguntasRepository preguntasRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findExamenPorNombre() throws InterruptedException {

        when(repository.findAll()).thenReturn(Datos.EXAMENES);
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

    @Test
    void testPreguntaExamen() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntasRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);

        Examen examen = service.findExamenPorNombreConPreguntas("Sociales");

        assertEquals(4, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Integrales"));
    }

    @Test
    void testPreguntaExamenVerify() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntasRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);

        Examen examen = service.findExamenPorNombreConPreguntas("Sociales");

        assertEquals(4, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Integrales"));
        verify(repository).findAll();
        verify(preguntasRepository).findPreguntasPorExamenId(5L);
    }

    @Test
    void testNoExisteExamenVerify() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        //when(preguntasRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);

        Examen examen = service.findExamenPorNombreConPreguntas("Sociales");

        assertNull(examen);
        verify(repository).findAll();
        //verify(preguntasRepository).findPreguntasPorExamenId(5L);
    }

    @Test
    void testGuardarExamenSinPregutnas(){
        when(repository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);
        Examen examen = service.guardar(Datos.EXAMEN);

        assertNotNull(examen.getId());
        assertEquals(45L, examen.getId());
        assertEquals("Artistica", examen.getNombre());
        verify(repository).guardar(any(Examen.class));
    }

    @Test
    void testGuardarExamenConPregutnas(){
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);
        when(repository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);
        Examen examen = service.guardar(newExamen);
        examen.setPreguntas(Datos.PREGUNTAS);

        assertNotNull(examen.getId());
        assertEquals(45L, examen.getId());
        assertEquals("Artistica", examen.getNombre());
        verify(repository).guardar(any(Examen.class));
        verify(preguntasRepository).guardarVarias(Datos.PREGUNTAS);
    }

    @Test
    void testManeroException(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntasRepository.findPreguntasPorExamenId(anyLong())).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.findExamenPorNombreConPreguntas("Español");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(repository).findAll();
        verify(preguntasRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testArgumentMatches(){
         when(repository.findAll()).thenReturn(Datos.EXAMENES);
         when(preguntasRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
         service.findExamenPorNombreConPreguntas("Informatica");

         verify(repository).findAll();
         verify(preguntasRepository).findPreguntasPorExamenId(argThat(arg -> arg != null && arg == 7L));
    }

    @Test
    void testArgumentMatchersTwo(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(preguntasRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Informatica");

        verify(repository).findAll();
        verify(preguntasRepository).findPreguntasPorExamenId(argThat(new MiArgMatchers()));
    }

    public static class MiArgMatchers implements ArgumentMatcher<Long>{
        private Long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }


        @Override
        public String toString() {
            return "Mensaje personalizado de error que imprime mockito en caso de que falle el test " +
                    argument + " deber ser entero positivo";
        }
    }
}