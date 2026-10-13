
package org.kinal.evaluacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EduControlApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(
                getClass().getResource(
                    "/org/kinal/evaluacion/view/LoginView.fxml"
                )
        );

        Scene scene = new Scene(root);

        stage.setTitle("EduControl - Inicio de Sesion");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

