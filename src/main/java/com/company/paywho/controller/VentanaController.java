package com.company.paywho.controller;

import com.company.paywho.JavaFXApp;
import com.company.paywho.model.ArchivoServicio;
import com.company.paywho.model.Utilidades;
import com.company.paywho.service.SesionServicio;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class VentanaController implements Initializable {

    private Stage ventana;

    private double recorridoX;
    private double recorridoY;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarComponentesVista();
    }

    private void inicializarComponentesVista() {
        inicializarToolbar();
        inicializarBotonesNavegacion();
        inicializarBotonesFuncionales();
        inicializarLogin();
        inicializarAutenticacion();
    }

    private void inicializarBotonesNavegacion() {
        LinkedList<BotonNavegacion> botonesNavegacion = new LinkedList<>();
        botonesNavegacion.add(new BotonNavegacion(btn_inicio, ArchivoServicio.getInstancia().getRuta("inicio.fxml")));
        botonesNavegacion.add(new BotonNavegacion(btn_ingreso, ArchivoServicio.getInstancia().getRuta("ingreso.fxml")));
        botonesNavegacion.add(new BotonNavegacion(btn_gasto, ArchivoServicio.getInstancia().getRuta("gasto.fxml")));
        botonesNavegacion.add(new BotonNavegacion(btn_ahorro, ArchivoServicio.getInstancia().getRuta("ahorro.fxml")));
        botonesNavegacion.add(new BotonNavegacion(btn_categoria, ArchivoServicio.getInstancia().getRuta("categoria.fxml")));
        botonesNavegacion.add(new BotonNavegacion(btn_perfil, ArchivoServicio.getInstancia().getRuta("perfil.fxml")));
        asignarRutasBotonesNavegacion(botonesNavegacion);
    }

    private void inicializarBotonesFuncionales() {
        btn_cerrar.setOnAction(evento -> {
            cerrarVentana();
        });
        btn_minimizar.setOnAction(evento -> {
            minimizarVentana();
        });
        btn_cerrar_sesion.setOnAction(evento -> {
            cerrarSesion();
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

    private void inicializarLogin() {
        Utilidades.visibilidadComponentes(obtenerGrupoComponentesEliminarLogin(), false);
        actualizarEtiquetas("User", "Bienvenido a tu espacio financiero", "Inicia sesión o regístrate para continuar.");
        establecerEscena(ArchivoServicio.getInstancia().getRuta("acceso.fxml"));
    }

    public void inicializarAutenticacion() {
        SesionServicio.sesionActivaProperty().addListener((observado, antiguoValor, nuevoValor) -> {
            if (nuevoValor) {
                Utilidades.visibilidadComponentes(obtenerGrupoComponentesEliminarLogin(), true);
                establecerEscena(ArchivoServicio.getInstancia().getRuta("inicio.fxml"));
                actualizarEtiquetas(SesionServicio.getUsuarioActual().getNombreApellido(), "Bienvenido de nuevo, " + SesionServicio.getUsuarioActual().getNombre(), "Un paso más hacia tus metas financieras.");
                actualizarImagenPerfil(SesionServicio.getUsuarioActual().getRuta_img());
            }
        });
    }

    private void establecerEscena(String ruta) {
        try {
            sp_contenido.getChildren().clear();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(ruta));
            fxml.setControllerFactory(JavaFXApp.getContexto()::getBean);
            Parent root = fxml.load();
            sp_contenido.getChildren().add(root);
            System.out.println("Cargo correctamente.");
        } catch (Exception e) {
            System.out.println("No se encontro el archivo fxml.");
        }
    }

    private void actualizarEtiquetas(String perfil, String titulo, String descripcion) {
        btn_perfil.setText(perfil);
        lbl_titulo_principal.setText(titulo);
        lbl_titulo_secundario.setText(descripcion);
    }

    private void actualizarImagenPerfil(String rutaImg) {
        img_perfil.setImage(Utilidades.obtenerImagenPerfil(rutaImg));
    }

    private void asignarRutasBotonesNavegacion(LinkedList<BotonNavegacion> botones) {
        for (BotonNavegacion boton : botones) {
            boton.getBoton().setOnAction(evento -> {
                establecerEscena(boton.getRuta());
            });
        }
    }

    private LinkedList obtenerGrupoComponentesEliminarLogin() {
        LinkedList<Node> nodos = new LinkedList<>();
        nodos.add(vb_contendor_botones_principales);
        nodos.add(vb_contendor_botones_secundarios);
        nodos.add(btn_perfil);
        return nodos;
    }

    private void actualizarRecorridoVentana(MouseEvent evento) {
        recorridoX = evento.getSceneX();
        recorridoY = evento.getSceneY();
    }

    private void calcularNuevaPosicionVentana(MouseEvent evento) {
        ventana.setX(evento.getScreenX() - recorridoX);
        ventana.setY(evento.getScreenY() - recorridoY);
    }

    private void cerrarSesion() {
        SesionServicio.cerrarSesion();
        inicializarLogin();
    }

    private void cerrarVentana() {
        ventana.close();
    }

    private void minimizarVentana() {
        ventana.setIconified(true);
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
    private Label lbl_titulo_secundario;
    @FXML
    private StackPane sp_contenido;
    @FXML
    private VBox vb_contendor_botones_principales;
    @FXML
    private VBox vb_contendor_botones_secundarios;
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
    @FXML
    private ImageView img_perfil;

}
