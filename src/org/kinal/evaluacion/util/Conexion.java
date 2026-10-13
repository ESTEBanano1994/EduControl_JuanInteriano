
package org.kinal.evaluacion.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {

    private static volatile Conexion instancia;

    private static final String URL =
            "jdbc:mysql://localhost:3306/educontrol?serverTimezone=UTC";

    private static final String USUARIO = "root";

    private Connection conexion;

    private Conexion() {
    }

    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public synchronized Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            String password = System.getenv("EDUCONTROL_DB_PASSWORD");

            if (password == null) {
                throw new SQLException(
                        "Configura la variable EDUCONTROL_DB_PASSWORD"
                );
            }

            conexion = DriverManager.getConnection(
                    URL, USUARIO, password
            );
        }

        return conexion;
    }

    public synchronized void cerrarConexion() throws SQLException {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } finally {
                conexion = null;
            }
        }
    }
}
