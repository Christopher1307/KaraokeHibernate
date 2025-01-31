// RootController.java
package aed.ui.controllers;

import aed.db.Cancion;
import aed.db.GenericDAO;
import aed.db.KaraokeLog;
import aed.db.Usuario;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    // Model
    private ObjectProperty<Usuario> usuario = new SimpleObjectProperty<>();
    private CancionesController cancionesController = new CancionesController();
    private PuntuacionesController puntuacionesController = new PuntuacionesController();

    private GenericDAO<Usuario> usuarioDAO = new GenericDAO<>(Usuario.class);
    private GenericDAO<Cancion> cancionDAO = new GenericDAO<>(Cancion.class);
    private GenericDAO<KaraokeLog> karaokeLogDAO = new GenericDAO<>(KaraokeLog.class);

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
    private Button volverButton;

    @FXML
    private Label tituloLabel;

    @FXML
    private VBox initialView;

    @FXML
    void onVolverAction(ActionEvent event) {
        tituloLabel.setVisible(true);
        this.getRoot().setCenter(initialView);
        volverButton.setVisible(false);
    }

    @FXML
    void OnInicioAction(ActionEvent event) {
        tituloLabel.setVisible(false);
        this.getRoot().setCenter(cancionesController.getCancionesRoot());
        volverButton.setVisible(true);
    }

    @FXML
    void OnPuntuacionesAction(ActionEvent event) {
        tituloLabel.setVisible(false);
        this.getRoot().setCenter(puntuacionesController.getPuntuacionesRoot());
        volverButton.setVisible(true);
    }

    @FXML
    void OnCerraAction(ActionEvent event) {
        System.exit(0);
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
        // View to return to when clicking on the "Volver" button
        initialView = new VBox(iniciarKaraokeButton, puntuacionesButton, cerrarButton);
        initialView.setSpacing(15);
        initialView.setAlignment(javafx.geometry.Pos.CENTER);
        this.getRoot().setCenter(initialView);

        // Set the CancionesController's  variables
        cancionesController.setUsuarioSesion(usuario.get());
        cancionesController.setCancionDAO(cancionDAO);
        cancionesController.setKaraokeLogDAO(karaokeLogDAO);

    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
}