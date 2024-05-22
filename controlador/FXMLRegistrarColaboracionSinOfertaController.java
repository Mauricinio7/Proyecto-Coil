package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FXMLRegistrarColaboracionSinOfertaController implements Initializable {

    @FXML
    private Pane panelDeslisante;
    @FXML
    private Label lbNombreProfesor;
    @FXML
    private Label lbCorreoProfesor;
    @FXML
    private Label lbRegionProfesor;
    @FXML
    private TextField tfNombreColaboracion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextField tfIdioma;
    @FXML
    private TextArea taObjetivo;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextArea taTemaInteres;
    @FXML
    private TextField tfNoEstudiantes;
    @FXML
    private ComboBox<?> cbAreaAcademica;
    @FXML
    private ComboBox<?> cbAsignatura;
    @FXML
    private ComboBox<?> cbDepartamento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarValores(){
        
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
        transicion.setToX(210);
        
        transicion.play();
    }

    @FXML
    private void btnRegistrarEstudiantes(ActionEvent event) {
    }

    @FXML
    private void btnAsociarProfesorExterno(ActionEvent event) {
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
    }
}
