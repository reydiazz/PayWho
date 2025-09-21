package com.company.paywho.model;

import java.io.InputStream;
import java.util.Properties;

public class ArchivoServicio {

    private static ArchivoServicio instancia;
    private Properties rutas;
    private final String RUTA_ARCHIVO = "/config.properties";

    private ArchivoServicio() {
        try (InputStream fis = getClass().getResourceAsStream(RUTA_ARCHIVO)) {
            rutas = new Properties();
            rutas.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArchivoServicio getInstancia() {
        if (instancia == null) {
            instancia = new ArchivoServicio();
        }
        return instancia;
    }

    public String getRuta(String key) {
        return rutas.getProperty(key);
    }
}
