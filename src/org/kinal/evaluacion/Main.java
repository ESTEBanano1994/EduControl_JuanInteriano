
package org.kinal.evaluacion;

import java.sql.Connection;
import java.sql.SQLException;
import org.kinal.evaluacion.util.Conexion;

public class Main {

    public static void main(String[] args) {

        System.out.println("EduControl - Prueba de conexión");

        try {
            Connection conexion = Conexion.getInstancia().getConexion();

            if (conexion != null && conexion.isValid(5)) {
                System.out.println("Conexion a MySQL exitosa");
                System.out.println("Base de datos: educontrol");
            }

        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());

        } finally {
            try {
                Conexion.getInstancia().cerrarConexion();
                System.out.println("Conexion cerrada correctamente");
            } catch (SQLException e) {
                System.out.println("Error al cerrar: " + e.getMessage());
            }
        }
    }
}
