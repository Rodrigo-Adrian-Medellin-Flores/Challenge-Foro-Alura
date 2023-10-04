package com.alura.foro.dto;

import com.alura.foro.modelo.Curso;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroCurso(

@NotBlank
String nombre
) {
    
    public DatosRegistroCurso(Curso curso){
        this(curso.getNombre());
    }

}

