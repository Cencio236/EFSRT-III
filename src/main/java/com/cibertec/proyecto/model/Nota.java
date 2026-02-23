package com.cibertec.proyecto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notas")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Integer idNota;

    // Campos para las notas parciales que pide tu HTML
    private Double t1;
    private Double t2;
    private Double t3;
    
    @Column(name = "examen_final")
    private Double examenFinal;

    private Double promedio;

    // Campo adicional por si el controlador usa setValorNota
    @Column(name = "valor_nota")
    private Double valorNota;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Usuario alumno;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    // --- GETTERS Y SETTERS ---

    public Integer getIdNota() { return idNota; }
    public void setIdNota(Integer idNota) { this.idNota = idNota; }

    public Double getT1() { return t1; }
    public void setT1(Double t1) { this.t1 = t1; }

    public Double getT2() { return t2; }
    public void setT2(Double t2) { this.t2 = t2; }

    public Double getT3() { return t3; }
    public void setT3(Double t3) { this.t3 = t3; }

    public Double getExamenFinal() { return examenFinal; }
    public void setExamenFinal(Double examenFinal) { this.examenFinal = examenFinal; }

    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }

    public Double getValorNota() { return valorNota; }
    public void setValorNota(Double valorNota) { this.valorNota = valorNota; }

    public Usuario getAlumno() { return alumno; }
    public void setAlumno(Usuario alumno) { this.alumno = alumno; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}