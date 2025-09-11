package com.company.paywho;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Main extends Application {
    
    public final String APP_NAME = "PayWho";
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ventana.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(APP_NAME);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
