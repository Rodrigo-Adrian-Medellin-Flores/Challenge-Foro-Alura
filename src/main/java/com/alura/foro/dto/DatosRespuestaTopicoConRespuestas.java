package com.alura.foro.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.alura.foro.modelo.Topico;

public record DatosRespuestaTopicoConRespuestas(String titulo, String mensaje, LocalDateTime fechaCreacion, StatusTopico status, String nombreAutor, String nombreCurso, Integer numeroRespuestas, List<DatosRespuestaRespuesta> respuestas) {

    public DatosRespuestaTopicoConRespuestas (Topico topico, List<DatosRespuestaRespuesta> respuestas){
        this(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre(), respuestas.size(), respuestas);
    }
    
}
