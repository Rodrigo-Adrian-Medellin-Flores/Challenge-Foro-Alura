package com.alura.foro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta (

@NotBlank
String mensaje,

@NotNull
Long autorID
) {
    
}
