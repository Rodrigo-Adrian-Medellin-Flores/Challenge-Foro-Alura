package com.alura.foro.dto;

import java.time.LocalDateTime;

import com.alura.foro.modelo.Topico;

public record DatosRespuestaTopico(String titulo, String mensaje, LocalDateTime fechaCreacion, StatusTopico status, String nombreAutor, String nombreCurso) {

    public DatosRespuestaTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre());
    }
    
}
