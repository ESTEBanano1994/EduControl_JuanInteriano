
USE educontrol;

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_insertar_usuario$$
CREATE PROCEDURE sp_insertar_usuario(
    IN p_nombre_completo VARCHAR(150),
    IN p_username VARCHAR(50),
    IN p_password_hash VARCHAR(255),
    IN p_rol VARCHAR(30)
)
BEGIN
    INSERT INTO usuario(nombre_completo, username, password_hash, rol)
    VALUES(p_nombre_completo, p_username, p_password_hash, p_rol);
    SELECT LAST_INSERT_ID() AS id_usuario;
END$$

DROP PROCEDURE IF EXISTS sp_actualizar_usuario$$
CREATE PROCEDURE sp_actualizar_usuario(
    IN p_id INT,
    IN p_nombre_completo VARCHAR(150),
    IN p_username VARCHAR(50),
    IN p_rol VARCHAR(30),
    IN p_activo BOOLEAN
)
BEGIN
    UPDATE usuario
    SET nombre_completo = p_nombre_completo,
        username = p_username,
        rol = p_rol,
        activo = p_activo
    WHERE id_usuario = p_id;

    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_eliminar_usuario$$
CREATE PROCEDURE sp_eliminar_usuario(IN p_id INT)
BEGIN
    DELETE FROM usuario WHERE id_usuario = p_id;
    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_listar_usuarios$$
CREATE PROCEDURE sp_listar_usuarios()
BEGIN
    SELECT id_usuario, nombre_completo, username, rol, activo, fecha_creacion
    FROM usuario
    ORDER BY id_usuario;
END$$

DROP PROCEDURE IF EXISTS sp_insertar_estudiante$$
CREATE PROCEDURE sp_insertar_estudiante(
    IN p_id_usuario INT,
    IN p_carnet VARCHAR(30),
    IN p_nombres VARCHAR(100),
    IN p_apellidos VARCHAR(100),
    IN p_fecha_nacimiento DATE,
    IN p_correo VARCHAR(150),
    IN p_telefono VARCHAR(25)
)
BEGIN
    INSERT INTO estudiante(
        id_usuario, carnet, nombres, apellidos,
        fecha_nacimiento, correo, telefono
    )
    VALUES(
        p_id_usuario, p_carnet, p_nombres, p_apellidos,
        p_fecha_nacimiento, p_correo, p_telefono
    );

    SELECT LAST_INSERT_ID() AS id_estudiante;
END$$

DROP PROCEDURE IF EXISTS sp_actualizar_estudiante$$
CREATE PROCEDURE sp_actualizar_estudiante(
    IN p_id INT,
    IN p_carnet VARCHAR(30),
    IN p_nombres VARCHAR(100),
    IN p_apellidos VARCHAR(100),
    IN p_fecha_nacimiento DATE,
    IN p_correo VARCHAR(150),
    IN p_telefono VARCHAR(25),
    IN p_estado VARCHAR(20)
)
BEGIN
    UPDATE estudiante
    SET carnet = p_carnet,
        nombres = p_nombres,
        apellidos = p_apellidos,
        fecha_nacimiento = p_fecha_nacimiento,
        correo = p_correo,
        telefono = p_telefono,
        estado = p_estado
    WHERE id_estudiante = p_id;

    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_eliminar_estudiante$$
CREATE PROCEDURE sp_eliminar_estudiante(IN p_id INT)
BEGIN
    DELETE FROM estudiante WHERE id_estudiante = p_id;
    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_listar_estudiantes$$
CREATE PROCEDURE sp_listar_estudiantes()
BEGIN
    SELECT *
    FROM estudiante
    ORDER BY apellidos, nombres;
END$$

DROP PROCEDURE IF EXISTS sp_insertar_docente$$
CREATE PROCEDURE sp_insertar_docente(
    IN p_nombres VARCHAR(100),
    IN p_apellidos VARCHAR(100),
    IN p_correo VARCHAR(150),
    IN p_telefono VARCHAR(25),
    IN p_especialidad VARCHAR(150)
)
BEGIN
    INSERT INTO docente(nombres, apellidos, correo, telefono, especialidad)
    VALUES(p_nombres, p_apellidos, p_correo, p_telefono, p_especialidad);

    SELECT LAST_INSERT_ID() AS id_docente;
END$$

DROP PROCEDURE IF EXISTS sp_actualizar_docente$$
CREATE PROCEDURE sp_actualizar_docente(
    IN p_id INT,
    IN p_nombres VARCHAR(100),
    IN p_apellidos VARCHAR(100),
    IN p_correo VARCHAR(150),
    IN p_telefono VARCHAR(25),
    IN p_especialidad VARCHAR(150),
    IN p_estado VARCHAR(20)
)
BEGIN
    UPDATE docente
    SET nombres = p_nombres,
        apellidos = p_apellidos,
        correo = p_correo,
        telefono = p_telefono,
        especialidad = p_especialidad,
        estado = p_estado
    WHERE id_docente = p_id;

    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_eliminar_docente$$
