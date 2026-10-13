
package org.kinal.evaluacion.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.kinal.evaluacion.dao.UsuarioDAO;
import org.kinal.evaluacion.dao.impl.UsuarioDAOImpl;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Usuario;
import org.kinal.evaluacion.util.SecurityUtil;
import org.kinal.evaluacion.util.SessionContext;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    private final UsuarioDAO usuarioDAO =
            new UsuarioDAOImpl();

    @FXML
    private void handleIngresar() {

        String username = txtUsuario.getText().trim();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblMensaje.setText(
                    "Ingrese usuario y contraseña.");
            return;
        }

        try {
            Usuario usuario =
                    usuarioDAO.buscarPorUsername(username);

            if (usuario == null
                    || !SecurityUtil.verificarPassword(
                            password, usuario.getPasswordHash())) {

                lblMensaje.setText(
                        "Usuario o contraseña incorrectos.");
                txtPassword.clear();
                return;
            }

            if (!usuario.isActivo()) {
                lblMensaje.setText(
                        "Este usuario está desactivado.");
                return;
            }

            String rol = usuario.getRol();

            if (!"COORDINADOR_ACADEMICO".equals(rol)
                    && !"ESTUDIANTE".equals(rol)
                    && !"SECRETARIA".equals(rol)) {

                lblMensaje.setText(
                        "El usuario no tiene un rol válido.");
                return;
            }

            SessionContext.iniciarSesion(usuario);

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                            "/org/kinal/evaluacion/view/MenuView.fxml"
                        )
                );

                Parent root = loader.load();

                Stage stage = (Stage)
                        txtUsuario.getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.setTitle("EduControl - Menú principal");
                stage.centerOnScreen();

            } catch (IOException e) {
                SessionContext.cerrarSesion();
                lblMensaje.setText(
                        "No se pudo abrir el menú.");
                e.printStackTrace();
            }

        } catch (DaoException e) {
            lblMensaje.setText(
                    "Error al consultar la base de datos.");
            e.printStackTrace();
        }
    }
}
