package com.alura.foro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alura.foro.dto.DatosRegistroRespuestaConTopico;
import com.alura.foro.exception.ValidacionException;
import com.alura.foro.modelo.Respuesta;
import com.alura.foro.modelo.Topico;
import com.alura.foro.modelo.Usuario;
import com.alura.foro.repository.RespuestaRepository;
import com.alura.foro.repository.TopicoRepository;
import com.alura.foro.repository.UsuarioRepository;
import com.alura.foro.validation.ValidadorRespuestas;

@Service
public class RespuestaService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RespuestaRepository respuestaRepository;

    private List<ValidadorRespuestas> validadores;

    public RespuestaService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, RespuestaRepository respuestaRepository, List<ValidadorRespuestas> validadores){
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.respuestaRepository = respuestaRepository;
        this.validadores = validadores;
    }

    public Respuesta registrarRespuesta(DatosRegistroRespuestaConTopico datosRegistroRespuestaConTopico) {
        validarRespuesta(datosRegistroRespuestaConTopico);
        Topico topico = topicoRepository.getReferenceById(datosRegistroRespuestaConTopico.topicoID());
        Usuario autor = usuarioRepository.getReferenceById(datosRegistroRespuestaConTopico.autorID());
        Respuesta respuesta = new Respuesta(datosRegistroRespuestaConTopico.mensaje(), topico, autor);
        return respuestaRepository.save(respuesta);
    }

    private void validarRespuesta(DatosRegistroRespuestaConTopico datosRegistroRespuestaConTopico) {
        Long topicoId = datosRegistroRespuestaConTopico.topicoID();
        Long autorId = datosRegistroRespuestaConTopico.autorID();

        if(topicoId != null){
            if(!topicoRepository.existsById(topicoId)){
                throw new ValidacionException("El topico especificado no es válido");
            }
        }

        if(autorId != null){
            if(!usuarioRepository.existsById(autorId)){
                throw new ValidacionException("El autor especificado no es válido");
            }
        }

        validadores.forEach(v -> v.validar(datosRegistroRespuestaConTopico));

    }    
    
}
