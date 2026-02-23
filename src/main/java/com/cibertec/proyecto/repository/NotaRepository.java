package com.cibertec.proyecto.repository;

import com.cibertec.proyecto.model.Nota;
import com.cibertec.proyecto.model.Usuario;
import com.cibertec.proyecto.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByCurso(Curso curso);
    
    // ESTE ES EL QUE FALTA PARA EL ALUMNO
    List<Nota> findByAlumno(Usuario alumno);
}