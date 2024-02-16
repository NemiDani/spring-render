package com.example.demo.servicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.demo.modelo.Preguntas;
import com.example.demo.modelo.dao.IPreguntasDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.demo.response.PreguntaResponseRest;



@Service
public class

PreguntaServiceImpl implements IPreguntaService {

	private static final Logger log = LoggerFactory.getLogger(PreguntaServiceImpl.class);
	
	@Autowired
	private IPreguntasDao preguntasDao;

	/***************************************************************************************************************************************
	***************************************************************************************************************************************/
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<PreguntaResponseRest> buscarPreguntas() {
		
		log.info("inicio metodo buscarPreguntas()");
		
		PreguntaResponseRest response = new PreguntaResponseRest();
		
		try {
			
			List<Preguntas> pregunta = (List<Preguntas>) preguntasDao.findAll();
			
			response.getPreguntaResponse().setCategoria(pregunta);
			
			response.setMetada("Respuesta ok", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetada("Respuesta nok", "-1", "Error al consultar pregunta");
			log.error("error al consultar preguntas: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	/***************************************************************************************************************************************
	***************************************************************************************************************************************/



	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<PreguntaResponseRest> buscarPorId(int id) {
		
		log.info("Inicio metodo buscarPorId)");
		
		PreguntaResponseRest response = new PreguntaResponseRest();
		List<Preguntas> list = new ArrayList<>();
		
		try {
			
			Optional<Preguntas> pregunta = preguntasDao.findById(id);
			
			if (pregunta.isPresent()) {
				list.add(pregunta.get());
				response.getPreguntaResponse().setCategoria(list);
			} else {
				log.error("Error en consultar pregunta");
				response.setMetada("Respuesta nok", "-1", "Pregunta no encontrada");
				return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("Error en consultar pregunta");
			response.setMetada("Respuesta nok", "-1", "Error al consultar pregunta");
			return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setMetada("Respuesta ok", "00", "Respuesta exitosa");
		return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	/***************************************************************************************************************************************
	***************************************************************************************************************************************/

		@Override
		public ResponseEntity<PreguntaResponseRest> buscarPorCategoria(String categoria) {

			log.info("Inicio metodo buscarPorCategoria)");

			PreguntaResponseRest response = new PreguntaResponseRest();
			List<Preguntas> list = new ArrayList<>();

			try {

				List<Preguntas> pregunta = preguntasDao.findByCategoria(categoria);

				if (!pregunta.isEmpty()) {
					response.getPreguntaResponse().setPregunta(pregunta);
				} else {
					log.error("Error en consultar pregunta");
					response.setMetada("Respuesta nok", "-1", "Pregunta no encontrada");
					return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				log.error("Error en consultar pregunta");
				response.setMetada("Respuesta nok", "-1", "Error al consultar pregunta");
				return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.setMetada("Respuesta ok", "00", "Respuesta exitosa");
			return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.OK); //devuelve 200


		}

	/***************************************************************************************************************************************
	***************************************************************************************************************************************/

	@Override
	public ResponseEntity<PreguntaResponseRest> aleatorio() {
		PreguntaResponseRest response = new PreguntaResponseRest();

		try {

			long totalPreguntas = preguntasDao.count();

			if (totalPreguntas > 0) {

				int randomIndex = (int) (Math.random() * totalPreguntas) + 1;
				Optional<Preguntas> preguntaAleatoria = preguntasDao.findById(randomIndex);

				if (preguntaAleatoria.isPresent()) {
					response.getPreguntaResponse().setPregunta(Collections.singletonList(preguntaAleatoria.get()));
					/*aqui casteo a collection, porque es la única forma de que funcione no sé por qué.
					Intento que me devuelva una lista con find by id, pero imposible ya que es innerit del crudRepository
					y si la creo yo aposta con @query da conflicto en otras :(, y creando un setter donde le
					pases una pregunta por parámetro tampoco*/

				} else {
					response.setMetada("Respuesta nok", "-1", "Pregunta aleatoria no encontrada");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			} else {
				response.setMetada("Respuesta nok", "-1", "No hay preguntas en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error al obtener pregunta aleatoria", e.getMessage());
			response.setMetada("Respuesta nok", "-1", "Error al obtener pregunta aleatoria");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setMetada("Respuesta ok", "00", "Pregunta aleatoria encontrada");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	/***************************************************************************************************************************************
	 ***************************************************************************************************************************************/


	@Override
	@Transactional
	public ResponseEntity<PreguntaResponseRest> crear(Preguntas pregunta) {
		log.info("Inicio metodo crear pregunta");
		
		PreguntaResponseRest response = new PreguntaResponseRest();
		List<Preguntas> list = new ArrayList<>();
		
		try {
			
			Preguntas preguntaGuardada = preguntasDao.save(pregunta);
			
			if( preguntaGuardada != null) {
				list.add(preguntaGuardada);
				response.getPreguntaResponse().setCategoria(list);
			} else {
				log.error("Error en grabar pregunta");
				response.setMetada("Respuesta nok", "-1", "Pregunta no guardada");
				return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch( Exception e) {
			log.error("Error en grabar empleado");
			response.setMetada("Respuesta nok", "-1", "Error al grabar empleado");
			return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setMetada("Respuesta ok", "00", "Empleado creado");
		return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	/***************************************************************************************************************************************
	 ***************************************************************************************************************************************/

	@Override
	@Transactional
	public ResponseEntity<PreguntaResponseRest> actualizar(Preguntas pregunta, int id) {
		
		log.info("Inicio metodo actualizar");
		
		PreguntaResponseRest response = new PreguntaResponseRest();
		List<Preguntas> list = new ArrayList<>();
		
		try {
			
			Optional<Preguntas> preguntaBuscado = preguntasDao.findById(id);
			
			if (preguntaBuscado.isPresent()) {
				preguntaBuscado.get().setPregunta(pregunta.getPregunta());

				 
				 Preguntas preguntaActualizar = preguntasDao.save(preguntaBuscado.get()); //actualizando
				 
				 if( preguntaActualizar != null ) {
					 response.setMetada("Respuesta ok", "00", "Pregunta actualizada");
					 list.add(preguntaActualizar);
					 response.getPreguntaResponse().setCategoria(list);
				 } else {
					 log.error("error en actualizar categoria");
					 response.setMetada("Respuesta nok", "-1", "Empleado no actualizado");
					 return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.BAD_REQUEST);
				 }
				 
				 
			} else {
				log.error("error en actualizar pregunta");
				response.setMetada("Respuesta nok", "-1", "Pregunta no actualizado");
				return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch ( Exception e) {
			log.error("error en actualizar pregunta", e.getMessage());
			e.getStackTrace();
			response.setMetada("Respuesta nok", "-1", "Pregunta no actualizado");
			return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.OK);
		
	}

	/***************************************************************************************************************************************
	 ***************************************************************************************************************************************/

	@Override
	@Transactional
	public ResponseEntity<PreguntaResponseRest> eliminar(int id) {
		
		log.info("Inicio metodo eliminar categoria");
		
		PreguntaResponseRest response = new PreguntaResponseRest();
		
		 try {
			 
			 //eliminamos el registro
			 preguntasDao.deleteById(id);
			 response.setMetada("Respuesta ok", "00", "pregunta eliminada");
			 
		 } catch (Exception e) {
			log.error("error en eliminar pregunta", e.getMessage());
			e.getStackTrace();
			response.setMetada("Respuesta nok", "-1", "Categoria no eliminada");
			return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 
		 return new ResponseEntity<PreguntaResponseRest>(response, HttpStatus.OK);
		
	}




}
