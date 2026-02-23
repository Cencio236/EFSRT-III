DROP DATABASE IF EXISTS db_academica;
CREATE DATABASE db_academica;
USE db_academica;

-- 1. TABLA USUARIOS (Docentes y Alumnos entran aquí)
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    nombre_completo VARCHAR(100),
    dni CHAR(8),
    email VARCHAR(100),
    rol VARCHAR(20), -- 'DOCENTE' o 'ALUMNO'
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    foto_url VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'
);

-- 2. TABLA CURSOS
CREATE TABLE cursos (
    id_curso INT PRIMARY KEY AUTO_INCREMENT,
    nombre_curso VARCHAR(100),
    codigo_seccion VARCHAR(20),
    id_docente INT,
    FOREIGN KEY (id_docente) REFERENCES usuarios(id_usuario)
);

-- 3. TABLA NOTAS (Vincula Alumno con Curso)
CREATE TABLE notas (
    id_nota INT PRIMARY KEY AUTO_INCREMENT,
    id_alumno INT,
    id_curso INT,
    valor_nota DECIMAL(4,2),
    FOREIGN KEY (id_alumno) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);

-- 4. TABLA MENSAJES
CREATE TABLE mensajes (
    id_mensaje INT PRIMARY KEY AUTO_INCREMENT,
    id_emisor INT,
    id_receptor INT,
    asunto VARCHAR(100),
    contenido TEXT,
    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    leido BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_emisor) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_receptor) REFERENCES usuarios(id_usuario)
);

-- 5. TABLA CALENDARIO (Actividades)
CREATE TABLE actividades (
    id_actividad INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100),
    descripcion TEXT,
    fecha_inicio DATETIME,
    tipo VARCHAR(50), -- 'EXAMEN', 'TAREA', 'EXPOSICION'
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- DATA DE PRUEBA: 1 DOCENTE Y 15 ALUMNOS
INSERT INTO usuarios (username, password, nombre_completo, dni, email, rol) VALUES 
('profe1', '123', 'Sebastian García', '45678912', 's.garcia@cibertec.edu.pe', 'DOCENTE');

-- Insertando 15 alumnos
INSERT INTO usuarios (username, password, nombre_completo, dni, email, rol) VALUES 
('alumno1', '123', 'Maria Lopez', '71234561', 'm1@cibertec.pe', 'ALUMNO'),
('alumno2', '123', 'Juan Perez', '71234562', 'm2@cibertec.pe', 'ALUMNO'),
('alumno3', '123', 'Ana Torres', '71234563', 'm3@cibertec.pe', 'ALUMNO'),
('alumno4', '123', 'Carlos Ruiz', '71234564', 'm4@cibertec.pe', 'ALUMNO'),
('alumno5', '123', 'Elena Cruz', '71234565', 'm5@cibertec.pe', 'ALUMNO'),
('alumno6', '123', 'Luis Soto', '71234566', 'm6@cibertec.pe', 'ALUMNO'),
('alumno7', '123', 'Rosa Diaz', '71234567', 'm7@cibertec.pe', 'ALUMNO'),
('alumno8', '123', 'Pedro Mas', '71234568', 'm8@cibertec.pe', 'ALUMNO'),
('alumno9', '123', 'Guty Carr', '71234569', 'm9@cibertec.pe', 'ALUMNO'),
('alumno10', '123', 'Ines Mar', '71234570', 'm10@cibertec.pe', 'ALUMNO'),
('alumno11', '123', 'Beto Cue', '71234571', 'm11@cibertec.pe', 'ALUMNO'),
('alumno12', '123', 'Luz Luna', '71234572', 'm12@cibertec.pe', 'ALUMNO'),
('alumno13', '123', 'Omar Paz', '71234573', 'm13@cibertec.pe', 'ALUMNO'),
('alumno14', '123', 'Sara Gil', '71234574', 'm14@cibertec.pe', 'ALUMNO'),
('alumno15', '123', 'Hugo Rey', '71234575', 'm15@cibertec.pe', 'ALUMNO');

-- CURSOS PARA EL PROFE SEBASTIAN
INSERT INTO cursos (nombre_curso, codigo_seccion, id_docente) VALUES 
('Desarrollo Web III', 'T4BN-01', 1),
('Base de Datos II', 'T4BN-02', 1),
('Arquitectura de Sistemas', 'T4BN-03', 1);

-- ACTIVIDADES INICIALES
INSERT INTO actividades (titulo, descripcion, fecha_inicio, tipo, id_usuario) VALUES 
('Examen Parcial', 'Evaluación de la unidad 1', '2026-03-15 10:00:00', 'EXAMEN', 1),
('Entrega de Proyecto', 'Subir avance al Blackboard', '2026-03-20 23:59:00', 'TAREA', 1);

