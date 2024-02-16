package com.example.demo.repositorio;

import com.example.demo.modelo.Preguntas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreguntasRepositorio extends JpaRepository<Preguntas, Long> {



}
