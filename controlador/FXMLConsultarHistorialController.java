/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import coilvic.modelo.pojo.OfertaColaboracion;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLConsultarHistorialController implements Initializable {

    private ObservableList<String> listaEstado;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private TextField tfName;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<OfertaColaboracion> tvColaboraciones;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private TableColumn clName;
    @FXML
    private TableColumn clProfesor;
    @FXML
    private TableColumn clPeriodo;
    @FXML
    private TableColumn clAsignatura;
    @FXML
    private TableColumn clDepartamento;
    @FXML
    private TableColumn clRegion;
    @FXML
    private TableColumn clInformacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillEstado();
        Platform.runLater(() -> {
            btnBuscar.requestFocus();
        });
    }    
      @FXML
    private void salePanel(MouseEvent event) {
        TranslateTransition transicion = new TranslateTransition();
        transicion.setDuration(Duration.millis(500));
        transicion.setNode(panelDeslisante);
        transicion.setToX(0);
        tvColaboraciones.setVisible(true);
        tfName.setVisible(true);
        transicion.play();
    }

    @FXML
    private void entraPanel(MouseEvent event) {
        TranslateTransition transicion = new TranslateTransition();
        transicion.setDuration(Duration.millis(500));
        transicion.setNode(panelDeslisante);
        transicion.setToX(230);
        tvColaboraciones.setVisible(false);
        tfName.setVisible(false);
        
        transicion.play();
    }
    public void fillEstado(){
        listaEstado = FXCollections.observableArrayList();
        listaEstado.add("Colaboraciones Activas");
        listaEstado.add("Colaboraciones Pendientes");
        cbEstado.setItems(listaEstado);
        cbEstado.setValue(listaEstado.get(0));
    }
}
