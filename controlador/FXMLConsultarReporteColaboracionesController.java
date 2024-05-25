package coilvic.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import coilvic.modelo.dao.PeriodoDAO;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FXMLConsultarReporteColaboracionesController implements Initializable {

    private ObservableList<String> periodos;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private ComboBox<String> cbPeriodo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarPeriodos();
    }    

    @FXML
    private void btnSalir(ActionEvent event) {
        //REGRESA A LA VENTANA ANTERIOR
    }


    @FXML
    private void btnDescargar(ActionEvent event) {
    }

    private void cargarPeriodos() {
        periodos = FXCollections.observableArrayList();
        periodos.addAll((ArrayList<String>) PeriodoDAO.obtenerPeriodosConcluidos().get("Periodos"));
        cbPeriodo.setItems(periodos);
        if (!periodos.isEmpty()) {
            cbPeriodo.getSelectionModel().selectFirst();
        }
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

    private void generarReporte() {
        //GENERAR REPORTE
    }
    
}
