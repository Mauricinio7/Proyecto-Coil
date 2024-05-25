package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FXMLConsultarReporteColaboracionesController implements Initializable {

    @FXML
    private Pane panelDeslisante;
    @FXML
    private ComboBox<String> cbPeriodo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSalir(ActionEvent event) {
        //REGRESA A LA VENTANA ANTERIOR
    }


    @FXML
    private void btnDescargar(ActionEvent event) {
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

    

    
}
