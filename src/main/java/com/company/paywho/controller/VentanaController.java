package com.company.paywho.controller;

import com.company.paywho.JavaFXApp;
import com.company.paywho.model.ArchivoServicio;
import com.company.paywho.service.SesionServicio;
import com.company.paywho.service.UsuarioServicio;
import com.company.paywho.view.BotonNavegacion;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VentanaController implements Initializable {

    private UsuarioServicio usuarioServicio;
    private SesionServicio sesionServicio;

    private Stage ventana;

    private double recorridoX;
    private double recorridoY;

    @Autowired
    public VentanaController(UsuarioServicio usuarioServicio, SesionServicio sesionServicio) {
        this.sesionServicio = sesionServicio;
        this.usuarioServicio = usuarioServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarBotones();
    }

    private void inicializarBotones() {
        inicializarBotonesPrincipales();
        inicializarBotonesSecundarios();
        inicializarToolbar();
        inicializarLogin();
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

    private void inicializarLogin() {
        visibilidadComponentes(recojerGrupoComponentes(1), false);
        cambiarEscena(ArchivoServicio.getInstancia().getRuta("acceso.fxml"));
        sesionServicio.sesionActivaProperty().addListener((observado, antiguoValor, nuevoValor) -> {
            if (nuevoValor) {
                visibilidadComponentes(recojerGrupoComponentes(1), true);
                cambiarEscena(ArchivoServicio.getInstancia().getRuta("inicio.fxml"));
                inicializarEtiquetasSegunUsuario();
            }
        });
    }

    private void inicializarBotonesSecundarios() {
        btn_cerrar.setOnAction(evento -> {
            ventana.close();
        });
        btn_minimizar.setOnAction(evento -> {
            ventana.setIconified(true);
        });
        btn_cerrar_sesion.setOnAction(evento -> {
            sesionServicio.setSesionActiva(false);
            inicializarLogin();
        });
    }

    private void inicializarToolbar() {
        hb_toolbar.setOnMousePressed(event -> {
            recorridoX = event.getSceneX();
            recorridoY = event.getSceneY();
        });
        hb_toolbar.setOnMouseDragged(event -> {
            ventana.setX(event.getScreenX() - recorridoX);
            ventana.setY(event.getScreenY() - recorridoY);
        });
    }

    private void cambiarEscena(String ruta) {
        try {
            sp_contenido.getChildren().clear();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(ruta));
            fxml.setControllerFactory(JavaFXApp.getContexto()::getBean);
            Parent root = fxml.load();
            sp_contenido.getChildren().add(root);
            System.out.println("Cargo correctamente.");
        } catch (Exception e) {
            System.out.println("No se encontro el archivo fxml.");
            e.printStackTrace();
        }
    }

    private LinkedList recojerGrupoComponentes(int opcion) {
        LinkedList<Node> nodos = new LinkedList<>();
        switch (opcion) {
            // Componentes que no deben estar al iniciar sesion
            case 1:
                nodos.add(vb_contendor_botones_principales);
                nodos.add(vb_contendor_botones_secundarios);
                nodos.add(hb_head);
                break;
            default:
                throw new AssertionError();
        }
        return nodos;
    }

    private void asignarRutaBotones(LinkedList<BotonNavegacion> botones) {
        for (BotonNavegacion boton : botones) {
            boton.getBoton().setOnAction(evento -> {
                cambiarEscena(boton.getRuta());
            });
        }
    }

    private void visibilidadComponentes(LinkedList<Node> componentes, boolean visibilidad) {
        for (Node componente : componentes) {
            if (visibilidad) {
                componente.setVisible(true);
                componente.setManaged(true);
            } else {
                componente.setVisible(false);
                componente.setManaged(false);
            }
        }
    }

    public void inicializarEtiquetasSegunUsuario() {
        btn_perfil.setText(usuarioServicio.getUsuarioActual().getNombre() + " " + usuarioServicio.getUsuarioActual().getApellido());
        lbl_titulo_principal.setText("Bienvenido de nuevo, " + usuarioServicio.getUsuarioActual().getNombre());
    }

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    @FXML
    private Label lbl_titulo_principal;
    @FXML
    private StackPane sp_contenido;
    @FXML
    private VBox vb_contendor_botones_principales;
    @FXML
    private VBox vb_contendor_botones_secundarios;
    @FXML
    private HBox hb_head;
    @FXML
    private HBox hb_toolbar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Button btn_minimizar;
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
    private Button btn_cerrar_sesion;
    @FXML
    private Button btn_perfil;

}
