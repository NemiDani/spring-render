package com.example.demo.response;

import com.example.demo.modelo.Preguntas;

import java.util.List;

public class PreguntaResponseRest extends ResponseRest{

	private PreguntaResponse preguntaResponse = new PreguntaResponse();

	public PreguntaResponse getPreguntaResponse() {
		return preguntaResponse;
	}

	public void setPreguntaResponse(PreguntaResponse preguntaResponse) {
		this.preguntaResponse = preguntaResponse;
	}

	public void setEmpleadoResponse(PreguntaResponse preguntaResponse) {
		this.preguntaResponse = preguntaResponse;
	}

	public void setPreguntas(List<Preguntas> preguntasPorCategoria) {
	}
}
