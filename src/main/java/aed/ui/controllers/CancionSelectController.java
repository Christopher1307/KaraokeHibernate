package aed.ui.controllers;

import aed.db.Cancion;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CancionSelectController  implements Initializable {

    private ObjectProperty<Cancion> cancion = new SimpleObjectProperty<>();

    @FXML
    private TextField autorTextField;

    @FXML
    private TextField nombreTextField;

    @FXML
    private Button playButton;

    @FXML
    private GridPane root;

    @FXML
    private Label tiempoLabel;

    @FXML
    void onPlayAction(ActionEvent event) {

    }

    public CancionSelectController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CancionSelectView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

    public GridPane getRoot() {
        return root;
    }

    public Cancion getCancion() {
        return cancion.get();
    }

    public ObjectProperty<Cancion> cancionProperty() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion.set(cancion);
    }
}
