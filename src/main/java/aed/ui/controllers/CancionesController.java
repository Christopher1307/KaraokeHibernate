package aed.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CancionesController  implements Initializable {

    @FXML
    private BorderPane cancionesRoot;

    @FXML
    private ListView<?> cancionesListView;


    public CancionesController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CancionesView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public BorderPane getCancionesRoot() {
        return cancionesRoot;
    }

    public void setCancionesRoot(BorderPane cancionesRoot) {
        this.cancionesRoot = cancionesRoot;
    }
}
