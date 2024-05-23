/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLVIstaOfertaColaboracionController implements Initializable {

    @FXML
    private Pane panelDeslisante;
    @FXML
    private ComboBox<?> cbPeriodo;
    @FXML
    private ComboBox<?> cdRegion;
    @FXML
    private ComboBox<?> cbDepartamento;
    @FXML
    private TextArea taObjetivo;
    @FXML
    private TextArea taTemaInteres;
    @FXML
    private TextField tfIdioma;
    @FXML
    private TextField tfNameCol;
    @FXML
    private ComboBox<?> cdAreaAcad;
    @FXML
    private ComboBox<?> cdAsignatura;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void salePanel(MouseEvent event) {
    }

    @FXML
    private void entraPanel(MouseEvent event) {
    }
    
}
