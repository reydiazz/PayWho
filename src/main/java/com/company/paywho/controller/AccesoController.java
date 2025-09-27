package com.company.paywho.controller;

import com.company.paywho.model.ArchivoServicio;
import com.company.paywho.service.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class AccesoController {

    private Stage ventana;
    private final UsuarioServicio usuarioServicio;

    @Autowired
    public AccesoController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @FXML
    public void initialize() {
        inicializarBotones();
    }

    private void inicializarBotones() {
        btn_iniciar_sesion.setOnAction(evento -> {
            iniciarSesion();
        });
        btn_registrarse.setOnAction(evento -> {
            registrarUsuario();
        });
    }

    private void iniciarSesion() {
        if (enviarDatosEsperarRespuestaServicioLogin()) {
            iniciarMenuPrincipal();
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void registrarUsuario() {
        if (enviarDatosEsperarRespuestaServicioRegistro()) {
            iniciarMenuPrincipal();
        } else {
            System.out.println("Datos no corespondientes.");
        }
    }

    private boolean enviarDatosEsperarRespuestaServicioLogin() {
        String correo = txf_correo.getText();
        String contrasena = pf_contrasena.getText();
        return usuarioServicio.validarUsuario(correo, contrasena);
    }

    private boolean enviarDatosEsperarRespuestaServicioRegistro() {
        String nombre = txf_nombre_registrar.getText();
        String apellido = txf_apellido_registrar.getText();
        String correo_electronico = txf_correo_registrar.getText();
        String contrasena = pf_contrasena_registrar.getText();
        Long saldo = Long.parseLong(txf_balance_registro.getText());
        return usuarioServicio.registrarUsuario(nombre, apellido, correo_electronico, contrasena, saldo);
    }

    private void iniciarMenuPrincipal() {
        try {
            FXMLLoader fxmlMenuPrincipal = new FXMLLoader(getClass().getResource(ArchivoServicio.getInstancia().getRuta("ventana.fxml")));
            Parent raiz = fxmlMenuPrincipal.load();
            Scene escena = new Scene(raiz);
            ventana.setScene(escena);
            VentanaController controlador = fxmlMenuPrincipal.getController();
            controlador.setVentana(ventana);
        } catch (Exception ex) {
            Logger.getLogger(AccesoController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Archivo fxml no encontrado.");
        }
    }
    
    @FXML
    private PasswordField pf_contrasena;
    @FXML
    private TextField txf_correo;
    @FXML
    private Button btn_iniciar_sesion;
    @FXML
    private TextField txf_correo_registrar;
    @FXML
    private TextField txf_nombre_registrar;
    @FXML
    private TextField txf_apellido_registrar;
    @FXML
    private PasswordField pf_contrasena_registrar;
    @FXML
    private Button btn_registrarse;
    @FXML
    private TextField txf_balance_registro;

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }
}
