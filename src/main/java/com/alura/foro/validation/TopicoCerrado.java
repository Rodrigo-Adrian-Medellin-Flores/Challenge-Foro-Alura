package com.alura.foro.validation;

import org.springframework.stereotype.Component;

import com.alura.foro.dto.DatosRegistroRespuestaConTopico;
import com.alura.foro.dto.StatusTopico;
import com.alura.foro.exception.ValidacionException;
import com.alura.foro.modelo.Topico;
import com.alura.foro.repository.TopicoRepository;

@Component
public class TopicoCerrado implements ValidadorRespuestas{

    private final TopicoRepository topicoRepository;

    public TopicoCerrado(TopicoRepository topicoRepository){
        this.topicoRepository = topicoRepository;
    }

    @Override
    public void validar(DatosRegistroRespuestaConTopico datosRegistroRespuestaConTopico) {
    
        Long topicoId = datosRegistroRespuestaConTopico.topicoID();

        Topico topico = topicoRepository.getReferenceById(topicoId);

        if(topico.getStatus() == StatusTopico.CERRADO){
            throw new ValidacionException("Este topico ya se encuentra cerrado, no es posible registar más respuestas");
        }
        
    }
    
}
