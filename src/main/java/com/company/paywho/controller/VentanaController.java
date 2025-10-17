package com.company.paywho.controller;

import com.company.paywho.JavaFXApp;
import com.company.paywho.model.ArchivoServicio;
import com.company.paywho.view.BotonNavegacion;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class VentanaController implements Initializable {

    private Stage ventana;
    private double xOffset;
    private double yOffset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarBotones();
    }

    private void inicializarBotones() {
        inicializarBotonesPrincipales();
        inicializarBotonesSecundarios();
        inicializarInicio();
    }

    private void inicializarBotonesPrincipales() {
        LinkedList<BotonNavegacion> botonesPrincipales = new LinkedList<>();
        botonesPrincipales.add(new BotonNavegacion(btn_inicio, ArchivoServicio.getInstancia().getRuta("inicio.fxml")));
        botonesPrincipales.add(new BotonNavegacion(btn_ingreso, ArchivoServicio.getInstancia().getRuta("ingreso.fxml")));
        botonesPrincipales.add(new BotonNavegacion(btn_gasto, ArchivoServicio.getInstancia().getRuta("gasto.fxml")));
        botonesPrincipales.add(new BotonNavegacion(btn_ahorro, ArchivoServicio.getInstancia().getRuta("ahorro.fxml")));
        botonesPrincipales.add(new BotonNavegacion(btn_categoria, ArchivoServicio.getInstancia().getRuta("categoria.fxml")));
        botonesPrincipales.add(new BotonNavegacion(btn_perfil, ArchivoServicio.getInstancia().getRuta("ajuste.fxml")));
        asignarRutaBotones(botonesPrincipales);
    }

    private void inicializarInicio() {
        cambiarEscena(ArchivoServicio.getInstancia().getRuta("inicio.fxml"));
    }

    private void inicializarBotonesSecundarios() {
        btn_cerrar.setOnAction(evento -> {
            ventana.close();
        });
        btn_minimizar.setOnAction(evento -> {
            ventana.setIconified(true);
        });
        btn_log_out.setOnAction(evento -> {
            JavaFXApp app = new JavaFXApp();
            app.iniciarLogin(ventana, app.getContexto());
        });
        hb_toolbar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        hb_toolbar.setOnMouseDragged(event -> {
            ventana.setX(event.getScreenX() - xOffset);
            ventana.setY(event.getScreenY() - yOffset);
        });
    }

    private void cambiarEscena(String ruta) {
        try {
            sp_contenido.getChildren().clear();
            Parent root = FXMLLoader.load(getClass().getResource(ruta));
            sp_contenido.getChildren().add(root);
            System.out.println("Cargo correctamente.");
        } catch (Exception e) {
            System.out.println("No se encontro el archivo fxml.");
        }
    }

    private void asignarRutaBotones(LinkedList<BotonNavegacion> botones) {
        for (BotonNavegacion boton : botones) {
            boton.getBoton().setOnAction(evento -> {
                cambiarEscena(boton.getRuta());
            });
        }
    }

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    @FXML
    private HBox hb_toolbar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Button btn_minimizar;
    @FXML
    private StackPane sp_contenido;
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
    private Button btn_perfil;

}
