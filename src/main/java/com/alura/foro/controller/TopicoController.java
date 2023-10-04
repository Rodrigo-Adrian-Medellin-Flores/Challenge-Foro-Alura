package com.alura.foro.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
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

import com.alura.foro.dto.DatosEdicionTopico;
import com.alura.foro.dto.DatosEdicionTopicoConId;
import com.alura.foro.dto.DatosRegistroTopico;
import com.alura.foro.dto.DatosRespuestaRespuesta;
import com.alura.foro.dto.DatosRespuestaTopico;
import com.alura.foro.dto.DatosRespuestaTopicoConRespuestas;
import com.alura.foro.modelo.Topico;
import com.alura.foro.repository.RespuestaRepository;
import com.alura.foro.repository.TopicoRepository;
import com.alura.foro.service.TopicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/foro/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private final TopicoService topicoService;
    private final TopicoRepository topicoRepository;
    private final RespuestaRepository respuestaRepository;

    public TopicoController(TopicoService topicoService, TopicoRepository topicoRepository, RespuestaRepository respuestaRepository){
        this.topicoService = topicoService;
        this.topicoRepository = topicoRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @Operation(summary = "Agrega nuevo topico al foro - Requiere Usuario autenticado")
    @PostMapping("/nuevo")
    @Secured("ROLE_USER")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder){
        Topico topicoRegistrado = topicoService.registrarTopico(datosRegistroTopico);
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topicoRegistrado);
        URI url = uriComponentsBuilder.path("/foro/topicos/{id}").buildAndExpand(topicoRegistrado.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @Operation(summary = "Listado de topicos en el foro - Acceso Libre")
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> listarTopicos(@PageableDefault(page = 0, size = 2, sort = {"fechaCreacion"}, direction = Direction.DESC)Pageable pageable){
        return ResponseEntity.ok(topicoRepository.findAll(pageable).map(DatosRespuestaTopico::new));
    }

    @Operation(summary = "Detalle de topico con respuestas - Acceso Libre")
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopicoConRespuestas> detallarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        List<DatosRespuestaRespuesta>  respuestas = respuestaRepository.findAllByTopico(topico).stream().map(DatosRespuestaRespuesta::new).toList();
        DatosRespuestaTopicoConRespuestas datosRespuestaTopicoConRespuestas = new DatosRespuestaTopicoConRespuestas(topico, respuestas);
        return ResponseEntity.ok(datosRespuestaTopicoConRespuestas);
    }

    @Operation(summary = "Editar topico del foro - Requiere Usuario autenticado/Autor de topico")
    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    @PreAuthorize("@jwtFilter.isTopicAuthor(#id)")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> editarTopico(@Param("id") @PathVariable Long id, @RequestBody @Valid DatosEdicionTopico datosEdicionTopico){
        DatosEdicionTopicoConId datosEdicionTopicoConId = new DatosEdicionTopicoConId(id, datosEdicionTopico);
        Topico topicoActualizado = topicoService.editarTopico(datosEdicionTopicoConId);
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topicoActualizado);
        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @Operation(summary = "Eliminar topico del foro - Requiere permiso ADMIN")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    


    
}
