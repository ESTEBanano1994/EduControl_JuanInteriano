
package org.kinal.evaluacion.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class AppLogger {

    private static final Logger LOGGER =
            Logger.getLogger("EduControl");

    private AppLogger() {
    }

    public static void info(String mensaje) {
        LOGGER.log(Level.INFO, mensaje);
    }

    public static void advertencia(String mensaje) {
        LOGGER.log(Level.WARNING, mensaje);
    }

    public static void error(String mensaje, Throwable causa) {
        LOGGER.log(Level.SEVERE, mensaje, causa);
    }
}
