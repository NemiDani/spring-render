package com.example.demo.controlador;

import com.example.demo.modelo.Preguntas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.response.PreguntaResponseRest;
import com.example.demo.servicio.IPreguntaService;

@RestController
@RequestMapping("/trivial")

public class PreguntaController {
	
	@Autowired
	private IPreguntaService service;

	
	@GetMapping("/pregunta")
	public ResponseEntity<PreguntaResponseRest> consultaCat() {
		
		ResponseEntity<PreguntaResponseRest> response = service.buscarPreguntas();
		return response;
	}

	@GetMapping("/pregunta/categoria/{categoria}")
	public ResponseEntity<PreguntaResponseRest> consultaPorCategoria(@PathVariable String categoria) {
		ResponseEntity<PreguntaResponseRest> response = service.buscarPorCategoria(categoria);
		return response;
	}

	@GetMapping("/pregunta/random")
	public ResponseEntity<PreguntaResponseRest> aleatorio(){
		ResponseEntity<PreguntaResponseRest> response = service.aleatorio();
		return response;
	}
	
	@GetMapping("/pregunta/{id}")
	public ResponseEntity<PreguntaResponseRest> consultaPorId(@PathVariable int id) {
		ResponseEntity<PreguntaResponseRest> response = service.buscarPorId(id);
		return response;
	}
	
	
	@PostMapping("/pregunta")
	public ResponseEntity<PreguntaResponseRest> crear(@RequestBody Preguntas request) {
		ResponseEntity<PreguntaResponseRest> response = service.crear(request);
		return response;
	}
	
	@PutMapping("/pregunta/{id}")
	public ResponseEntity<PreguntaResponseRest> actualizar (@RequestBody Preguntas request, @PathVariable int id) {
		ResponseEntity<PreguntaResponseRest> response = service.actualizar(request, id);
		return response;
	}
	
	@DeleteMapping("/pregunta/{id}")
	public ResponseEntity<PreguntaResponseRest> eliminar (@PathVariable int id) {
		ResponseEntity<PreguntaResponseRest> response = service.eliminar(id);
		return response;
	}

}
