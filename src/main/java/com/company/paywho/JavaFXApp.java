package com.company.paywho;

import com.company.paywho.controller.VentanaController;
import com.company.paywho.model.ArchivoServicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFXApp extends Application {

    private static ConfigurableApplicationContext configuracionApp;

    @Override
    public void init() {
        configuracionApp = new SpringApplicationBuilder(SpringBootApp.class).run();
    }

    public static ConfigurableApplicationContext getContexto() {
        return configuracionApp;
    }

    @Override
    public void start(Stage stage) throws Exception {
        iniciarApp(stage);
    }

    @Override
    public void stop() {
        configuracionApp.close();
    }

    public void iniciarApp(Stage stage) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(ArchivoServicio.getInstancia().getRuta("ventana.fxml")));
            fxml.setControllerFactory(configuracionApp::getBean);
            Parent raiz = fxml.load();
            Scene escena = new Scene(raiz);
            stage.setScene(escena);
            VentanaController controlador = fxml.getController();
            controlador.setVentana(stage);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
