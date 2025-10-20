package com.company.paywho.controller;

import com.company.paywho.service.SesionServicio;
import com.company.paywho.service.UsuarioServicio;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
        File file = new File(SesionServicio.getUsuarioActual().getRuta_img());
        Image image = new Image(file.toURI().toString());
        img_perfil.setImage(image);
    }

    private void inicializarBotones() {
        btn_editar.setOnAction(evento -> {
            if (esperarServicioSiContrasenaEsCorrecta()) {
                System.out.println("Contrasena correcta");
                if (editarPerfil()) {
                    System.out.println("Datos editados correctamente, para visualizarlos autentíquese de nuevo.");
                } else {
                    System.out.println("Datos invalidos");
                }
            } else {
                System.out.println("Contrasena incorrecta.");
            }
        });

        btn_cambiar_img_perfil.setOnAction(evento -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar imagen");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg")
            );

            Stage stage = (Stage) ((javafx.scene.Node) evento.getSource()).getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                try {
                    // Mostrar la imagen en el ImageView
                    Image image = new Image(file.toURI().toString());
                    img_perfil.setImage(image);

                    // Crear carpeta si no existe
                    File carpeta = new File("user_img");
                    if (!carpeta.exists()) {
                        carpeta.mkdir();
                    }

                    // ?Copiar la imagen a la carpeta del proyecto
                    String nombreArchivo = System.currentTimeMillis() + "_" + file.getName();
                    File destino = new File(carpeta, nombreArchivo);
                    Files.copy(file.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    if (usuarioServicio.editarImagenUsuario(SesionServicio.getUsuarioActual().getId_usuario(), destino.getPath())) {
                        System.out.println("Imagen editada correctamente, para visualizarla autentíquese de nuevo.");
                    } else {
                        System.out.println("Error al cargar la imagen.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean esperarServicioSiContrasenaEsCorrecta() {
        return usuarioServicio.validarUsuarioContrasena(SesionServicio.getUsuarioActual().getId_usuario(), pf_contrasena_actual.getText());
    }

    private boolean editarPerfil() {
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