USE db_academica;

-- 1. Apagar el modo seguro temporalmente
SET SQL_SAFE_UPDATES = 0;

-- 2. Limpiar y llenar (lo que ya teníamos)
DELETE FROM notas;

INSERT INTO notas (valor_nota, id_alumno, id_curso) VALUES 
(18.5, 2, 1), (15.0, 3, 1), (12.5, 4, 1), (20.0, 5, 1), (14.0, 6, 1),
(11.5, 7, 1), (19.0, 8, 1), (13.5, 9, 1), (16.0, 10, 1), (17.5, 11, 1),
(12.0, 12, 1), (14.5, 13, 1), (15.5, 14, 1), (18.0, 15, 1), (13.0, 16, 1);

INSERT INTO notas (valor_nota, id_alumno, id_curso) VALUES 
(14.5, 2, 2), (17.0, 3, 2), (11.5, 4, 2), (19.0, 5, 2), (15.0, 6, 2),
(13.5, 7, 2), (20.0, 8, 2), (12.5, 9, 2), (18.0, 10, 2), (16.5, 11, 2),
(14.0, 12, 2), (15.5, 13, 2), (13.0, 14, 2), (17.5, 15, 2), (12.0, 16, 2);

DELETE FROM mensajes;
INSERT INTO mensajes (asunto, contenido, fecha_envio, leido, id_emisor, id_receptor) VALUES 
('Duda sobre la Tarea 1', 'Profesor, no me queda claro el punto 3 del laboratorio.', NOW(), false, 2, 1),
('Justificación de Inasistencia', 'Buen día, adjunto mi certificado médico por la falta de ayer.', NOW(), false, 5, 1),
('Consulta Proyecto Final', '¿Podemos usar MongoDB en lugar de MySQL?', NOW(), false, 8, 1);

-- 3. Volver a encender el modo seguro (por precaución)
SET SQL_SAFE_UPDATES = 1;
-- Supongamos que el ID del alumno es 2 y los profes son ID 3 y 4
INSERT INTO mensajes (id_emisor, id_receptor, asunto, contenido, fecha_envio, leido) 
VALUES 
(3, 2, 'Proyecto Final de Algoritmos', 'Hola Sebastian, recuerda que el plazo para subir el T2 vence este domingo. No olvides adjuntar el informe.', NOW(), 0),

(4, 2, 'Clase de Recuperación', 'Estimados, la clase de Sistemas Operativos se recuperará este sábado a las 9:00 AM vía Zoom.', NOW(), 0),

(3, 2, 'Felicitaciones T1', 'Buen trabajo en tu examen parcial. Sigue así.', NOW(), 1);
INSERT INTO mensajes (id_emisor, id_receptor, asunto, contenido, fecha_envio, leido) 
VALUES 
(3, 2, 'Proyecto Final de Algoritmos', 'Hola Sebastian, recuerda que el plazo para subir el T2 vence este domingo. No olvides adjuntar el informe.', NOW(), 0),
(4, 2, 'Clase de Recuperación', 'Estimados, la clase de Sistemas Operativos se recuperará este sábado a las 9:00 AM vía Zoom.', NOW(), 0),
(3, 2, 'Felicitaciones T1', 'Buen trabajo en tu examen parcial. Sigue así.', NOW(), 1),
(5, 2, 'Material de Lectura', 'He subido el PDF de la semana 6 sobre Gestión de Memoria al aula virtual.', NOW(), 0),
(3, 2, 'Revisión de Calificaciones', 'Sebastian, he revisado tu reclamo de la T1. La nota ha sido actualizada en el sistema.', NOW(), 0),
(4, 2, 'Encuesta de Satisfacción', 'Por favor, completen la encuesta sobre el taller de Linux antes de mañana.', NOW(), 1),
(5, 2, 'Invitación a Conferencia', 'Te invito a la charla de Ciberseguridad que dará la universidad este jueves.', NOW(), 0),
(3, 2, 'Duda sobre Laboratorio', 'Recibí tu correo. La respuesta a tu duda es que debes usar punteros en esa sección.', NOW(), 0),
(4, 2, 'Aviso Urgente', 'La plataforma entrará en mantenimiento hoy a las 11:00 PM.', NOW(), 0),
(5, 2, 'Feedback Grupal', 'Tu grupo tuvo el mejor desempeño en la presentación de ayer. ¡Sigan así!', NOW(), 0);