package com.alura.foro.modelo;

import java.util.ArrayList;
import java.util.List;

import com.alura.foro.dto.DatosRegistroCurso;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre")
	private String nombre;

    /*@OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
	private List<Topico> topicos = new ArrayList<>();
    */

    public Curso(DatosRegistroCurso datosRegistroCurso){
        this.nombre = datosRegistroCurso.nombre();
    }

    public void actualizarDatos(DatosRegistroCurso datosRegistroCurso){
        this.nombre = datosRegistroCurso.nombre();
    }
    
    /* 
    @Column(name = "Categoria")
	private String categoria;
    */
}
