package com.company.paywho.controller;

import com.company.paywho.model.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class VentanaController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarBotones();
    }

    private void inicializarBotones() {

    }

    private void inicializarBotonesPrincipales() {
        
    }

    private void cambiarEscena(String ruta) {
        try {
            limpiarEscenarioYCambiarEscena(ruta);
        } catch (Exception e) {
            System.out.println("No se encontro el archivo fxml");
        }
    }

    private void limpiarEscenarioYCambiarEscena(String ruta) throws IOException {
        sp_contenido.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        sp_contenido.getChildren().add(root);
    }

    @FXML
    private StackPane sp_contenido;
    @FXML
    private BorderPane bp_root;
    @FXML
    private HBox hb_toolbar;
    @FXML
    private Button btn_inicio;
    @FXML
    private Button btn_ingreso;
    @FXML
    private Button btn_gasto;
    @FXML
    private Button btn_ahorro;
    @FXML
    private Button btn_categoria;
    @FXML
    private Button btn_ajuste;
    @FXML
    private Button btn_log_out;
    @FXML
    private Button btn_minimizar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Button btn_perfil;

}
