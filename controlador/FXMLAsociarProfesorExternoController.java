package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import coilvic.modelo.pojo.ProfesorUv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;


public class FXMLAsociarProfesorExternoController implements Initializable {

    @FXML
    private ScrollPane scPaneLista;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txtNombre;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(ProfesorUv profesor){
        //TODO pasar colaboracion
    }


    @FXML
    private void btnClicBuscar(ActionEvent event) {
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }

    @FXML
    private void clicbtnAgregar(ActionEvent event) {
    }
    
}
