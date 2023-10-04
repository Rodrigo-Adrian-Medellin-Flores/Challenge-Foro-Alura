package com.alura.foro.dto;

import java.time.LocalDateTime;

import com.alura.foro.modelo.Respuesta;

public record DatosRespuestaRespuesta (String nombreAutor, LocalDateTime fechaCreacion, String mensaje){

    public DatosRespuestaRespuesta(Respuesta respuesta){
        this(respuesta.getAutor().getNombre(), respuesta.getFechaCreacion(), respuesta.getMensaje());
    }
    
}
