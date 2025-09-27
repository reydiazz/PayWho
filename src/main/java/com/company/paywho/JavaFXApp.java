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
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFXApp extends Application {

    private ConfigurableApplicationContext contexto;
    private final String APP_NOMBRE = "PayWho";
    private final String RUTA_FXML = "acceso.fxml";

    @Override
    public void init() {
        contexto = new SpringApplicationBuilder(SpringBootApp.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        iniciarLogin(stage);
    }

    @Override
    public void stop() {
        contexto.close();
    }

    private void iniciarLogin(Stage stage) {
        try {
            FXMLLoader fxmlLogin = new FXMLLoader(getClass().getResource(ArchivoServicio.getInstancia().getRuta(RUTA_FXML)));
            fxmlLogin.setControllerFactory(contexto::getBean);
            Parent raiz = fxmlLogin.load();
            Scene escena = new Scene(raiz);
            stage.setTitle(APP_NOMBRE);
            stage.setScene(escena);
            stage.show();
            AccesoController controlador = fxmlLogin.getController();
            controlador.setVentana(stage);
        } catch (Exception ex) {
            Logger.getLogger(JavaFXApp.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Archivo fxml no encontrado.");
        }
    }
}
