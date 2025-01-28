package aed.ui.controllers;

import aed.db.GenericDAO;
import aed.db.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // Model
    private final StringProperty nombre = new SimpleStringProperty();
    private RootController rootController;
    GenericDAO<Usuario> usuarioDAO = new GenericDAO<>(Usuario.class);

    // View

    @FXML
    private Button entrarButton;

    @FXML
    private BorderPane loginRoot;

    @FXML
    private TextField nombreTextField;

    public LoginController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombreTextField.textProperty().bindBidirectional(nombre);
    }

    @FXML
    void onEntrarAction(ActionEvent event) {

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombre.get());

        if (usuarioDAO.findByNombreUsuario(usuario.getNombreUsuario()) == null) {
            NewUserAlert(usuario);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bienvenido");
            alert.setHeaderText("Bienvenido al Karaoke " + usuario.getNombreUsuario());
            alert.showAndWait();
        }
        rootController = new RootController(usuario);
        Stage stage = (Stage) loginRoot.getScene().getWindow();
        stage.close();


        Scene scene = new Scene(rootController.getRoot());
        Stage mainStage = new Stage();

        mainStage.setScene(scene);

        mainStage.show();
    }

    public void NewUserAlert(Usuario newUser) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo usuario");
        alert.setHeaderText("Usuario no encontrado");
        alert.setContentText("¿Desea crear un nuevo usuario?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            usuarioDAO.create(newUser);
        }
    }


    public BorderPane getLoginRoot() {
        return loginRoot;
    }

    public void setLoginRoot(BorderPane loginRoot) {
        this.loginRoot = loginRoot;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public RootController getRootController() {
        return rootController;
    }
}


