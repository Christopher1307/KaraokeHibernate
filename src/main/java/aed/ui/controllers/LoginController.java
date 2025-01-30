package aed.ui.controllers;

import aed.db.GenericDAO;
import aed.db.Usuario;
import aed.ui.controllers.RootController;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // Model
    private final StringProperty nombre = new SimpleStringProperty();
    private RootController rootController;
    private final GenericDAO<Usuario> usuarioDAO = new GenericDAO<>(Usuario.class);

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
        String nombreUsuario = nombre.get();
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            showErrorAlert("Por favor, ingrese un nombre de usuario.");
            return;
        }

        Usuario usuario = usuarioDAO.findByNombreUsuario(nombreUsuario);

        if (usuario == null) {
            if (!NewUserAlert(new Usuario(nombreUsuario))) {
                return; // No hacer nada si el usuario cancela la creación
            }
        } else {
            showInfoAlert("Bienvenido", "Bienvenido al Karaoke " + usuario.getNombreUsuario());
        }

        rootController = new RootController(usuario);
        Stage stage = (Stage) loginRoot.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(rootController.getRoot());
        Stage mainStage = new Stage();
        mainStage.setScene(scene);
        mainStage.show();
    }

    private boolean NewUserAlert(Usuario newUser) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo usuario");
        alert.setHeaderText("Usuario no encontrado");
        alert.setContentText("¿Desea crear un nuevo usuario?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            usuarioDAO.create(newUser);
            return true;
        }
        return false;
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
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