CREATE PROCEDURE sp_eliminar_docente(IN p_id INT)
BEGIN
    DELETE FROM docente WHERE id_docente = p_id;
    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_listar_docentes$$
CREATE PROCEDURE sp_listar_docentes()
BEGIN
    SELECT *
    FROM docente
    ORDER BY apellidos, nombres;
END$$

DROP PROCEDURE IF EXISTS sp_insertar_curso$$
CREATE PROCEDURE sp_insertar_curso(
    IN p_codigo VARCHAR(20),
    IN p_nombre VARCHAR(150),
    IN p_descripcion VARCHAR(500),
    IN p_creditos TINYINT UNSIGNED,
    IN p_horas_semanales TINYINT UNSIGNED
)
BEGIN
    INSERT INTO curso(codigo, nombre, descripcion, creditos, horas_semanales)
    VALUES(p_codigo, p_nombre, p_descripcion, p_creditos, p_horas_semanales);

    SELECT LAST_INSERT_ID() AS id_curso;
END$$

DROP PROCEDURE IF EXISTS sp_actualizar_curso$$
CREATE PROCEDURE sp_actualizar_curso(
    IN p_id INT,
    IN p_codigo VARCHAR(20),
    IN p_nombre VARCHAR(150),
    IN p_descripcion VARCHAR(500),
    IN p_creditos TINYINT UNSIGNED,
    IN p_horas_semanales TINYINT UNSIGNED,
    IN p_estado VARCHAR(20)
)
BEGIN
    UPDATE curso
    SET codigo = p_codigo,
        nombre = p_nombre,
        descripcion = p_descripcion,
        creditos = p_creditos,
        horas_semanales = p_horas_semanales,
        estado = p_estado
    WHERE id_curso = p_id;

    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_eliminar_curso$$
CREATE PROCEDURE sp_eliminar_curso(IN p_id INT)
BEGIN
    DELETE FROM curso WHERE id_curso = p_id;
    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_listar_cursos$$
CREATE PROCEDURE sp_listar_cursos()
BEGIN
    SELECT *
    FROM curso
    ORDER BY nombre;
END$$

DROP PROCEDURE IF EXISTS sp_insertar_prerrequisito$$
CREATE PROCEDURE sp_insertar_prerrequisito(
    IN p_id_curso INT,
    IN p_id_curso_prerrequisito INT
)
BEGIN
    IF p_id_curso = p_id_curso_prerrequisito THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Un curso no puede ser su propio prerrequisito.';
    END IF;

    INSERT INTO prerrequisito(id_curso, id_curso_prerrequisito)
    VALUES(p_id_curso, p_id_curso_prerrequisito);

    SELECT LAST_INSERT_ID() AS id_prerrequisito;
END$$

DROP PROCEDURE IF EXISTS sp_eliminar_prerrequisito$$
CREATE PROCEDURE sp_eliminar_prerrequisito(IN p_id INT)
BEGIN
    DELETE FROM prerrequisito WHERE id_prerrequisito = p_id;
    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_listar_prerrequisitos$$
CREATE PROCEDURE sp_listar_prerrequisitos()
BEGIN
    SELECT
        p.id_prerrequisito,
        c.nombre AS curso,
        cp.nombre AS curso_prerrequisito
    FROM prerrequisito p
    INNER JOIN curso c ON c.id_curso = p.id_curso
    INNER JOIN curso cp ON cp.id_curso = p.id_curso_prerrequisito
    ORDER BY c.nombre, cp.nombre;
END$$

DROP PROCEDURE IF EXISTS sp_insertar_seccion$$
CREATE PROCEDURE sp_insertar_seccion(
    IN p_id_curso INT,
    IN p_id_docente INT,
    IN p_codigo_seccion VARCHAR(20),
    IN p_periodo VARCHAR(30),
    IN p_anio SMALLINT UNSIGNED,
    IN p_horario VARCHAR(100),
    IN p_aula VARCHAR(50),
    IN p_cupo_maximo INT UNSIGNED
)
BEGIN
    IF p_cupo_maximo = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El cupo máximo debe ser mayor que cero.';
    END IF;

    INSERT INTO seccion(
        id_curso, id_docente, codigo_seccion, periodo, anio,
        horario, aula, cupo_maximo, cupo_disponible
    )
    VALUES(
        p_id_curso, p_id_docente, p_codigo_seccion, p_periodo, p_anio,
        p_horario, p_aula, p_cupo_maximo, p_cupo_maximo
    );

    SELECT LAST_INSERT_ID() AS id_seccion;
END$$

