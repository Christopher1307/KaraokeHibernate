package aed.ui;

import aed.ui.controllers.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KaraokeApp extends Application {

    LoginController loginController = new LoginController();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(loginController.getLoginRoot());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
