package com.alura.foro.dto;

import jakarta.validation.constraints.NotNull;

public record DatosEdicionTopicoConId(@NotNull Long id, String titulo, String mensaje, Long autorID, Long cursoID) implements DatosRecibidosTopico{

    public DatosEdicionTopicoConId(Long id, DatosEdicionTopico datosEdicionTopico){
        this(id, datosEdicionTopico.titulo(), datosEdicionTopico.mensaje(), datosEdicionTopico.autorID(), datosEdicionTopico.cursoID());
    }
    
}
