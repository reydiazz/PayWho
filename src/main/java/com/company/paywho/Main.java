package com.company.paywho;

import com.company.paywho.controller.AccesoController;
import com.company.paywho.model.ArchivoServicio;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private final String APP_NAME = "PayWho";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        iniciarLogin(stage);
    }

    private void iniciarLogin(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ArchivoServicio.getInstancia().getRuta("acceso.fxml")));
        Parent root = loader.load();
        stage.setTitle(APP_NAME);
        stage.setScene(new Scene(root));
        stage.show();
        AccesoController controlador = loader.getController();
        controlador.setVentana(stage);
    }

}
