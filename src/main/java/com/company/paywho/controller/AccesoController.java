package com.company.paywho.controller;

import com.company.paywho.model.ArchivoServicio;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccesoController implements Initializable {

    private Stage ventana;

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void iniciarMenuPrincipal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ArchivoServicio.getInstancia().getRuta("ventana.fxml")));
        Parent root = loader.load();
        ventana.setScene(new Scene(root));
        VentanaController controlador = loader.getController();
        controlador.setVentana(ventana);
    }

}
