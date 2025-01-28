// PuntuacionesController.java
package aed.ui.controllers;

import aed.db.KaraokeLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class PuntuacionesController {

    @FXML
    private SplitPane root;

    @FXML
    private TableView<KaraokeLog> karaokeTableView;

    @FXML
    private TableColumn<KaraokeLog, String> usuarioTableView;

    @FXML
    private TableColumn<KaraokeLog, String> tiempoTableView;

    private EntityManagerFactory emf;

    public PuntuacionesController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionesView.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Set preferred size
        root.setPrefSize(1000, 400);
    }

    @FXML
    public void initialize() {
        // Configurar la conexión con la base de datos
        emf = Persistence.createEntityManagerFactory("KaraokePU");

        // Configurar las columnas del TableView
        usuarioTableView.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        tiempoTableView.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        // Cargar los datos en el TableView
        loadKaraokeLogs();
    }

    private void loadKaraokeLogs() {
        EntityManager em = emf.createEntityManager();
        try {
            // Consulta para obtener todos los registros de KaraokeLog
            TypedQuery<KaraokeLog> query = em.createQuery("SELECT k FROM KaraokeLog k", KaraokeLog.class);
            ObservableList<KaraokeLog> logs = FXCollections.observableArrayList(query.getResultList());

            // Asignar los datos al TableView
            karaokeTableView.setItems(logs);
        } finally {
            em.close();
        }
    }

    public SplitPane getPuntuacionesRoot() {
        return root;
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}