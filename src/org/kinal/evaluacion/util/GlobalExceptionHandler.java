
package org.kinal.evaluacion.util;

import org.kinal.evaluacion.exceptions.AppException;
import org.kinal.evaluacion.exceptions.DBException;

public final class GlobalExceptionHandler {

    private GlobalExceptionHandler() {
    }

    public static String manejar(Exception excepcion) {

        if (excepcion == null) {
            return "Ha ocurrido un error desconocido.";
        }

        AppLogger.error(
                "Error detectado en EduControl",
                excepcion
        );

        if (excepcion instanceof DBException) {
            return "Ocurrio un problema con la base de datos.";
        }

        if (excepcion instanceof AppException) {
            return excepcion.getMessage();
        }

        return "Ocurrio un error inesperado en el sistema.";
    }
}
