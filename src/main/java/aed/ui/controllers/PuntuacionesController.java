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
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class PuntuacionesController {

    @FXML
    private BorderPane root;

    @FXML
    private TableView<KaraokeLog> karaokeTableView;

    @FXML
    private TableColumn<KaraokeLog, String> usuarioColumn;

    @FXML
    private TableColumn<KaraokeLog, String> cancionColumn;

    @FXML
    private TableColumn<KaraokeLog, String> fechaColumn;

    private EntityManagerFactory emf;

    public PuntuacionesController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionesView.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        // Configurar la conexión con la base de datos
        emf = Persistence.createEntityManagerFactory("KaraokePU");

        // Configurar las columnas del TableView
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        cancionColumn.setCellValueFactory(new PropertyValueFactory<>("cancion"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

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

    public BorderPane getPuntuacionesRoot() {
        return root;
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}