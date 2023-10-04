package com.alura.foro.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosEdicionRespuesta (@NotBlank String mensaje){
    
}
