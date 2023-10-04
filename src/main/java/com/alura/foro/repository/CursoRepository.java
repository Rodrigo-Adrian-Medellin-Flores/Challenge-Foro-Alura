package com.alura.foro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alura.foro.modelo.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
    
}
