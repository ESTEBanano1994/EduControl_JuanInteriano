
package org.kinal.evaluacion.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.kinal.evaluacion.model.Usuario;
import org.kinal.evaluacion.util.SessionContext;

public class MenuController {

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblUsuario;

    @FXML
    private Button btnOpcion1;

    @FXML
    private Button btnOpcion2;

    @FXML
    private Button btnOpcion3;

    @FXML
    public void initialize() {

        Usuario usuario = SessionContext.getUsuarioActivo();

        if (usuario == null) {
            lblTitulo.setText("Sin sesión activa");
            lblUsuario.setText("Acceso no autorizado");

            btnOpcion1.setDisable(true);
            btnOpcion2.setDisable(true);
            btnOpcion3.setDisable(true);
            return;
        }

        lblUsuario.setText(
                "Bienvenido: " + usuario.getNombreCompleto());

        switch (usuario.getRol()) {

            case "COORDINADOR_ACADEMICO":
                lblTitulo.setText("Menú Coordinador");
                btnOpcion1.setText("Gestionar cursos");
                btnOpcion2.setText("Gestionar docentes");
                btnOpcion3.setText("Gestionar secciones");
                break;

            case "ESTUDIANTE":
                lblTitulo.setText("Menú Estudiante");
                btnOpcion1.setText("Consultar cursos");
                btnOpcion2.setText("Realizar prematrícula");
                btnOpcion3.setText("Mis matrículas");
                break;

            case "SECRETARIA":
                lblTitulo.setText("Menú Secretaría");
                btnOpcion1.setText("Consultar estudiantes");
                btnOpcion2.setText("Validar pagos");
                btnOpcion3.setText("Confirmar matrículas");
                break;

            default:
                lblTitulo.setText("Rol no autorizado");
                btnOpcion1.setDisable(true);
                btnOpcion2.setDisable(true);
                btnOpcion3.setDisable(true);
                break;
        }
    }

    @FXML
    private void handleOpcion1() {
        ejecutarOpcion(1);
    }

    @FXML
    private void handleOpcion2() {
        ejecutarOpcion(2);
    }

    @FXML
    private void handleOpcion3() {
        ejecutarOpcion(3);
    }

    private void ejecutarOpcion(int numero) {

        Usuario usuario = SessionContext.getUsuarioActivo();

        if (usuario == null || !usuario.isActivo()) {
            mostrarMensaje("No hay una sesión válida.");
            return;
        }

        String rol = usuario.getRol();
        String modulo;

        switch (rol) {

            case "COORDINADOR_ACADEMICO":
                modulo = numero == 1 ? "Gestionar cursos"
                        : numero == 2 ? "Gestionar docentes"
                        : "Gestionar secciones";
                break;

            case "ESTUDIANTE":
                modulo = numero == 1 ? "Consultar cursos"
                        : numero == 2 ? "Realizar prematrícula"
                        : "Mis matrículas";
                break;

            case "SECRETARIA":
                modulo = numero == 1 ? "Consultar estudiantes"
                        : numero == 2 ? "Validar pagos"
                        : "Confirmar matrículas";
                break;

            default:
                mostrarMensaje("Rol no autorizado.");
                return;
        }

        mostrarMensaje(modulo
                + "\nEste módulo se implementará "
                + "en los siguientes puntos.");
    }

    private void mostrarMensaje(String mensaje) {

        Alert alerta = new Alert(
                Alert.AlertType.INFORMATION);

        alerta.setTitle("EduControl");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void handleCerrarSesion() {

        SessionContext.cerrarSesion();

        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource(
                        "/org/kinal/evaluacion/view/LoginView.fxml"
                    )
            );

            Stage stage = (Stage)
                    lblTitulo.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("EduControl - Inicio de sesión");
            stage.centerOnScreen();

        } catch (IOException e) {
            mostrarMensaje(
                    "Error al regresar al inicio de sesión.");
            e.printStackTrace();
        }
    }
}
