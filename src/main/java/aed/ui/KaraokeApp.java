package aed.ui;

import aed.db.EntityManagerUtil;
import aed.ui.controllers.LoginController;
import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KaraokeApp extends Application {

    LoginController loginController = new LoginController();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the EntityManagerUtil

        Scene scene = new Scene(loginController.getLoginRoot());
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}