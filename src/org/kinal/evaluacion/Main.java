package org.kinal.evaluacion;

import javafx.application.Platform;
import javafx.scene.control.Button;

public class Main {

    public static void main(String[] args) {

        System.out.println("EduControl - Prueba de dependencias");

        System.out.println("JavaFX: " + Button.class.getName());
        System.out.println("JavaFX Platform: " + Platform.class.getName());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Connector/J: OK");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connector/J: ERROR");
        }
    }
}