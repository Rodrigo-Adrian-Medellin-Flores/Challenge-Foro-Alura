package com.alura.foro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alura.foro.dto.DatosRegistroTopico;
import com.alura.foro.dto.DatosEdicionTopicoConId;
import com.alura.foro.dto.DatosRecibidosTopico;
import com.alura.foro.exception.ValidacionException;
import com.alura.foro.modelo.Curso;
import com.alura.foro.modelo.Topico;
import com.alura.foro.modelo.Usuario;
import com.alura.foro.repository.CursoRepository;
import com.alura.foro.repository.TopicoRepository;
import com.alura.foro.repository.UsuarioRepository;
import com.alura.foro.validation.ValidadorTopicos;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    private List<ValidadorTopicos> validadores;

    public TopicoService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository, List<ValidadorTopicos> validadores){
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.validadores = validadores;
    }

    public Topico registrarTopico(DatosRegistroTopico datosRegistroTopico){
        validarTopico(datosRegistroTopico);
        Usuario usuario = usuarioRepository.getReferenceById(datosRegistroTopico.autorID());
        Curso curso = cursoRepository.getReferenceById(datosRegistroTopico.cursoID());
        Topico topico = new Topico(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje(), usuario, curso);
        return topicoRepository.save(topico);
    }

    public Topico editarTopico(DatosEdicionTopicoConId datosEdicionTopicoConId){
        validarTopico(datosEdicionTopicoConId);
        
        Long topicoID = datosEdicionTopicoConId.id();
        Long autorID = datosEdicionTopicoConId.autorID();
        Long cursoID = datosEdicionTopicoConId.cursoID();

        Topico topico = topicoRepository.getReferenceById(topicoID);
        Usuario usuario = null;
        Curso curso = null;

        if(autorID != null){
            usuario = usuarioRepository.getReferenceById(datosEdicionTopicoConId.autorID());
        }

        if(cursoID != null){
            curso = cursoRepository.getReferenceById(datosEdicionTopicoConId.cursoID());
        }

        return topico.actualizarDatos(datosEdicionTopicoConId.titulo(), datosEdicionTopicoConId.mensaje(), usuario, curso);
    }

    private <T extends DatosRecibidosTopico> void validarTopico(T datosTopico){
        
        Long topicoID = datosTopico.id();
        Long autorID = datosTopico.autorID();
        Long cursoID = datosTopico.cursoID();

        if(topicoID != null){
            if(!topicoRepository.existsById(topicoID)){
                throw new ValidacionException("El topico especificado no es válido");
            }
        }

        if(autorID != null){
            if(!usuarioRepository.existsById(autorID)){
                throw new ValidacionException("El autor especificado no es válido");
            }
        }

        if(cursoID != null){
            if(!cursoRepository.existsById(cursoID)){
                throw new ValidacionException("El curso especificado no es válido");
            }
        }

        validadores.forEach(v -> v.validar(datosTopico));
    }
    
}
