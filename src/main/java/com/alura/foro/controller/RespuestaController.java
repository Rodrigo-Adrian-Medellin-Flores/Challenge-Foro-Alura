package com.alura.foro.controller;

import java.net.URI;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.foro.dto.DatosEdicionRespuesta;
import com.alura.foro.dto.DatosRegistroRespuesta;
import com.alura.foro.dto.DatosRegistroRespuestaConTopico;
import com.alura.foro.dto.DatosRespuestaRespuesta;
import com.alura.foro.modelo.Respuesta;
import com.alura.foro.repository.RespuestaRepository;
import com.alura.foro.service.RespuestaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/foro")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    private final RespuestaService respuestaService;
    private final RespuestaRepository respuestaRepository;

    public RespuestaController(RespuestaService respuestaService, RespuestaRepository respuestaRepository){
        this.respuestaService = respuestaService;
        this.respuestaRepository = respuestaRepository;
    }

    @Operation(summary = "Postear respuesta en topico - Requiere Usuario autenticado")
    @PostMapping("/topicos/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<DatosRespuestaRespuesta> registrarRespuesta(@PathVariable Long id, @RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta, UriComponentsBuilder uriComponentsBuilder){
        DatosRegistroRespuestaConTopico datosRegistroRespuestaConTopico = new DatosRegistroRespuestaConTopico(id, datosRegistroRespuesta);
        Respuesta respuestaRegistrada = respuestaService.registrarRespuesta(datosRegistroRespuestaConTopico);
        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuestaRegistrada);
        URI url = uriComponentsBuilder.path("/foro/post/{id}").buildAndExpand(respuestaRegistrada.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaRespuesta);
    }

    @Operation(summary = "Detallar respuesta especificada - Requiere Usuario autenticado")
    @GetMapping("/post/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<DatosRespuestaRespuesta> detallarRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuesta);
        return ResponseEntity.ok(datosRespuestaRespuesta);
    }

    @Operation(summary = "Editar respuesta publicada - Requiere Usuario autenticado/Autor de respuesta")
    @PutMapping("/post/{id}")
    @Secured("ROLE_USER")
    @PreAuthorize("@jwtFilter.isResponseAuthor(#id)")
    @Transactional
    public ResponseEntity<DatosRespuestaRespuesta> editarRespuesta(@Param("id") @PathVariable Long id, @RequestBody @Valid DatosEdicionRespuesta datosEdicionRespuesta){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarDatos(datosEdicionRespuesta);
        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuesta);
        return ResponseEntity.ok(datosRespuestaRespuesta);
    }

    @Operation(summary = "Eliminar respuesta publicada - Requiere permiso ADMIN")
    @DeleteMapping("/post/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
