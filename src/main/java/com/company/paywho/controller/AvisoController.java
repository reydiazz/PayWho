package com.company.paywho.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AvisoController implements Initializable {

    private Stage ventana;
    private double recorridoX;
    private double recorridoY;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarBotones();
        inicializarToolbar();
    }

    private void inicializarBotones() {
        btn_aceptar_uno.setOnAction(evento -> {
            ventana.close();
        });
    }

    private void inicializarToolbar() {
        hb_toolbar.setOnMousePressed(evento -> {
            actualizarRecorridoVentana(evento);
        });
        hb_toolbar.setOnMouseDragged(evento -> {
            calcularNuevaPosicionVentana(evento);
        });
    }

    public void inicializarMensaje(String mensaje) {
        lbl_mensaje.setText(mensaje);
    }

    private void actualizarRecorridoVentana(MouseEvent evento) {
        recorridoX = evento.getSceneX();
        recorridoY = evento.getSceneY();
    }

    private void calcularNuevaPosicionVentana(MouseEvent evento) {
        ventana.setX(evento.getScreenX() - recorridoX);
        ventana.setY(evento.getScreenY() - recorridoY);
    }

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }
    
    @FXML
    private Label lbl_mensaje;
    @FXML
    private HBox hb_toolbar;
    @FXML
    private Button btn_aceptar_uno;
}
