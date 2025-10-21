package com.company.paywho.controller;

import com.company.paywho.model.Utilidades;
import com.company.paywho.service.SesionServicio;
import com.company.paywho.service.UsuarioServicio;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PerfilController implements Initializable {

    private UsuarioServicio usuarioServicio;

    @Autowired
    public PerfilController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarDatosUsuario();
        inicializarBotones();
    }

    private void inicializarDatosUsuario() {
        txf_nombre.setText(SesionServicio.getUsuarioActual().getNombre());
        txf_apellido.setText(SesionServicio.getUsuarioActual().getApellido());
        txf_correo_electronico.setText(SesionServicio.getUsuarioActual().getCorreo_electronico());
        txf_balance_actual.setText(String.valueOf(SesionServicio.getUsuarioActual().getSaldo()));
        img_perfil.setImage(Utilidades.obtenerImagenPerfil(SesionServicio.getUsuarioActual().getRuta_img()));
    }

    private void inicializarBotones() {
        btn_editar.setOnAction(evento -> {
            editarPerfil();
        });

        btn_cambiar_img_perfil.setOnAction(evento -> {
            editarImagenPerfil(evento);
        });
    }

    private void editarPerfil() {
        if (esperarServicioSiContrasenaEsCorrecta()) {
            System.out.println("Contrasena correcta");
            if (esperarServicioEditarPerfil()) {
                System.out.println("Datos editados correctamente, para visualizarlos autentíquese de nuevo.");
            } else {
                System.out.println("Datos invalidos");
            }
        } else {
            System.out.println("Contrasena incorrecta.");
        }
    }

    private void editarImagenPerfil(ActionEvent evento) {
        File nuevaImagen = Utilidades.cargarImagen(evento);
        if (nuevaImagen != null) {
            if (usuarioServicio.editarImagenUsuario(SesionServicio.getUsuarioActual().getId_usuario(), nuevaImagen.getPath())) {
                System.out.println("Imagen editada correctamente, para visualizarla autentíquese de nuevo.");
            } else {
                System.out.println("Error al cargar la imagen.");
            }
        }
    }

    private boolean esperarServicioSiContrasenaEsCorrecta() {
        return usuarioServicio.validarUsuarioContrasena(SesionServicio.getUsuarioActual().getId_usuario(), pf_contrasena_actual.getText());
    }

    private boolean esperarServicioEditarPerfil() {
        String nuevoNombre = txf_nombre.getText();
        String nuevoApellido = txf_apellido.getText();
        String nuevoCorreoElectronico = txf_correo_electronico.getText();
        String nuevoBalance = txf_balance_actual.getText();
        return usuarioServicio.editarUsuario(nuevoNombre, nuevoApellido, nuevoCorreoElectronico, nuevoBalance, SesionServicio.getUsuarioActual().getId_usuario());
    }

    @FXML
    private TextField txf_correo_electronico;
    @FXML
    private TextField txf_nombre;
    @FXML
    private TextField txf_apellido;
    @FXML
    private TextField txf_balance_actual;
    @FXML
    private PasswordField pf_contrasena_actual;
    @FXML
    private ImageView img_perfil;
    @FXML
    private Button btn_editar;
    @FXML
    private Button btn_cambiar_img_perfil;
}
