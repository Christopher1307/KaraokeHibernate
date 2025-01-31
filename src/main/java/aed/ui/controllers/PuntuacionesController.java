package aed.ui.controllers;

import aed.db.KaraokeLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private Button recargarButton;

    @FXML
    private TextField yearTextField;

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
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("usuarioNombre"));
        cancionColumn.setCellValueFactory(new PropertyValueFactory<>("cancion"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaRepro"));

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

    @FXML
    void onRecargarAction(ActionEvent event) {
        loadKaraokeLogs();
    }


    @FXML
    private void onFilterByYear() {
        String year = yearTextField.getText();
        if (year == null || year.trim().isEmpty()) {
            loadKaraokeLogs();
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            // Consulta para obtener los registros de KaraokeLog filtrados por año
            TypedQuery<KaraokeLog> query = em.createQuery("SELECT k FROM KaraokeLog k WHERE k.fechaRepro LIKE :year", KaraokeLog.class);
            query.setParameter("year", year + "%");
            ObservableList<KaraokeLog> logs = FXCollections.observableArrayList(query.getResultList());

            // Asignar los datos filtrados al TableView
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