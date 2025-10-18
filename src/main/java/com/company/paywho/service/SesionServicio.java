package com.company.paywho.service;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Component;

@Component
public class SesionServicio {

    private final BooleanProperty sesionActiva = new SimpleBooleanProperty(false);

    public BooleanProperty sesionActivaProperty() {
        return sesionActiva;
    }

    public boolean isSesionActiva() {
        return sesionActiva.get();
    }

    public void setSesionActiva(boolean valor) {
        sesionActiva.set(valor);
    }
}
