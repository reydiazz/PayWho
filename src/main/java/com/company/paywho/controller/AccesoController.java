package com.company.paywho.controller;

import com.company.paywho.model.Utilidades;
import com.company.paywho.service.UsuarioServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AccesoController {

    private UsuarioServicio usuarioServicio;

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
        btn_crear_cuenta.setOnAction(evento -> {
            mostrarPlantillaRegistro();
        });
        btn_logear.setOnAction(evento -> {
            mostrarPlantillaLogin();
        });
    }

    private void iniciarSesion() {
        if (!enviarDatosEsperarRespuestaServicioLogin()) {
            Utilidades.crearModal("Acceso denegado.");
        } 
    }

    private void registrarUsuario() {
        if (disponibilidadCorreoElectronico()) {
            Utilidades.crearModal("Correo electronico ya registrado.");
        } else {
            if (enviarDatosEsperarRespuestaServicioRegistro()) {
                Utilidades.crearModal("Usuario registrado");
            } else {
                Utilidades.crearModal("Complete el formulario correctamente.");
            }
        }

    }

    private boolean disponibilidadCorreoElectronico() {
        String correo = txf_correo_registrar.getText();
        return usuarioServicio.validarCorreoElectronico(correo);
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
        String saldo = txf_balance_registro.getText();
        return usuarioServicio.registrarUsuario(nombre, apellido, correo_electronico, contrasena, saldo);
    }

    private void mostrarPlantillaRegistro() {
        Utilidades.visibilidadComponente(vb_iniciar_sesion, false);
        Utilidades.visibilidadComponente(vb_registrarse, true);
    }

    private void mostrarPlantillaLogin() {
        Utilidades.visibilidadComponente(vb_registrarse, false);
        Utilidades.visibilidadComponente(vb_iniciar_sesion, true);
    }

    @FXML
    private Button btn_logear;
    @FXML
    private Button btn_crear_cuenta;
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
    @FXML
    private VBox vb_iniciar_sesion;
    @FXML
    private VBox vb_registrarse;

}
