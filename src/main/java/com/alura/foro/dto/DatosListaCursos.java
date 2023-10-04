package com.alura.foro.dto;

import com.alura.foro.modelo.Curso;

public record DatosListaCursos(String nombre) {
    public DatosListaCursos(Curso curso){
        this(curso.getNombre());
    }
}
