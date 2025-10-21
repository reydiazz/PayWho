package com.company.paywho.model;

import com.company.paywho.service.SesionServicio;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Utilidades {

    public static <T extends Node> LinkedList<T> recojerNodos(Parent root, Parent evitar, Class<T> tipo) {
        LinkedList<T> lista = new LinkedList<>();
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node == evitar) {
                continue;
            }
            if (tipo.isInstance(node)) {
                lista.add(tipo.cast(node));
            }
            if (node instanceof Parent) {
                lista.addAll(recojerNodos((Parent) node, evitar, tipo));
            }
        }
        return lista;
    }

    public static String sha256(final String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void visibilidadComponentes(LinkedList<Node> componentes, boolean visibilidad) {
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

    public static void visibilidadComponente(Node componente, boolean visibilidad) {
        if (visibilidad) {
            componente.setVisible(true);
            componente.setManaged(true);
        } else {
            componente.setVisible(false);
            componente.setManaged(false);
        }
    }

    public static File cargarImagen(ActionEvent evento) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) ((javafx.scene.Node) evento.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                File carpeta = new File("user_img");
                if (!carpeta.exists()) {
                    carpeta.mkdir();
                }
                String nombreArchivo = System.currentTimeMillis() + "_" + file.getName();
                File destino = new File(carpeta, nombreArchivo);
                Files.copy(file.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return destino;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Image obtenerImagenPerfil(String rutaImg) {
        Image image = null;
        File file = new File(rutaImg);
        if (file.exists()) {
            image = new Image(file.toURI().toString());
        } else {
            System.out.println("Imagen no encontrada.");
            image = new Image(Thread.currentThread().getContextClassLoader().getResource("img/user_img_default.png").toExternalForm());
        }
        return image;
    }

}
