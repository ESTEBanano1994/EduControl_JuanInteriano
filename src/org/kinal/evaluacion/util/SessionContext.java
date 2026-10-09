
package org.kinal.evaluacion.util;

import java.time.LocalDateTime;

public final class SessionContext {

    private static final SessionContext INSTANCIA = new SessionContext();

    private Integer idUsuario;
    private String username;
    private String nombreCompleto;
    private String rol;
    private LocalDateTime horaInicioSesion;

    private SessionContext() {
    }

    public static SessionContext getInstancia() {
        return INSTANCIA;
    }

    public synchronized void iniciarSesion(
            int idUsuario,
            String username,
            String nombreCompleto,
            String rol) {

        if (idUsuario <= 0
                || username == null || username.isBlank()
                || nombreCompleto == null || nombreCompleto.isBlank()
                || rol == null || rol.isBlank()) {
            throw new IllegalArgumentException(
                    "Los datos del usuario no son validos"
            );
        }

        if (!rol.equals("COORDINADOR_ACADEMICO")
                && !rol.equals("ESTUDIANTE")
                && !rol.equals("SECRETARIA")) {
            throw new IllegalArgumentException(
                    "El rol del usuario no es valido"
            );
        }

        this.idUsuario = idUsuario;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.horaInicioSesion = LocalDateTime.now();
    }

    public synchronized boolean sesionActiva() {
        return idUsuario != null;
    }

    public synchronized Integer getIdUsuario() {
        return idUsuario;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized String getNombreCompleto() {
        return nombreCompleto;
    }

    public synchronized String getRol() {
        return rol;
    }

    public synchronized LocalDateTime getHoraInicioSesion() {
        return horaInicioSesion;
    }

    public synchronized void cerrarSesion() {
        idUsuario = null;
        username = null;
        nombreCompleto = null;
        rol = null;
        horaInicioSesion = null;
    }
}
