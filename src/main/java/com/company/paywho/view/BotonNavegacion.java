package com.company.paywho.view;

import javafx.scene.control.Button;

public class BotonNavegacion {

    private Button boton;
    private String ruta;

    public BotonNavegacion(Button boton, String ruta) {
        this.boton = boton;
        this.ruta = ruta;
    }

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}
