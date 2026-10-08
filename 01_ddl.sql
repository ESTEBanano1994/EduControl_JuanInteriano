USE educontrol;
SELECT * FROM estudiante;

CREATE DATABASE IF NOT EXISTS educontrol
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE educontrol;

DROP TABLE IF EXISTS detalle_matricula;
DROP TABLE IF EXISTS matricula;
DROP TABLE IF EXISTS prerrequisito;
DROP TABLE IF EXISTS seccion;
DROP TABLE IF EXISTS curso;
DROP TABLE IF EXISTS docente;
DROP TABLE IF EXISTS estudiante;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol ENUM('COORDINADOR_ACADEMICO', 'ESTUDIANTE', 'SECRETARIA') NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_usuario_rol (rol),
    INDEX idx_usuario_activo (activo)
) ENGINE=InnoDB;

CREATE TABLE estudiante (
    id_estudiante INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NULL,
    carnet VARCHAR(30) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(25) NULL,
    estado ENUM('ACTIVO', 'INACTIVO', 'GRADUADO', 'RETIRADO') NOT NULL DEFAULT 'ACTIVO',
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_estudiante_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    INDEX idx_estudiante_apellidos (apellidos),
    INDEX idx_estudiante_estado (estado)
) ENGINE=InnoDB;

CREATE TABLE docente (
    id_docente INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(25) NULL,
    especialidad VARCHAR(150) NULL,
    estado ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_docente_apellidos (apellidos),
    INDEX idx_docente_estado (estado)
) ENGINE=InnoDB;

CREATE TABLE curso (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500) NULL,
    creditos TINYINT UNSIGNED NOT NULL,
    horas_semanales TINYINT UNSIGNED NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_curso_creditos
        CHECK (creditos > 0),

    CONSTRAINT chk_curso_horas
        CHECK (horas_semanales > 0),

    INDEX idx_curso_nombre (nombre),
    INDEX idx_curso_estado (estado)
) ENGINE=InnoDB;

CREATE TABLE prerrequisito (
    id_prerrequisito INT AUTO_INCREMENT PRIMARY KEY,
    id_curso INT NOT NULL,
    id_curso_prerrequisito INT NOT NULL,

    CONSTRAINT fk_prerrequisito_curso
        FOREIGN KEY (id_curso)
        REFERENCES curso(id_curso)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_prerrequisito_curso_requerido
        FOREIGN KEY (id_curso_prerrequisito)
        REFERENCES curso(id_curso)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT uk_curso_prerrequisito
        UNIQUE (id_curso, id_curso_prerrequisito),


    INDEX idx_prerrequisito_curso (id_curso),
    INDEX idx_prerrequisito_requerido (id_curso_prerrequisito)
) ENGINE=InnoDB;

CREATE TABLE seccion (
    id_seccion INT AUTO_INCREMENT PRIMARY KEY,
    id_curso INT NOT NULL,
    id_docente INT NOT NULL,
    codigo_seccion VARCHAR(20) NOT NULL,
    periodo VARCHAR(30) NOT NULL,
    anio SMALLINT UNSIGNED NOT NULL,
    horario VARCHAR(100) NOT NULL,
    aula VARCHAR(50) NULL,
    cupo_maximo INT UNSIGNED NOT NULL,
    cupo_disponible INT UNSIGNED NOT NULL,
    estado ENUM('ABIERTA', 'CERRADA', 'CANCELADA') NOT NULL DEFAULT 'ABIERTA',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_seccion_curso
        FOREIGN KEY (id_curso)
        REFERENCES curso(id_curso)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_seccion_docente
        FOREIGN KEY (id_docente)
        REFERENCES docente(id_docente)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT uk_seccion_periodo
        UNIQUE (id_curso, codigo_seccion, periodo, anio),

    CONSTRAINT chk_seccion_cupo_maximo
        CHECK (cupo_maximo > 0),

    CONSTRAINT chk_seccion_cupo_disponible
        CHECK (cupo_disponible <= cupo_maximo),

    CONSTRAINT chk_seccion_anio
        CHECK (anio >= 2020),

    INDEX idx_seccion_curso (id_curso),
    INDEX idx_seccion_docente (id_docente),
    INDEX idx_seccion_periodo (periodo, anio),
    INDEX idx_seccion_estado (estado)
) ENGINE=InnoDB;

CREATE TABLE matricula (
    id_matricula INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    fecha_prematricula DATETIME NULL,
    fecha_matricula DATETIME NULL,
    estado ENUM(
        'PREMATRICULA',
        'PENDIENTE_PAGO',
        'OFICIAL',
        'CANCELADA'
    ) NOT NULL DEFAULT 'PREMATRICULA',
    observaciones VARCHAR(500) NULL,

    CONSTRAINT fk_matricula_estudiante
        FOREIGN KEY (id_estudiante)
        REFERENCES estudiante(id_estudiante)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    INDEX idx_matricula_estudiante (id_estudiante),
    INDEX idx_matricula_estado (estado),
    INDEX idx_matricula_fecha (fecha_matricula)
) ENGINE=InnoDB;

CREATE TABLE detalle_matricula (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_matricula INT NOT NULL,
    id_seccion INT NOT NULL,
    estado ENUM('ACTIVO', 'RETIRADO', 'CANCELADO') NOT NULL DEFAULT 'ACTIVO',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_detalle_matricula
        FOREIGN KEY (id_matricula)
        REFERENCES matricula(id_matricula)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_detalle_seccion
        FOREIGN KEY (id_seccion)
        REFERENCES seccion(id_seccion)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT uk_matricula_seccion
        UNIQUE (id_matricula, id_seccion),

    INDEX idx_detalle_matricula (id_matricula),
    INDEX idx_detalle_seccion (id_seccion),
    INDEX idx_detalle_estado (estado)
) ENGINE=InnoDB;
