
package org.kinal.evaluacion.controller;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.kinal.evaluacion.dao.SeccionDAO;
import org.kinal.evaluacion.dao.MatriculaDAO;
import org.kinal.evaluacion.dao.impl.SeccionDAOImpl;
import org.kinal.evaluacion.dao.impl.MatriculaDAOImpl;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Seccion;
import org.kinal.evaluacion.model.Usuario;
import org.kinal.evaluacion.util.SessionContext;

public class PrematriculaController {

    @FXML private Label lblEstudiante;
    @FXML private Label lblMensaje;

    @FXML private TableView<Seccion> tablaSecciones;

    @FXML private TableColumn<Seccion, Integer> colIdSeccion;
    @FXML private TableColumn<Seccion, Integer> colIdCurso;
    @FXML private TableColumn<Seccion, Integer> colDocente;
    @FXML private TableColumn<Seccion, Integer> colCupoMaximo;
    @FXML private TableColumn<Seccion, Integer> colCupoDisponible;

    @FXML private Button btnPrematricular;

    private final SeccionDAO seccionDAO =
            new SeccionDAOImpl();

    private final MatriculaDAO matriculaDAO =
            new MatriculaDAOImpl();

    private final ObservableList<Seccion> listaSecciones =
            FXCollections.observableArrayList();

    private int idEstudiante = -1;

    @FXML
    public void initialize() {

        colIdSeccion.setCellValueFactory(
                new PropertyValueFactory<>("idSeccion"));

        colIdCurso.setCellValueFactory(
                new PropertyValueFactory<>("idCurso"));

        colDocente.setCellValueFactory(
                new PropertyValueFactory<>("idDocente"));

        colCupoMaximo.setCellValueFactory(
                new PropertyValueFactory<>("cupoMaximo"));

        colCupoDisponible.setCellValueFactory(
                new PropertyValueFactory<>("cupoDisponible"));

        tablaSecciones.setItems(listaSecciones);

        Usuario usuario = SessionContext.getUsuarioActivo();

        if (usuario == null ||
                !SessionContext.tieneRol("ESTUDIANTE")) {

            lblEstudiante.setText("Acceso no autorizado");

            lblMensaje.setText(
                    "Debe iniciar sesión como estudiante.");

            btnPrematricular.setDisable(true);
            return;
        }

        lblEstudiante.setText(
                "Estudiante: " + usuario.getNombreCompleto());

        btnPrematricular.setDisable(true);

        lblMensaje.setText(
                "Cargando información del estudiante...");

        cargarSecciones();
    }

    private void cargarSecciones() {

        try {

            listaSecciones.setAll(seccionDAO.listar());

            lblMensaje.setText(
                    "Secciones cargadas correctamente.");

        } catch (DaoException e) {

            lblMensaje.setText(
                    "Error al cargar secciones: "
                    + e.getMessage());
        }
    }

    public void establecerIdEstudiante(int idEstudiante) {

        if (idEstudiante <= 0) {

            this.idEstudiante = -1;

            lblMensaje.setText(
                    "El estudiante no tiene un ID válido.");

            btnPrematricular.setDisable(true);
            return;
        }

        this.idEstudiante = idEstudiante;

        btnPrematricular.setDisable(false);

        lblMensaje.setText(
                "Seleccione una sección para prematricularse.");
    }

    @FXML
    private void handlePrematricular() {

        if (!SessionContext.tieneRol("ESTUDIANTE")) {

            lblMensaje.setText(
                    "No tiene permisos para prematricular.");

            return;
        }

        if (idEstudiante <= 0) {

            lblMensaje.setText(
                    "No se pudo identificar al estudiante.");

            return;
        }

        Seccion seleccionada =
                tablaSecciones.getSelectionModel()
                        .getSelectedItem();

        if (seleccionada == null) {

            lblMensaje.setText(
                    "Seleccione una sección de la tabla.");

            return;
        }

        if (seleccionada.getCupoDisponible() <= 0) {

            lblMensaje.setText(
                    "La sección no tiene cupos disponibles.");

            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Confirmar prematrícula");
        confirmacion.setHeaderText(null);

        confirmacion.setContentText(
                "¿Desea prematricularse en la sección "
                + seleccionada.getCodigoSeccion() + "?");

        if (confirmacion.showAndWait()
                .filter(ButtonType.OK::equals)
                .isEmpty()) {

            return;
        }

        try {

            matriculaDAO.prematricularSeccion(
                    idEstudiante,
                    seleccionada.getIdSeccion());

            cargarSecciones();

            tablaSecciones.getSelectionModel()
                    .clearSelection();

            lblMensaje.setText(
                    "Prematrícula realizada correctamente.");

        } catch (DaoException e) {

            lblMensaje.setText(
                    "No se pudo completar la prematrícula: "
                    + e.getMessage());
        }
    }

    @FXML
    private void handleActualizar() {

        cargarSecciones();
    }

    @FXML
    private void handleRegresar() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(
                            "/org/kinal/evaluacion/view/MenuView.fxml"));

            Stage stage = (Stage)
                    tablaSecciones.getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.setTitle(
                    "EduControl - Menú principal");

            stage.centerOnScreen();

        } catch (IOException e) {

            lblMensaje.setText(
                    "No se pudo regresar al menú.");

            e.printStackTrace();
        }
    }
}
