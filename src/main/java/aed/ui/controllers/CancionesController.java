package aed.ui.controllers;

import aed.db.Cancion;
import aed.db.GenericDAO;
import aed.db.KaraokeLog;
import aed.db.Usuario;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class CancionesController  implements Initializable {

    // Model
    private ListProperty<Cancion> canciones = new SimpleListProperty<>(FXCollections.observableArrayList());

    private ObjectProperty<Cancion> cancionSeleccionada = new SimpleObjectProperty<>();
    private CancionSelectController cancionSelectController = new CancionSelectController();

    private GenericDAO<Cancion> cancionDAO = new GenericDAO<>(Cancion.class);
    private GenericDAO<KaraokeLog> karaokeLogDAO = new GenericDAO<>(KaraokeLog.class);
    private Usuario usuarioSesion = new Usuario();

    // View

    @FXML
    private BorderPane cancionesRoot;

    @FXML
    private ListView<Cancion> cancionesListView;

    @FXML
    private VBox emptyBox;

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
        cancionSelectController.setKaraokeLogDAO(karaokeLogDAO);
        cancionSelectController.setUsuario(usuarioSesion);

        // Obtener todas las canciones y ordenarlas por 'vecesCantada' de mayor a menor
        List<Cancion> cancionesOrdenadas = cancionDAO.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Cancion::getVecesCantada).reversed())
                .toList();

        canciones.setAll(cancionesOrdenadas);

        cancionesListView.itemsProperty().bind(canciones);
        cancionesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            cancionSeleccionada.set(newVal);
        });

        // Binds
        cancionSelectController.cancionProperty().bindBidirectional(cancionSeleccionada);

        // Listeners
        cancionSeleccionada.addListener(this::onSelectedCancionChanged);
    }

    private void onSelectedCancionChanged(ObservableValue<? extends Cancion> o, Cancion ov, Cancion nv) {
        if (nv != null) {
            cancionesRoot.setCenter(cancionSelectController.getRoot());
        } else {
            cancionesRoot.setCenter(emptyBox);
        }
    }

    public BorderPane getCancionesRoot() {
        return cancionesRoot;
    }

    public void setCancionesRoot(BorderPane cancionesRoot) {
        this.cancionesRoot = cancionesRoot;
    }

    public ObservableList<Cancion> getCanciones() {
        return canciones.get();
    }

    public ListProperty<Cancion> cancionesProperty() {
        return canciones;
    }

    public void setCanciones(ObservableList<Cancion> canciones) {
        this.canciones.set(canciones);
    }

    public Cancion getCancionSeleccionada() {
        return cancionSeleccionada.get();
    }

    public ObjectProperty<Cancion> cancionSeleccionadaProperty() {
        return cancionSeleccionada;
    }

    public void setCancionSeleccionada(Cancion cancionSeleccionada) {
        this.cancionSeleccionada.set(cancionSeleccionada);
    }

    public GenericDAO<Cancion> getCancionDAO() {
        return cancionDAO;
    }

    public void setCancionDAO(GenericDAO<Cancion> cancionDAO) {
        this.cancionDAO = cancionDAO;
    }

    public GenericDAO<KaraokeLog> getKaraokeLogDAO() {
        return karaokeLogDAO;
    }

    public void setKaraokeLogDAO(GenericDAO<KaraokeLog> karaokeLogDAO) {
        this.karaokeLogDAO = karaokeLogDAO;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
}
