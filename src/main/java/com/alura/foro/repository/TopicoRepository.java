package com.alura.foro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alura.foro.modelo.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>{
}
