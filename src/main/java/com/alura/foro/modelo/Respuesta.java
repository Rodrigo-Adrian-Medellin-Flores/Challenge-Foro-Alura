package com.alura.foro.modelo;

import java.time.LocalDateTime;

import com.alura.foro.dto.DatosEdicionRespuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "respuestas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Mensaje")
	private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Topico_ID")
	private Topico topico;

    @Column(name = "Fecha_Creacion")
	private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "Autor_ID")
	private Usuario autor;

    @Column(name = "Solucion")
	private Boolean solucion;

    public Respuesta(String mensaje, Topico topico, Usuario autor){
        this.mensaje = mensaje;
        this.topico = topico;
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;
        this.solucion = false;
    }

    public void actualizarDatos(@Valid DatosEdicionRespuesta datosEdicionRespuesta) {
        this.mensaje = datosEdicionRespuesta.mensaje();
        this.fechaCreacion = LocalDateTime.now();
    }
    
}
