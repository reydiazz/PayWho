package com.company.paywho.model;

import java.io.FileInputStream;
import java.util.Properties;

public class ArchivoServicio {

    private static ArchivoServicio instancia;
    private Properties rutas;

    private ArchivoServicio() {
        rutas = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
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
