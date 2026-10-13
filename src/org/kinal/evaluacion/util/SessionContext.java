
package org.kinal.evaluacion.util;

import org.kinal.evaluacion.model.Usuario;

public final class SessionContext {

    private static Usuario usuarioActivo;

    private SessionContext() {
    }

    public static void iniciarSesion(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "El usuario no puede ser null");
        }

        // No conservamos el hash en la sesion.
        Usuario sesion = new Usuario();

        sesion.setIdUsuario(usuario.getIdUsuario());
        sesion.setNombreCompleto(usuario.getNombreCompleto());
        sesion.setUsername(usuario.getUsername());
        sesion.setRol(usuario.getRol());
        sesion.setActivo(usuario.isActivo());

        usuarioActivo = sesion;
    }

    public static Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public static boolean sesionActiva() {
        return usuarioActivo != null;
    }

    public static boolean tieneRol(String rol) {
        return usuarioActivo != null
                && usuarioActivo.getRol().equals(rol);
    }

    public static void cerrarSesion() {
        usuarioActivo = null;
    }
}