DROP PROCEDURE IF EXISTS sp_actualizar_seccion$$
CREATE PROCEDURE sp_actualizar_seccion(
    IN p_id INT,
    IN p_id_docente INT,
    IN p_horario VARCHAR(100),
    IN p_aula VARCHAR(50),
    IN p_cupo_maximo INT UNSIGNED,
    IN p_estado VARCHAR(20)
)
BEGIN
    DECLARE v_inscritos INT DEFAULT 0;

    SELECT COUNT(*) INTO v_inscritos
    FROM detalle_matricula
    WHERE id_seccion = p_id
      AND estado = 'ACTIVO';

    IF p_cupo_maximo < v_inscritos THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El cupo no puede ser menor que los estudiantes inscritos.';
    END IF;

    UPDATE seccion
    SET id_docente = p_id_docente,
        horario = p_horario,
        aula = p_aula,
        cupo_maximo = p_cupo_maximo,
        cupo_disponible = p_cupo_maximo - v_inscritos,
        estado = p_estado
    WHERE id_seccion = p_id;

    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_eliminar_seccion$$
CREATE PROCEDURE sp_eliminar_seccion(IN p_id INT)
BEGIN
    DELETE FROM seccion WHERE id_seccion = p_id;
    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_listar_secciones$$
CREATE PROCEDURE sp_listar_secciones()
BEGIN
    SELECT
        s.id_seccion,
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
        s.estado
    FROM seccion s
    INNER JOIN curso c ON c.id_curso = s.id_curso
    INNER JOIN docente d ON d.id_docente = s.id_docente
    ORDER BY s.anio DESC, s.periodo, c.nombre;
END$$

DROP PROCEDURE IF EXISTS sp_insertar_matricula$$
CREATE PROCEDURE sp_insertar_matricula(
    IN p_id_estudiante INT
)
BEGIN
    INSERT INTO matricula(id_estudiante, fecha_prematricula, estado)
    VALUES(p_id_estudiante, NOW(), 'PREMATRICULA');

    SELECT LAST_INSERT_ID() AS id_matricula;
END$$

DROP PROCEDURE IF EXISTS sp_actualizar_estado_matricula$$
CREATE PROCEDURE sp_actualizar_estado_matricula(
    IN p_id_matricula INT,
    IN p_estado VARCHAR(30),
    IN p_observaciones VARCHAR(500)
)
BEGIN
    UPDATE matricula
    SET estado = p_estado,
        observaciones = p_observaciones,
        fecha_matricula = CASE
            WHEN p_estado = 'OFICIAL' THEN NOW()
            ELSE fecha_matricula
        END
    WHERE id_matricula = p_id_matricula;

    SELECT ROW_COUNT() AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_eliminar_matricula$$
CREATE PROCEDURE sp_eliminar_matricula(IN p_id INT)
BEGIN
    DECLARE v_finalizado INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    UPDATE seccion s
    INNER JOIN detalle_matricula dm ON dm.id_seccion = s.id_seccion
    SET s.cupo_disponible = s.cupo_disponible + 1
    WHERE dm.id_matricula = p_id
      AND dm.estado = 'ACTIVO';

    DELETE FROM matricula WHERE id_matricula = p_id;

    SET v_finalizado = ROW_COUNT();

    COMMIT;

    SELECT v_finalizado AS filas_afectadas;
END$$

DROP PROCEDURE IF EXISTS sp_listar_matriculas$$
CREATE PROCEDURE sp_listar_matriculas()
BEGIN
    SELECT
        m.id_matricula,
        e.carnet,
        CONCAT(e.nombres, ' ', e.apellidos) AS estudiante,
        m.fecha_prematricula,
        m.fecha_matricula,
        m.estado,
        m.observaciones
    FROM matricula m
    INNER JOIN estudiante e ON e.id_estudiante = m.id_estudiante
    ORDER BY m.id_matricula DESC;
END$$

DROP PROCEDURE IF EXISTS sp_listar_detalle_matricula$$
CREATE PROCEDURE sp_listar_detalle_matricula(IN p_id_matricula INT)
BEGIN
    SELECT
        dm.id_detalle,
        dm.id_matricula,
        s.id_seccion,
        c.codigo AS codigo_curso,
        c.nombre AS curso,
        s.codigo_seccion,
        s.periodo,
        s.anio,
        CONCAT(d.nombres, ' ', d.apellidos) AS docente,
        dm.estado,
        dm.fecha_registro
    FROM detalle_matricula dm
    INNER JOIN seccion s ON s.id_seccion = dm.id_seccion
    INNER JOIN curso c ON c.id_curso = s.id_curso
    INNER JOIN docente d ON d.id_docente = s.id_docente
    WHERE dm.id_matricula = p_id_matricula
    ORDER BY c.nombre;
END$$

