/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLRegistrarOfertaExternaController implements Initializable {

    @FXML
    private Pane panelDeslisante;
    @FXML
    private Label lbName;
    @FXML
    private Label lbPeriodo;
    @FXML
    private Label lbObjetivoGeneral;
    @FXML
    private ComboBox<?> cbPeriodo;
    @FXML
    private TextArea taObjetivo;
    @FXML
    private TextArea taTemaInteres;
    @FXML
    private TextField tfIdioma;
    @FXML
    private TextField tfNameCol;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

       @FXML
    private void salePanel(MouseEvent event) {
        TranslateTransition transicion = new TranslateTransition();
        transicion.setDuration(Duration.millis(500));
        transicion.setNode(panelDeslisante);
        transicion.setToX(0);
        transicion.play();
    }

    @FXML
    private void entraPanel(MouseEvent event) {
        TranslateTransition transicion = new TranslateTransition();
        transicion.setDuration(Duration.millis(500));
        transicion.setNode(panelDeslisante);
        transicion.setToX(230);
        transicion.play();
    }

    @FXML
    private void btSaveAnimationExited(MouseEvent event) {
    }

    @FXML
    private void btSaveAnimationEntered(MouseEvent event) {
    }

    @FXML
    private void clicSave(MouseEvent event) {
    }

    @FXML
    private void btCancelAnimationExited(MouseEvent event) {
    }

    @FXML
    private void btCancelAnimationEntered(MouseEvent event) {
    }

    @FXML
    private void clicCancel(MouseEvent event) {
    }
    
}
