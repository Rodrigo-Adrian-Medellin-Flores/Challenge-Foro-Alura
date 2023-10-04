package com.alura.foro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuestaConTopico (

@NotNull
Long topicoID, 

@NotBlank
String mensaje,

@NotNull
Long autorID
) {

    public DatosRegistroRespuestaConTopico(Long topicoID, DatosRegistroRespuesta datosRegistroRespuesta){
        this(topicoID, datosRegistroRespuesta.mensaje(), datosRegistroRespuesta.autorID());
    }
    
}
