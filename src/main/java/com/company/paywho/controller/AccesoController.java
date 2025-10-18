package com.company.paywho.controller;

import com.company.paywho.service.SesionServicio;
import com.company.paywho.service.UsuarioServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AccesoController {

    private UsuarioServicio usuarioServicio;
    private SesionServicio sesionServicio;

    @Autowired
    public AccesoController(UsuarioServicio usuarioServicio, SesionServicio sesionServicio) {
        this.sesionServicio = sesionServicio;
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
            sesionServicio.setSesionActiva(true);
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void registrarUsuario() {
        if (enviarDatosEsperarRespuestaServicioRegistro()) {
            sesionServicio.setSesionActiva(true);
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
        String saldo = txf_balance_registro.getText();
        return usuarioServicio.registrarUsuario(nombre, apellido, correo_electronico, contrasena, saldo);
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

}
