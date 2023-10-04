package com.alura.foro.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

import com.alura.foro.dto.DatosListaCursos;
import com.alura.foro.dto.DatosRegistroCurso;
import com.alura.foro.dto.DatosRespuestaCurso;
import com.alura.foro.modelo.Curso;
import com.alura.foro.repository.CursoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    private final CursoRepository cursoRepository;

    public CursoController(CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    @Operation(summary = "Registro de nuevos cursos - Requiere permiso ADMIN")
    @PostMapping("/nuevo")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<DatosRespuestaCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso, UriComponentsBuilder uriComponentsBuilder){
        Curso cursoParaRegistrar = new Curso(datosRegistroCurso);
        Curso cursoRegistrado = cursoRepository.save(cursoParaRegistrar);
        DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(cursoRegistrado);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(cursoRegistrado.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCurso);
    }

    @Operation(summary = "Listado de cursos - Acceso Libre")
    @GetMapping
    public ResponseEntity<Page<DatosListaCursos>> listarCursos(@PageableDefault(page = 0, size = 2, sort = {"nombre"}) Pageable pageable){
        return ResponseEntity.ok(cursoRepository.findAll(pageable).map(DatosListaCursos::new));
    }

    @Operation(summary = "Datos del curso especificado - Acceso Libre")
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> detallarCurso(@PathVariable Long id){
        Curso curso = cursoRepository.getReferenceById(id);
        DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(curso);
        return ResponseEntity.ok(datosRespuestaCurso);
    }

    @Operation(summary = "Editar datos del curso - Requiere permiso ADMIN")
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> editarCurso(@PathVariable Long id, @RequestBody @Valid DatosRegistroCurso datosRegistroCurso){
        Curso curso = cursoRepository.getReferenceById(id);
        curso.actualizarDatos(datosRegistroCurso);
        DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(curso);
        return ResponseEntity.ok(datosRespuestaCurso);
    }

    @Operation(summary = "Eliminar curso - Requiere permiso ADMIN")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity eliminarCurso(@PathVariable Long id){
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
