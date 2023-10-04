package com.alura.foro.dto;

public record DatosEdicionTopico(String titulo, String mensaje, Long autorID, Long cursoID) implements DatosRecibidosTopico{

    @Override
    public Long id() {
        return null;
    }
    
}
