package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.modelo.Preguntas;
import com.example.demo.modelo.dao.IPreguntasDao;
import com.example.demo.response.PreguntaResponseRest;
import com.example.demo.servicio.PreguntaServiceImpl;

public class PreguntasServiceControllerTest {

    @Mock
    private IPreguntasDao preguntasDao;

    @InjectMocks
    private PreguntaServiceImpl preguntaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuscarPreguntas() {

        List<Preguntas> preguntas = new ArrayList<>();

        Preguntas pregunta1 = new Preguntas();
        pregunta1.setId(1);
        pregunta1.setPregunta("¿Cuál es la capital de Francia?");
        preguntas.add(pregunta1);

        Preguntas pregunta2 = new Preguntas();
        pregunta2.setId(2);
        pregunta2.setPregunta("¿En qué año fue la Revolución Francesa?");
        preguntas.add(pregunta2);

        when(preguntasDao.findAll()).thenReturn(preguntas);

        ResponseEntity<PreguntaResponseRest> responseEntity = preguntaService.buscarPreguntas();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }


    @Test
    public void testBuscarPorId_PreguntaEncontrada() {

        Preguntas pregunta = new Preguntas();
        pregunta.setId(1);
        pregunta.setPregunta("¿Cuál es la capital de Francia?");
        Optional<Preguntas> preguntaOptional = Optional.of(pregunta);


        when(preguntasDao.findById(1)).thenReturn(preguntaOptional);


        ResponseEntity<PreguntaResponseRest> responseEntity = preguntaService.buscarPorId(1);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }


    @Test
    public void testBuscarPorCategoria_PreguntaNoEncontrada() {

        List<Preguntas> pregunta = new ArrayList<>();

        when(preguntasDao.findByCategoria("CategoriaNoExistente")).thenReturn(pregunta);

        ResponseEntity<PreguntaResponseRest> responseEntity = preguntaService.buscarPorCategoria("CategoriaNoExistente");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    public void testAleatorio_PreguntaNoEncontrada() {

        when(preguntasDao.count()).thenReturn(0L);

        ResponseEntity<PreguntaResponseRest> responseEntity = preguntaService.aleatorio();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }


    @Test
    public void testCrear_PreguntaGuardadaExitosamente() {

        Preguntas preguntaAGuardar = new Preguntas();
        preguntaAGuardar.setId(1);
        preguntaAGuardar.setPregunta("¿Cuál es el océano más grande del mundo?");

        when(preguntasDao.save(preguntaAGuardar)).thenReturn(preguntaAGuardar);

        ResponseEntity<PreguntaResponseRest> responseEntity = preguntaService.crear(preguntaAGuardar);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }


    @Test
    public void testActualizar_PreguntaActualizadaExitosamente() {

        Preguntas preguntaExistente = new Preguntas();
        preguntaExistente.setId(1);
        preguntaExistente.setPregunta("¿Cuál es el río más largo del mundo?");

        Preguntas preguntaActualizada = new Preguntas();
        preguntaActualizada.setId(1);
        preguntaActualizada.setPregunta("¿Cuál es el río más largo de América del Sur?");

        when(preguntasDao.findById(1)).thenReturn(Optional.of(preguntaExistente));

        when(preguntasDao.save(preguntaExistente)).thenReturn(preguntaActualizada);

        ResponseEntity<PreguntaResponseRest> responseEntity = preguntaService.actualizar(preguntaActualizada, 1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }


    @Test
    public void testEliminar_PreguntaEliminadaExitosamente() {

        doNothing().when(preguntasDao).deleteById(1);

        ResponseEntity<PreguntaResponseRest> responseEntity = preguntaService.eliminar(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }









}





