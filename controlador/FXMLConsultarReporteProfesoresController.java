package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FXMLConsultarReporteProfesoresController implements Initializable {

    @FXML
    private ComboBox<String> cbPeriodo;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private Pane panelDescarga;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSalir(ActionEvent event) {
    }

    @FXML
    private void btnDescargar(ActionEvent event) {
    }

    @FXML
    private void salePanel(MouseEvent event) {
    }

    @FXML
    private void entraPanel(MouseEvent event) {
    }
    
}
