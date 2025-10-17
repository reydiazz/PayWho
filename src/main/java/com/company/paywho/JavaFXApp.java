package com.company.paywho;

import com.company.paywho.controller.AccesoController;
import com.company.paywho.model.ArchivoServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFXApp extends Application {

    private static ConfigurableApplicationContext contexto;

    @Override
    public void init() {
        contexto = new SpringApplicationBuilder(SpringBootApp.class).run();
    }

    public static ConfigurableApplicationContext getContexto() {
        return contexto;
    }

    @Override
    public void start(Stage stage) throws Exception {
        iniciarLogin(stage, contexto);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public void stop() {
        contexto.close();
    }

    public void iniciarLogin(Stage stage, ConfigurableApplicationContext contexto) {
        try {
            FXMLLoader fxmlLogin = new FXMLLoader(
                    getClass().getResource(ArchivoServicio.getInstancia().getRuta("acceso.fxml"))
            );
            fxmlLogin.setControllerFactory(contexto::getBean);
            Parent raiz = fxmlLogin.load();
            Scene escena = new Scene(raiz);
            stage.setScene(escena);
            AccesoController controlador = fxmlLogin.getController();
            controlador.setVentana(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
