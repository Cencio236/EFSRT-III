package com.cibertec.proyecto.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActividad;
    
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private String tipo; // EXAMEN, TAREA, EVENTO

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario creador;

    // --- GETTERS Y SETTERS ---
    public Integer getIdActividad() { return idActividad; }
    public void setIdActividad(Integer idActividad) { this.idActividad = idActividad; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }
}