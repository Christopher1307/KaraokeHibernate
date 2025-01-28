package aed.ui.controllers;

import aed.db.KaraokeLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
