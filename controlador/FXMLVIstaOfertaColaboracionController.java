/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.Region;
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
    private ComboBox<String> cbPeriodo;
    @FXML
    private ComboBox<Region> cdRegion;
    @FXML
    private ComboBox<Departamento> cbDepartamento;
    @FXML
    private TextArea taObjetivo;
    @FXML
    private TextArea taTemaInteres;
    @FXML
    private TextField tfIdioma;
    @FXML
    private TextField tfNameCol;
    @FXML
    private ComboBox<String> cdAreaAcad;
    @FXML
    private ComboBox<Asignatura> cdAsignatura;

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
    public void inicializarValores(){
        
    }
    public static boolean validarNombreColaboracion(String nombre){
        String regex = "[a-zA-Z0-9íáéóúñÁÉÍÓÚÑÜ ]+";
        Pattern patronCoincidencias =Pattern.compile(regex);
        Matcher coincidencias = patronCoincidencias.matcher(nombre);
        return coincidencias.matches();
    }
}
