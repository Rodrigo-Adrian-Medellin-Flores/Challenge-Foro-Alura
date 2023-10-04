package com.alura.foro.validation;

import com.alura.foro.dto.DatosRecibidosTopico;

public interface ValidadorTopicos {
    public <T extends DatosRecibidosTopico> void validar(T datosTopico);
}
