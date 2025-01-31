package aed.ui.controllers;

import aed.db.Cancion;
import aed.db.GenericDAO;
import aed.db.KaraokeLog;
import aed.db.Usuario;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
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
    private StringProperty autor = new SimpleStringProperty();
    private StringProperty nombre = new SimpleStringProperty();
    private StringProperty tiempo = new SimpleStringProperty();
    private StringProperty vecesRepro = new SimpleStringProperty();

    private Usuario usuario = new Usuario();
    private GenericDAO<KaraokeLog> karaokeLogDAO = new GenericDAO<>(KaraokeLog.class);
    private GenericDAO<Cancion> cancionDAO = new GenericDAO<>(Cancion.class);

    @FXML
    private Label vecesReproLabel;

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
        Cancion cancion = getCancion();
        if (cancion != null) {
            cancion.setVecesCantada(cancion.getVecesCantada() + 1);
            karaokeLogDAO.save(cancion, usuario);
            cancionDAO.addRepro(cancion);
        }
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
        cancion.addListener(this::onCancionChanged);
    }

    private void onCancionChanged(ObservableValue<? extends Cancion> o, Cancion oldValue, Cancion newValue) {
        if (oldValue != null) {
            autorTextField.textProperty().unbindBidirectional(autorProperty());
            nombreTextField.textProperty().unbindBidirectional(nombreProperty());
            tiempoLabel.textProperty().unbindBidirectional(tiempoProperty());
            vecesReproLabel.textProperty().unbindBidirectional(vecesReproProperty());
        }
        if (newValue != null) {
            autor.set(newValue.getNombreAutor());
            nombre.set(newValue.getNombreCancion());
            // Convertir el int de segundos en un string con formato mm:ss
            int minutos = newValue.getTiempo() / 60;
            tiempo.set(minutos + ":" + newValue.getTiempo() % 60);
            vecesRepro.set(newValue.getVecesCantada() + "");
            autorTextField.textProperty().bindBidirectional(autorProperty());
            nombreTextField.textProperty().bindBidirectional(nombreProperty());
            tiempoLabel.textProperty().bindBidirectional(tiempoProperty());
            vecesReproLabel.textProperty().bindBidirectional(vecesReproProperty());
        }
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

    public String getAutor() {
        return autor.get();
    }

    public StringProperty autorProperty() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor.set(autor);
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

    public String getTiempo() {
        return tiempo.get();
    }

    public StringProperty tiempoProperty() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo.set(tiempo);
    }

    public String getVecesRepro() {
        return vecesRepro.get();
    }

    public StringProperty vecesReproProperty() {
        return vecesRepro;
    }

    public void setVecesRepro(String vecesRepro) {
        this.vecesRepro.set(vecesRepro);
    }

    public void setKaraokeLogDAO(GenericDAO<KaraokeLog> karaokeLogDAO) {
        this.karaokeLogDAO = karaokeLogDAO;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
