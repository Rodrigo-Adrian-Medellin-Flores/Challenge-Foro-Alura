package com.alura.foro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(

    @NotBlank
    String titulo,

    @NotBlank
    String mensaje,

    @NotNull
    Long autorID,

    @NotNull
    Long cursoID
) implements DatosRecibidosTopico{

	@Override
	public Long id() {
        return null;
	}
    
}
