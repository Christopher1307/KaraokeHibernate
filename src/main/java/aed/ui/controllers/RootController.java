package aed.ui.controllers;

import aed.db.Usuario;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    // Model
    private ObjectProperty<Usuario> usuario = new SimpleObjectProperty<>();



    // View

    @FXML
    private Button cerrarButton;

    @FXML
    private Button iniciarKaraokeButton;

    @FXML
    private Button puntuacionesButton;

    @FXML
    private BorderPane root;

    @FXML
    void OnInicioAction(ActionEvent event) {

    }

    @FXML
    void OnPuntuacionesAction(ActionEvent event) {

    }

    @FXML
    void OnCerraAction(ActionEvent event) {

    }

    public RootController(Usuario usuarioSesion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        usuario.set(usuarioSesion);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
}