DROP PROCEDURE IF EXISTS sp_prematricular_seccion$$
CREATE PROCEDURE sp_prematricular_seccion(
    IN p_id_estudiante INT,
    IN p_id_seccion INT
)
BEGIN
    DECLARE v_id_matricula INT DEFAULT NULL;
    DECLARE v_id_curso INT DEFAULT NULL;
    DECLARE v_cupo INT DEFAULT NULL;
    DECLARE v_estado_seccion VARCHAR(20) DEFAULT NULL;
    DECLARE v_estado_matricula VARCHAR(30) DEFAULT NULL;
    DECLARE v_prerrequisitos_pendientes INT DEFAULT 0;
    DECLARE v_ya_inscrito INT DEFAULT 0;
    DECLARE v_curso_aprobado INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    SELECT id_curso, cupo_disponible, estado
    INTO v_id_curso, v_cupo, v_estado_seccion
    FROM seccion
    WHERE id_seccion = p_id_seccion
    FOR UPDATE;

    IF v_id_curso IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La sección no existe.';
    END IF;

    IF v_estado_seccion <> 'ABIERTA' THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La sección no está abierta.';
    END IF;

    IF v_cupo <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La sección no tiene cupos disponibles.';
    END IF;

    SELECT COUNT(*)
    INTO v_prerrequisitos_pendientes
    FROM prerrequisito p
    WHERE p.id_curso = v_id_curso
      AND NOT EXISTS (
          SELECT 1
          FROM detalle_matricula dm
          INNER JOIN matricula m ON m.id_matricula = dm.id_matricula
          INNER JOIN seccion s ON s.id_seccion = dm.id_seccion
          WHERE m.id_estudiante = p_id_estudiante
            AND s.id_curso = p.id_curso_prerrequisito
            AND m.estado = 'OFICIAL'
            AND dm.estado = 'ACTIVO'
      );

    IF v_prerrequisitos_pendientes > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El estudiante no cumple los prerrequisitos requeridos.';
    END IF;

    SELECT COUNT(*)
    INTO v_ya_inscrito
    FROM detalle_matricula dm
    INNER JOIN matricula m ON m.id_matricula = dm.id_matricula
    INNER JOIN seccion s ON s.id_seccion = dm.id_seccion
    WHERE m.id_estudiante = p_id_estudiante
      AND s.id_curso = v_id_curso
      AND m.estado IN ('PREMATRICULA', 'PENDIENTE_PAGO', 'OFICIAL')
      AND dm.estado = 'ACTIVO';

    IF v_ya_inscrito > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El estudiante ya está inscrito en una sección de este curso.';
    END IF;

    SELECT id_matricula, estado
    INTO v_id_matricula, v_estado_matricula
    FROM matricula
    WHERE id_estudiante = p_id_estudiante
      AND estado = 'PREMATRICULA'
    ORDER BY id_matricula DESC
    LIMIT 1
    FOR UPDATE;

    IF v_id_matricula IS NULL THEN
        INSERT INTO matricula(id_estudiante, fecha_prematricula, estado)
        VALUES(p_id_estudiante, NOW(), 'PREMATRICULA');

        SET v_id_matricula = LAST_INSERT_ID();
    END IF;

    INSERT INTO detalle_matricula(id_matricula, id_seccion, estado)
    VALUES(v_id_matricula, p_id_seccion, 'ACTIVO');

    UPDATE seccion
    SET cupo_disponible = cupo_disponible - 1
    WHERE id_seccion = p_id_seccion
      AND cupo_disponible > 0;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'No fue posible reservar el cupo.';
    END IF;

    COMMIT;

    SELECT
        v_id_matricula AS id_matricula,
        p_id_seccion AS id_seccion,
        'Prematrícula registrada correctamente.' AS mensaje;
END$$

DROP PROCEDURE IF EXISTS sp_retirar_seccion$$
CREATE PROCEDURE sp_retirar_seccion(
    IN p_id_detalle INT
)
BEGIN
    DECLARE v_id_seccion INT DEFAULT NULL;
    DECLARE v_estado VARCHAR(20) DEFAULT NULL;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    SELECT id_seccion, estado
    INTO v_id_seccion, v_estado
    FROM detalle_matricula
    WHERE id_detalle = p_id_detalle
    FOR UPDATE;

    IF v_id_seccion IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El detalle de matrícula no existe.';
    END IF;

    IF v_estado = 'ACTIVO' THEN
        UPDATE detalle_matricula
        SET estado = 'RETIRADO'
        WHERE id_detalle = p_id_detalle;

        UPDATE seccion
        SET cupo_disponible = LEAST(cupo_disponible + 1, cupo_maximo)
        WHERE id_seccion = v_id_seccion;
    END IF;

    COMMIT;

    SELECT 'Retiro procesado correctamente.' AS mensaje;
END$$

DELIMITER ;