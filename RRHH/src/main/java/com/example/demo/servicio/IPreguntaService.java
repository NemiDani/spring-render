package com.example.demo.servicio;

import com.example.demo.modelo.Preguntas;
import org.springframework.http.ResponseEntity;


import com.example.demo.response.PreguntaResponseRest;

public interface IPreguntaService {

	public ResponseEntity<PreguntaResponseRest> buscarPreguntas();
	public ResponseEntity<PreguntaResponseRest> aleatorio();

	public ResponseEntity<PreguntaResponseRest> buscarPorId(int id);
	public ResponseEntity<PreguntaResponseRest> buscarPorCategoria(String categoria);
	public ResponseEntity<PreguntaResponseRest> crear(Preguntas pregunta);
	public ResponseEntity<PreguntaResponseRest> actualizar(Preguntas pregunta, int id);
	public ResponseEntity<PreguntaResponseRest> eliminar (int id);

}
