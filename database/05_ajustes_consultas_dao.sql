
USE educontrol;

DELIMITER $$

-- 1. LISTAR PRERREQUISITOS CON SUS IDs

DROP PROCEDURE IF EXISTS sp_listar_prerrequisitos$$

CREATE PROCEDURE sp_listar_prerrequisitos()
BEGIN
    SELECT
        p.id_prerrequisito,
        p.id_curso,
        p.id_curso_prerrequisito,
        c.nombre AS curso,
        cp.nombre AS curso_prerrequisito
    FROM prerrequisito p
    INNER JOIN curso c
        ON c.id_curso = p.id_curso
    INNER JOIN curso cp
        ON cp.id_curso = p.id_curso_prerrequisito
    ORDER BY c.nombre, cp.nombre;
END$$


-- 2. LISTAR SECCIONES CON SUS IDs Y FECHA

DROP PROCEDURE IF EXISTS sp_listar_secciones$$

CREATE PROCEDURE sp_listar_secciones()
BEGIN
    SELECT
        s.id_seccion,
        s.id_curso,
        s.id_docente,
        c.codigo AS codigo_curso,
        c.nombre AS curso,
        s.codigo_seccion,
        s.periodo,
        s.anio,
        CONCAT(d.nombres, ' ', d.apellidos) AS docente,
        s.horario,
        s.aula,
        s.cupo_maximo,
        s.cupo_disponible,
        s.estado,
        s.fecha_creacion
    FROM seccion s
    INNER JOIN curso c
        ON c.id_curso = s.id_curso
    INNER JOIN docente d
        ON d.id_docente = s.id_docente
    ORDER BY s.anio DESC, s.periodo, c.nombre;
END$$


-- 3. LISTAR MATRÍCULAS CON ID DE ESTUDIANTE

DROP PROCEDURE IF EXISTS sp_listar_matriculas$$

CREATE PROCEDURE sp_listar_matriculas()
BEGIN
    SELECT
        m.id_matricula,
        m.id_estudiante,
        e.carnet,
        CONCAT(e.nombres, ' ', e.apellidos) AS estudiante,
        m.fecha_prematricula,
        m.fecha_matricula,
        m.estado,
        m.observaciones
    FROM matricula m
    INNER JOIN estudiante e
        ON e.id_estudiante = m.id_estudiante
    ORDER BY m.id_matricula DESC;
END$$


-- 4. LISTAR DETALLES DE UNA MATRÍCULA

DROP PROCEDURE IF EXISTS sp_listar_detalle_matricula$$

CREATE PROCEDURE sp_listar_detalle_matricula(
    IN p_id_matricula INT
)
BEGIN
    SELECT
        dm.id_detalle,
        dm.id_matricula,
        dm.id_seccion,
        c.codigo AS codigo_curso,
        c.nombre AS curso,
        s.codigo_seccion,
        s.periodo,
        s.anio,
        CONCAT(d.nombres, ' ', d.apellidos) AS docente,
        dm.estado,
        dm.fecha_registro
    FROM detalle_matricula dm
    INNER JOIN seccion s
        ON s.id_seccion = dm.id_seccion
    INNER JOIN curso c
        ON c.id_curso = s.id_curso
    INNER JOIN docente d
        ON d.id_docente = s.id_docente
    WHERE dm.id_matricula = p_id_matricula
    ORDER BY c.nombre;
END$$


-- 5. NUEVO PROCEDIMIENTO: BUSCAR DETALLE POR ID

DROP PROCEDURE IF EXISTS sp_buscar_detalle_por_id$$

CREATE PROCEDURE sp_buscar_detalle_por_id(
    IN p_id_detalle INT
)
BEGIN
    SELECT
        id_detalle,
        id_matricula,
        id_seccion,
        estado,
        fecha_registro
    FROM detalle_matricula
    WHERE id_detalle = p_id_detalle;
END$$

DELIMITER ;
