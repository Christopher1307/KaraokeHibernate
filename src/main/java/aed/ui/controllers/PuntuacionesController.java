package aed.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PuntuacionesController {

    @FXML
    private TableColumn<?, ?> cancionTableView;

    @FXML
    private TableView<?> karaokeTableView;

    @FXML
    private TableColumn<?, ?> repeticionTableView;

    @FXML
    private SplitPane root;

    @FXML
    private TableColumn<?, ?> tiempoTableView;

    @FXML
    private TableColumn<?, ?> usuarioTableView;

}
