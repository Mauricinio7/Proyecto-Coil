package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import coilvic.modelo.pojo.ProfesorExterno;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FXMLRegistrarProfesorExternoController implements Initializable {

    private ProfesorExterno profesorExterno;
    private String nombreColaboracion;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private TextField tfBusquedaProfesor;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<?> tvProfesoresExternos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colIdioma;
    @FXML
    private TableColumn colInstitucion;
    @FXML
    private TableColumn colPais;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colColaboracion;

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
    private void btnAnadir(ActionEvent event) {
    }
    
}
