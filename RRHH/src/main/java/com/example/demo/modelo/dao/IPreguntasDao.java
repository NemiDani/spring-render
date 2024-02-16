package com.example.demo.modelo.dao;

import com.example.demo.modelo.Preguntas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPreguntasDao extends CrudRepository<Preguntas,Integer> {

    @Query("SELECT p FROM Preguntas p WHERE p.categoria = ?1")
    List<Preguntas> findByCategoria(String categoria);




}
