package com.cibertec.proyecto.repository;
import com.cibertec.proyecto.model.Mensaje;
import com.cibertec.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
    List<Mensaje> findByReceptorOrderByFechaEnvioDesc(Usuario receptor);
}