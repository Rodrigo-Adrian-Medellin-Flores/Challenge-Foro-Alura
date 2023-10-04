package com.alura.foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alura.foro.modelo.Respuesta;
import com.alura.foro.modelo.Topico;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long>{
    public List<Respuesta> findAllByTopico(Topico topico);
}
