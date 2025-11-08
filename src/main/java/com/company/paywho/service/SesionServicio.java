package com.company.paywho.service;

import com.company.paywho.entity.Usuario;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Service;

@Service
public class SesionServicio {

    private static Usuario usuarioActual;
    private static final BooleanProperty sesionActiva = new SimpleBooleanProperty(false);

    public static void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
        sesionActiva.set(true);
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
        sesionActiva.set(false);
    }

    public static boolean haySesionActiva() {
        return sesionActiva.get();
    }

    public static void sesionActiva() {
        sesionActiva.set(true);
    }

    public static void sesionDesactivada() {
        sesionActiva.set(false);
    }

    public static BooleanProperty sesionActivaProperty() {
        return sesionActiva;
    }

}
