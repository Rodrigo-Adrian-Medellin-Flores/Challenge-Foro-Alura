package com.alura.foro.validation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alura.foro.dto.DatosRecibidosTopico;
import com.alura.foro.exception.ValidacionException;
import com.alura.foro.modelo.Topico;
import com.alura.foro.repository.TopicoRepository;

@Component
public class TopicoRepetido implements ValidadorTopicos{

    private final TopicoRepository topicoRepository;

    public TopicoRepetido(TopicoRepository topicoRepository){
        this.topicoRepository = topicoRepository;
    }

    @Override
    public <T extends DatosRecibidosTopico> void validar(T datosTopico) {

        String titulo = datosTopico.titulo();
        String mensaje = datosTopico.mensaje();
        
        if(titulo == null && mensaje == null){
            return;
        }

        List<Topico> topicos = topicoRepository.findAll();

        Topico topicoParaRevisar;
        
        if(titulo != null && mensaje != null){
            topicoParaRevisar = new Topico(titulo, mensaje);
        } else {
            Topico topicoActual = topicoRepository.getReferenceById(datosTopico.id());
            if(titulo == null){
                topicoParaRevisar = new Topico(topicoActual.getTitulo(), mensaje);
            } else if (mensaje == null){
                topicoParaRevisar = new Topico(titulo, topicoActual.getMensaje());
            }
            return;
        }

        if(topicos.contains(topicoParaRevisar)){
            throw new ValidacionException("Ya existe un topico con ese titulo y mensaje");
        }
    }
    
}
