package com.cibertec.proyecto.repository;

import com.cibertec.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUsername(String username);

    List<Usuario> findByRol(String rol);
}