package com.cibertec.proyecto.repository;
import com.cibertec.proyecto.model.Curso;
import com.cibertec.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    List<Curso> findByDocente(Usuario docente);
}