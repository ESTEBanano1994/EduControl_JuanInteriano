
package org.kinal.evaluacion.controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.kinal.evaluacion.dao.CursoDAO;
import org.kinal.evaluacion.dao.impl.CursoDAOImpl;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Curso;
import org.kinal.evaluacion.util.SessionContext;

public class CursoController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtCreditos;
    @FXML private TextField txtHoras;

    @FXML private ComboBox<String> cmbEstado;

    @FXML private TableView<Curso> tablaCursos;

    @FXML private TableColumn<Curso, String> colCodigo;
    @FXML private TableColumn<Curso, String> colNombre;
    @FXML private TableColumn<Curso, Integer> colCreditos;
    @FXML private TableColumn<Curso, Integer> colHoras;
    @FXML private TableColumn<Curso, String> colEstado;

    @FXML private Label lblMensaje;

    private final CursoDAO cursoDAO = new CursoDAOImpl();

    private final ObservableList<Curso> listaCursos =
            FXCollections.observableArrayList();

    private Curso cursoSeleccionado;

    @FXML
    public void initialize() {

        cmbEstado.setItems(
                FXCollections.observableArrayList(
                        "ACTIVO", "INACTIVO"));

        cmbEstado.setValue("ACTIVO");

        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo"));

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));

        colCreditos.setCellValueFactory(
                new PropertyValueFactory<>("creditos"));

        colHoras.setCellValueFactory(
                new PropertyValueFactory<>("horasSemanales"));

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado"));

        tablaCursos.setItems(listaCursos);

        tablaCursos.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, anterior, nuevo) -> {
                    if (nuevo != null) {
                        mostrarCurso(nuevo);
                    }
                });

        boolean autorizado =
                SessionContext.tieneRol(
                        "COORDINADOR_ACADEMICO");

        txtCodigo.setDisable(!autorizado);
        txtNombre.setDisable(!autorizado);
        txtDescripcion.setDisable(!autorizado);
        txtCreditos.setDisable(!autorizado);
        txtHoras.setDisable(!autorizado);
        cmbEstado.setDisable(!autorizado);

        cargarCursos();
    }

    private void cargarCursos() {

        try {
            listaCursos.setAll(cursoDAO.listar());
            lblMensaje.setText(
                    "Cursos cargados correctamente.");

        } catch (DaoException e) {
            lblMensaje.setText(
                    "Error al cargar cursos: "
                    + e.getMessage());
        }
    }

    private void mostrarCurso(Curso curso) {

        cursoSeleccionado = curso;

        txtCodigo.setText(curso.getCodigo());
        txtNombre.setText(curso.getNombre());
        txtDescripcion.setText(curso.getDescripcion());

        txtCreditos.setText(
                String.valueOf(curso.getCreditos()));

        txtHoras.setText(
                String.valueOf(curso.getHorasSemanales()));

        cmbEstado.setValue(curso.getEstado());
    }

    private boolean autorizado() {

        if (!SessionContext.tieneRol(
                "COORDINADOR_ACADEMICO")) {

            lblMensaje.setText(
                    "No tiene permisos para esta operación.");
            return false;
        }

        return true;
    }

    private Curso leerFormulario() {

        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion =
                txtDescripcion.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            throw new IllegalArgumentException(
                    "Código y nombre son obligatorios.");
        }

        int creditos;
        int horas;

        try {
            creditos = Integer.parseInt(
                    txtCreditos.getText().trim());

            horas = Integer.parseInt(
                    txtHoras.getText().trim());

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Créditos y horas deben ser números enteros.");
        }

        if (creditos <= 0 || horas <= 0) {
            throw new IllegalArgumentException(
                    "Créditos y horas deben ser mayores que cero.");
        }

        Curso curso = new Curso();

        curso.setCodigo(codigo);
        curso.setNombre(nombre);
        curso.setDescripcion(descripcion);
        curso.setCreditos(creditos);
        curso.setHorasSemanales(horas);
        curso.setEstado(cmbEstado.getValue());

        return curso;
    }

    @FXML
    private void handleAgregar() {

        if (!autorizado()) return;

        try {
            Curso curso = leerFormulario();

            cursoDAO.insertar(curso);

            cargarCursos();
            limpiarFormulario();

            lblMensaje.setText(
                    "Curso agregado correctamente.");

        } catch (DaoException | IllegalArgumentException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void handleActualizar() {

        if (!autorizado()) return;

        if (cursoSeleccionado == null) {
            lblMensaje.setText(
                    "Seleccione un curso de la tabla.");
            return;
        }

        try {
            Curso curso = leerFormulario();

            curso.setIdCurso(
                    cursoSeleccionado.getIdCurso());

            cursoDAO.actualizar(curso);

            cargarCursos();
            limpiarFormulario();

            lblMensaje.setText(
                    "Curso actualizado correctamente.");

        } catch (DaoException | IllegalArgumentException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {

        if (!autorizado()) return;

        if (cursoSeleccionado == null) {
            lblMensaje.setText(
                    "Seleccione un curso para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Eliminar curso");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Desea eliminar el curso seleccionado?");

        if (confirmacion.showAndWait()
                .filter(ButtonType.OK::equals)
                .isEmpty()) {
            return;
        }

        try {
            cursoDAO.eliminar(
                    cursoSeleccionado.getIdCurso());

            cargarCursos();
            limpiarFormulario();

            lblMensaje.setText(
                    "Curso eliminado correctamente.");

        } catch (DaoException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void handleLimpiar() {
        limpiarFormulario();
        lblMensaje.setText("");
    }

    private void limpiarFormulario() {

        tablaCursos.getSelectionModel().clearSelection();

        cursoSeleccionado = null;

        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtCreditos.clear();
        txtHoras.clear();

        cmbEstado.setValue("ACTIVO");
    }

    @FXML
    private void handleRegresar() {

        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource(
                        "/org/kinal/evaluacion/view/MenuView.fxml"));

            Stage stage = (Stage)
                    tablaCursos.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("EduControl - Menú principal");
            stage.centerOnScreen();

        } catch (IOException e) {
            lblMensaje.setText(
                    "No se pudo regresar al menú.");
            e.printStackTrace();
        }
    }
}
