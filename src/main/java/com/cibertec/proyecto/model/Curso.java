package com.cibertec.proyecto.model;
import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCurso;
    private String nombreCurso;
    private String codigoSeccion;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Usuario docente;

    // Getters y Setters
    public Integer getIdCurso() { return idCurso; }
    public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }
    public String getCodigoSeccion() { return codigoSeccion; }
    public void setCodigoSeccion(String codigoSeccion) { this.codigoSeccion = codigoSeccion; }
    public Usuario getDocente() { return docente; }
    public void setDocente(Usuario docente) { this.docente = docente; }
}