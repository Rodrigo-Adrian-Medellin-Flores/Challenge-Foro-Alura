package com.alura.foro.dto;

import com.alura.foro.modelo.Curso;

public record DatosRespuestaCurso(Long id, String nombre) {
    public DatosRespuestaCurso(Curso curso){
        this(curso.getId(), curso.getNombre());
    }
}
