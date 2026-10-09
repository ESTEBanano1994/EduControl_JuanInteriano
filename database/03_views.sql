
USE educontrol;

CREATE OR REPLACE VIEW vw_estudiantes AS
SELECT
    e.id_estudiante,
    e.id_usuario,
    e.carnet,
    e.nombres,
    e.apellidos,
    CONCAT(e.nombres, ' ', e.apellidos) AS nombre_completo,
    e.fecha_nacimiento,
    e.correo,
    e.telefono,
    e.estado,
    e.fecha_registro
FROM estudiante e;

CREATE OR REPLACE VIEW vw_docentes AS
SELECT
    d.id_docente,
    d.nombres,
    d.apellidos,
    CONCAT(d.nombres, ' ', d.apellidos) AS nombre_completo,
    d.correo,
    d.telefono,
    d.especialidad,
    d.estado,
    d.fecha_registro
FROM docente d;

CREATE OR REPLACE VIEW vw_cursos AS
SELECT
    c.id_curso,
    c.codigo,
    c.nombre,
    c.descripcion,
    c.creditos,
    c.horas_semanales,
    c.estado,
    c.fecha_creacion,
    COUNT(DISTINCT p.id_curso_prerrequisito) AS cantidad_prerrequisitos
FROM curso c
LEFT JOIN prerrequisito p
    ON p.id_curso = c.id_curso
GROUP BY
    c.id_curso,
    c.codigo,
    c.nombre,
    c.descripcion,
    c.creditos,
    c.horas_semanales,
    c.estado,
    c.fecha_creacion;

CREATE OR REPLACE VIEW vw_prerrequisitos AS
SELECT
    p.id_prerrequisito,
    c.id_curso,
    c.codigo AS codigo_curso,
    c.nombre AS curso,
    cp.id_curso AS id_curso_prerrequisito,
    cp.codigo AS codigo_prerrequisito,
    cp.nombre AS curso_prerrequisito
FROM prerrequisito p
INNER JOIN curso c
    ON c.id_curso = p.id_curso
INNER JOIN curso cp
    ON cp.id_curso = p.id_curso_prerrequisito;

CREATE OR REPLACE VIEW vw_secciones AS
SELECT
    s.id_seccion,
    c.id_curso,
    c.codigo AS codigo_curso,
    c.nombre AS curso,
    s.codigo_seccion,
    s.periodo,
    s.anio,
    d.id_docente,
    CONCAT(d.nombres, ' ', d.apellidos) AS docente,
    s.horario,
    s.aula,
    s.cupo_maximo,
    s.cupo_disponible,
    (s.cupo_maximo - s.cupo_disponible) AS cupos_ocupados,
    s.estado,
    s.fecha_creacion
FROM seccion s
INNER JOIN curso c
    ON c.id_curso = s.id_curso
INNER JOIN docente d
    ON d.id_docente = s.id_docente;

CREATE OR REPLACE VIEW vw_matriculas AS
SELECT
    m.id_matricula,
    e.id_estudiante,
    e.carnet,
    CONCAT(e.nombres, ' ', e.apellidos) AS estudiante,
    e.correo,
    m.fecha_prematricula,
    m.fecha_matricula,
    m.estado,
    m.observaciones,
    COUNT(DISTINCT dm.id_detalle) AS total_secciones
FROM matricula m
INNER JOIN estudiante e
    ON e.id_estudiante = m.id_estudiante
LEFT JOIN detalle_matricula dm
    ON dm.id_matricula = m.id_matricula
   AND dm.estado = 'ACTIVO'
GROUP BY
    m.id_matricula,
    e.id_estudiante,
    e.carnet,
    e.nombres,
    e.apellidos,
    e.correo,
    m.fecha_prematricula,
    m.fecha_matricula,
    m.estado,
    m.observaciones;

CREATE OR REPLACE VIEW vw_detalle_matricula AS
SELECT
    dm.id_detalle,
    m.id_matricula,
    m.estado AS estado_matricula,
    e.id_estudiante,
    e.carnet,
    CONCAT(e.nombres, ' ', e.apellidos) AS estudiante,
    s.id_seccion,
    c.id_curso,
    c.codigo AS codigo_curso,
    c.nombre AS curso,
    s.codigo_seccion,
    s.periodo,
    s.anio,
    CONCAT(d.nombres, ' ', d.apellidos) AS docente,
    s.horario,
    s.aula,
    dm.estado AS estado_detalle,
    dm.fecha_registro
FROM detalle_matricula dm
INNER JOIN matricula m
    ON m.id_matricula = dm.id_matricula
INNER JOIN estudiante e
    ON e.id_estudiante = m.id_estudiante
INNER JOIN seccion s
    ON s.id_seccion = dm.id_seccion
INNER JOIN curso c
    ON c.id_curso = s.id_curso
INNER JOIN docente d
    ON d.id_docente = s.id_docente;

CREATE OR REPLACE VIEW vw_usuarios AS
SELECT
    u.id_usuario,
    u.nombre_completo,
    u.username,
    u.rol,
    u.activo,
    u.fecha_creacion
FROM usuario u;

CREATE OR REPLACE VIEW vw_cupos_disponibles AS
SELECT
    s.id_seccion,
    c.codigo AS codigo_curso,
    c.nombre AS curso,
    s.codigo_seccion,
    s.periodo,
    s.anio,
    s.cupo_maximo,
    s.cupo_disponible,
    (s.cupo_maximo - s.cupo_disponible) AS cupos_ocupados,
    s.estado
FROM seccion s
INNER JOIN curso c
    ON c.id_curso = s.id_curso
WHERE s.estado = 'ABIERTA'
  AND s.cupo_disponible > 0;

CREATE OR REPLACE VIEW vw_resumen_matriculas AS
SELECT
    COUNT(*) AS total_matriculas,
    SUM(CASE WHEN estado = 'PREMATRICULA' THEN 1 ELSE 0 END) AS total_prematriculas,
    SUM(CASE WHEN estado = 'PENDIENTE_PAGO' THEN 1 ELSE 0 END) AS pendientes_pago,
    SUM(CASE WHEN estado = 'OFICIAL' THEN 1 ELSE 0 END) AS matriculas_oficiales,
    SUM(CASE WHEN estado = 'CANCELADA' THEN 1 ELSE 0 END) AS matriculas_canceladas
FROM matricula;