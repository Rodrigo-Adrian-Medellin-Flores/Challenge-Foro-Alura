package com.alura.foro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroUsuario(

@NotBlank
String nombre,

@NotBlank
@Email
String email,

@NotBlank
@Size(min = 8, max = 32)
String contrasena

) {
    
}
