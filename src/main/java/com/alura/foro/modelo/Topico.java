package com.alura.foro.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.alura.foro.dto.StatusTopico;
import com.alura.foro.exception.ValidacionException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "Titulo")
	private String titulo;

    @EqualsAndHashCode.Include
    @Column(name = "Mensaje")
	private String mensaje;

    @Column(name = "Fecha_Creacion")
	private LocalDateTime fechaCreacion;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING) 
	private StatusTopico status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Autor_ID")
	private Usuario autor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Curso_ID")
	private Curso curso;

    @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
	private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(String titulo, String mensaje, Usuario autor, Curso curso){
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.status = StatusTopico.NO_RESPONDIDO;
        this.autor = autor;
        this.curso = curso;
    }

    public Topico(String titulo, String mensaje){
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    public Topico actualizarDatos(String titulo, String mensaje, Usuario autor, Curso curso) {

        if(titulo != null){
            if(!titulo.isBlank()){
                this.titulo = titulo;
            } else {
                throw new ValidacionException("El topico debe contener un titulo");
            }
        }

        if(mensaje != null){
            if(!mensaje.isBlank()){
                this.mensaje = mensaje;
            } else {
                throw new ValidacionException("El topico debe contener un mensaje");
            }
        }

        if(autor != null){
            this.autor = autor;
        }

        if(curso != null){
            this.curso = curso;
        }

        return this;
    }

    
}
