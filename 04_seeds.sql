USE educontrol;

START TRANSACTION;

INSERT INTO usuario
    (nombre_completo, username, password_hash, rol, activo)
VALUES
    ('Coordinador Académico', 'coordinador', SHA2('Edu123*', 256), 'COORDINADOR_ACADEMICO', TRUE),
    ('Ana López', 'ana.lopez', SHA2('Edu123*', 256), 'ESTUDIANTE', TRUE),
    ('Carlos Méndez', 'carlos.mendez', SHA2('Edu123*', 256), 'ESTUDIANTE', TRUE),
    ('María García', 'maria.garcia', SHA2('Edu123*', 256), 'ESTUDIANTE', TRUE),
    ('José Ramírez', 'jose.ramirez', SHA2('Edu123*', 256), 'ESTUDIANTE', TRUE),
    ('Laura Castillo', 'laura.castillo', SHA2('Edu123*', 256), 'ESTUDIANTE', TRUE),
    ('Secretaría Académica', 'secretaria', SHA2('Edu123*', 256), 'SECRETARIA', TRUE),
    ('Coordinador Auxiliar', 'coord.auxiliar', SHA2('Edu123*', 256), 'COORDINADOR_ACADEMICO', TRUE);

INSERT INTO estudiante
    (id_usuario, carnet, nombres, apellidos, fecha_nacimiento, correo, telefono, estado)
VALUES
    (2, '2026001', 'Ana', 'López Pérez', '2005-03-15', 'ana.lopez@educontrol.test', '5551-1001', 'ACTIVO'),
    (3, '2026002', 'Carlos', 'Méndez Ruiz', '2004-07-22', 'carlos.mendez@educontrol.test', '5551-1002', 'ACTIVO'),
    (4, '2026003', 'María', 'García Soto', '2005-01-10', 'maria.garcia@educontrol.test', '5551-1003', 'ACTIVO'),
    (5, '2026004', 'José', 'Ramírez Díaz', '2003-11-08', 'jose.ramirez@educontrol.test', '5551-1004', 'ACTIVO'),
    (6, '2026005', 'Laura', 'Castillo León', '2004-05-19', 'laura.castillo@educontrol.test', '5551-1005', 'ACTIVO');

INSERT INTO docente
    (nombres, apellidos, correo, telefono, especialidad, estado)
VALUES
    ('Roberto', 'Hernández', 'roberto.hernandez@educontrol.test', '5552-2001', 'Matemática', 'ACTIVO'),
    ('Patricia', 'Morales', 'patricia.morales@educontrol.test', '5552-2002', 'Programación', 'ACTIVO'),
    ('Fernando', 'Gómez', 'fernando.gomez@educontrol.test', '5552-2003', 'Bases de datos', 'ACTIVO'),
    ('Claudia', 'Vásquez', 'claudia.vasquez@educontrol.test', '5552-2004', 'Redes', 'ACTIVO'),
    ('Miguel', 'Pineda', 'miguel.pineda@educontrol.test', '5552-2005', 'Ingeniería de software', 'ACTIVO');

INSERT INTO curso
    (codigo, nombre, descripcion, creditos, horas_semanales, estado)
VALUES
    ('MAT101', 'Matemática Básica', 'Fundamentos matemáticos.', 4, 5, 'ACTIVO'),
    ('PRG101', 'Introducción a la Programación', 'Fundamentos de programación.', 4, 5, 'ACTIVO'),
    ('BD101', 'Bases de Datos I', 'Diseño y consultas de bases de datos.', 4, 5, 'ACTIVO'),
    ('RED101', 'Redes de Computadoras', 'Fundamentos de redes.', 3, 4, 'ACTIVO'),
    ('IS101', 'Ingeniería de Software', 'Análisis y diseño de sistemas.', 4, 5, 'ACTIVO');

INSERT INTO prerrequisito
    (id_curso, id_curso_prerrequisito)
VALUES
    (2, 1),
    (3, 1),
    (3, 2),
    (4, 1),
    (5, 2);

INSERT INTO seccion
    (id_curso, id_docente, codigo_seccion, periodo, anio,
     horario, aula, cupo_maximo, cupo_disponible, estado)
VALUES
    (1, 1, 'A', 'PRIMER SEMESTRE', 2026, 'Lunes y miércoles 08:00-10:00', 'A-101', 30, 29, 'ABIERTA'),
    (2, 2, 'A', 'PRIMER SEMESTRE', 2026, 'Lunes y miércoles 10:00-12:00', 'LAB-1', 25, 24, 'ABIERTA'),
    (3, 3, 'A', 'PRIMER SEMESTRE', 2026, 'Martes y jueves 08:00-10:00', 'LAB-2', 25, 24, 'ABIERTA'),
    (4, 4, 'A', 'PRIMER SEMESTRE', 2026, 'Martes y jueves 10:00-12:00', 'B-201', 30, 29, 'ABIERTA'),
    (5, 5, 'A', 'PRIMER SEMESTRE', 2026, 'Viernes 08:00-12:00', 'B-202', 20, 19, 'ABIERTA');

INSERT INTO matricula
    (id_estudiante, fecha_prematricula, fecha_matricula, estado, observaciones)
VALUES
    (1, '2026-01-05 09:00:00', '2026-01-10 10:00:00', 'OFICIAL', 'Matrícula confirmada.'),
    (2, '2026-01-06 09:30:00', NULL, 'PREMATRICULA', 'Pendiente de validación.'),
    (3, '2026-01-07 10:00:00', NULL, 'PENDIENTE_PAGO', 'Pendiente de validar el pago.'),
    (4, '2026-01-08 11:00:00', '2026-01-12 08:30:00', 'OFICIAL', 'Documentación validada.'),
    (5, '2026-01-09 14:00:00', NULL, 'CANCELADA', 'Prematrícula cancelada.');

INSERT INTO detalle_matricula
    (id_matricula, id_seccion, estado)
VALUES
    (1, 1, 'ACTIVO'),
    (2, 2, 'ACTIVO'),
    (3, 3, 'ACTIVO'),
    (4, 4, 'ACTIVO'),
    (5, 5, 'CANCELADO');

COMMIT;