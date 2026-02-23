package com.cibertec.proyecto.controller;

import com.cibertec.proyecto.model.*;
import com.cibertec.proyecto.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PortalController {

    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private CursoRepository cursoRepo;
    @Autowired private MensajeRepository mensajeRepo;
    @Autowired private ActividadRepository actividadRepo;
    @Autowired private NotaRepository notaRepo;

    @GetMapping("/")
    public String index() { return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Usuario user = usuarioRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("perfil", user);
            
            if ("DOCENTE".equals(user.getRol())) {
                return "redirect:/docente/home";
            } else {
                return "redirect:/alumno/home";
            }
        }
        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    // --- ROL DOCENTE ---

    @GetMapping("/docente/home")
    public String homeDocente(Model model, HttpSession session) {
        if (session.getAttribute("perfil") == null) return "redirect:/";
        model.addAttribute("noticias", actividadRepo.findAll());
        model.addAttribute("seccion", "inicio");
        return "docente-portal";
    }

    @GetMapping("/docente/cursos")
    public String cursosDocente(Model model, HttpSession session) {
        Usuario docente = (Usuario) session.getAttribute("perfil");
        if (docente == null) return "redirect:/";
        model.addAttribute("cursos", cursoRepo.findByDocente(docente));
        model.addAttribute("seccion", "cursos");
        return "docente-portal";
    }

    @GetMapping("/docente/curso/{id}")
    public String verAlumnosCurso(@PathVariable Integer id, Model model, HttpSession session) {
        if (session.getAttribute("perfil") == null) return "redirect:/";
        Curso curso = cursoRepo.findById(id).orElse(null);
        if (curso != null) {
            List<Nota> notasCurso = notaRepo.findByCurso(curso);
            model.addAttribute("cursoSeleccionado", curso);
            model.addAttribute("notas", notasCurso);
        }
        model.addAttribute("seccion", "detalle-curso");
        return "docente-portal";
    }

    @PostMapping("/docente/nota/actualizar")
    public String actualizarNota(@RequestParam Integer idNota, @RequestParam Double valor, @RequestParam Integer idCurso) {
        Nota nota = notaRepo.findById(idNota).orElse(null);
        if (nota != null) {
            nota.setValorNota(valor); 
            notaRepo.save(nota);
        }
        return "redirect:/docente/curso/" + idCurso;
    }

    @GetMapping("/docente/mensajes")
    public String mensajesDocente(Model model, HttpSession session) {
        Usuario docente = (Usuario) session.getAttribute("perfil");
        if (docente == null) return "redirect:/";
        model.addAttribute("mensajes", mensajeRepo.findByReceptorOrderByFechaEnvioDesc(docente));
        model.addAttribute("alumnos", usuarioRepo.findByRol("ALUMNO"));
        model.addAttribute("seccion", "mensajes");
        return "docente-portal";
    }

    @PostMapping("/docente/mensajes/enviar")
    public String enviarMensaje(@RequestParam Integer idReceptor, @RequestParam String asunto, @RequestParam String contenido, HttpSession session) {
        Usuario emisor = (Usuario) session.getAttribute("perfil");
        Usuario receptor = usuarioRepo.findById(idReceptor).orElse(null);
        if (emisor != null && receptor != null) {
            Mensaje nuevo = new Mensaje();
            nuevo.setEmisor(emisor);
            nuevo.setReceptor(receptor);
            nuevo.setAsunto(asunto);
            nuevo.setContenido(contenido);
            nuevo.setFechaEnvio(LocalDateTime.now());
            nuevo.setLeido(false);
            mensajeRepo.save(nuevo);
        }
        return "redirect:/docente/mensajes";
    }

    @GetMapping("/docente/calendario")
    public String calendarioDocente(Model model, HttpSession session) {
        if (session.getAttribute("perfil") == null) return "redirect:/";
        model.addAttribute("actividades", actividadRepo.findAll());
        model.addAttribute("seccion", "calendario");
        return "docente-portal";
    }

    @PostMapping("/docente/calendario/guardar")
    public String guardarActividad(@RequestParam String titulo, @RequestParam String tipo, @RequestParam String fechaStr, @RequestParam String descripcion, HttpSession session) {
        Usuario docente = (Usuario) session.getAttribute("perfil");
        if (docente != null) {
            Actividad a = new Actividad();
            a.setTitulo(titulo);
            a.setTipo(tipo);
            a.setDescripcion(descripcion);
            a.setFechaInicio(LocalDateTime.parse(fechaStr));
            a.setCreador(docente);
            actividadRepo.save(a);
        }
        return "redirect:/docente/calendario";
    }

    @GetMapping("/docente/calificaciones")
    public String calificacionesDocente(Model model, HttpSession session) {
        if (session.getAttribute("perfil") == null) return "redirect:/";
        model.addAttribute("alumnos", usuarioRepo.findByRol("ALUMNO"));
        model.addAttribute("seccion", "calificaciones");
        return "docente-portal";
    }

    @GetMapping("/docente/perfil")
    public String perfilDocente(Model model, HttpSession session) {
        if (session.getAttribute("perfil") == null) return "redirect:/";
        model.addAttribute("seccion", "perfil");
        return "docente-portal";
    }

    // --- ROL ALUMNO ---

    @GetMapping("/alumno/home")
    public String homeAlumno(Model model, HttpSession session) {
        if (session.getAttribute("perfil") == null) return "redirect:/";
        model.addAttribute("noticias", actividadRepo.findAll());
        model.addAttribute("seccion", "inicio");
        return "alumno-portal"; 
    }

    @GetMapping("/alumno/notas")
    public String notasAlumno(Model model, HttpSession session) {
        Usuario alumno = (Usuario) session.getAttribute("perfil");
        if (alumno == null) return "redirect:/";
        
        List<Nota> listaNotas = notaRepo.findByAlumno(alumno);
        
        // Si la lista está vacía o tiene algo raro, esto nos avisará en consola
        System.out.println("DEBUG: Cantidad de notas encontradas -> " + listaNotas.size());
        
        model.addAttribute("notas", listaNotas);
        model.addAttribute("seccion", "notas");
        return "alumno-portal";
    }

    @GetMapping("/alumno/mensajes")
    public String mensajesAlumno(Model model, HttpSession session) {
        Usuario alumno = (Usuario) session.getAttribute("perfil");
        if (alumno == null) return "redirect:/";
        model.addAttribute("mensajes", mensajeRepo.findByReceptorOrderByFechaEnvioDesc(alumno));
        model.addAttribute("seccion", "mensajes");
        return "alumno-portal";
    }

    @PostMapping("/alumno/mensajes/leer")
    public String marcarComoLeido(@RequestParam Integer idMensaje) {
        Mensaje mensaje = mensajeRepo.findById(idMensaje).orElse(null);
        if (mensaje != null) {
            mensaje.setLeido(true);
            mensajeRepo.save(mensaje);
        }
        return "redirect:/alumno/mensajes";
    }

    @PostMapping("/alumno/mensajes/eliminar")
    public String eliminarMensaje(@RequestParam Integer idMensaje) {
        if (mensajeRepo.existsById(idMensaje)) {
            mensajeRepo.deleteById(idMensaje);
        }
        return "redirect:/alumno/mensajes";
    }
    
    @GetMapping("/alumno/calendario")
    public String calendarioAlumno(Model model, HttpSession session) {
        if (session.getAttribute("perfil") == null) return "redirect:/";
        model.addAttribute("actividades", actividadRepo.findAll());
        model.addAttribute("seccion", "calendario");
        return "alumno-portal";
    }
    
    // Agregado para que el perfil del alumno también cargue
    @GetMapping("/alumno/perfil")
    public String perfilAlumno(Model model, HttpSession session) {
        if (session.getAttribute("perfil") == null) return "redirect:/";
        model.addAttribute("seccion", "perfil");
        return "alumno-portal";
    }

    // CORRECCIÓN: Acepta GET y POST, y usa la ruta /auth/logout que pusimos en el HTML
    @RequestMapping(value = "/auth/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/?logout"; // Redirige a la raíz (login)
    }
    @PostMapping("/alumno/calendario/guardar")
    public String guardarEventoAlumno(@RequestParam String titulo, @RequestParam String tipo, 
                                      @RequestParam String fechaStr, @RequestParam String descripcion, 
                                      HttpSession session) {
        Usuario alumno = (Usuario) session.getAttribute("perfil");
        if (alumno != null) {
            Actividad a = new Actividad();
            a.setTitulo(titulo);
            a.setTipo(tipo);
            a.setDescripcion(descripcion);
            // Esto convierte el formato del HTML (2026-02-23T10:00) a Java
            a.setFechaInicio(LocalDateTime.parse(fechaStr));
            a.setCreador(alumno);
            actividadRepo.save(a);
        }
        return "redirect:/alumno/calendario";
    }
    
}