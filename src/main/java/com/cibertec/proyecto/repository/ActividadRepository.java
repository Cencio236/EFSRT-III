package com.cibertec.proyecto.repository;

import com.cibertec.proyecto.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
    // Aquí podrías agregar métodos para filtrar por fecha si quisieras más adelante
}