package coilvic.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.awt.Desktop;
import coilvic.modelo.dao.PeriodoDAO;
import coilvic.modelo.pojo.ReporteColaboraciones;
import coilvic.utilidades.Utils;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.itextpdf.text.DocumentException;

public class FXMLConsultarReporteColaboracionesController implements Initializable {

    private File carpeta;
    private ObservableList<String> periodos;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private ComboBox<String> cbPeriodo;
    @FXML
    private Pane panelDescarga;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarPeriodos();
    }    

    @FXML
    private void btnSalir(ActionEvent event) {
        //REGRESA A LA VENTANA ANTERIOR
    }


    @FXML
    private void btnDescargar(ActionEvent event) {
        seleccionarDirectorio();
        if (carpeta != null) {
            mostrarVentanaDescarga();
            generarReporte(carpeta.getAbsolutePath());
        }
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

    private void mostrarVentanaDescarga() {
        TranslateTransition transicion = new TranslateTransition();
        transicion.setDuration(Duration.millis(700));
        transicion.setNode(panelDescarga);
        transicion.setToY(-137);
        transicion.play();
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            TranslateTransition reverseTransition = new TranslateTransition();
            reverseTransition.setDuration(Duration.millis(700));
            reverseTransition.setNode(panelDescarga);
            reverseTransition.setToY(0);
            reverseTransition.play();
        });
        pause.play();
    }

    private void generarReporte(String ruta) {
        ReporteColaboraciones reporte = new ReporteColaboraciones(ruta);
        try {
            reporte.crearDocumento();
            reporte.abrirDocumento();
            reporte.agregarTitulo("Reporte de Colaboraciones");
            reporte.agregarSaltoLinea();
            reporte.agregarSaltoLinea();
            reporte.agregarParrafo("Periodo: " + cbPeriodo.getValue());
            reporte.agregarSaltoLinea();
            reporte.agregarSaltoLinea();
            reporte.agregarTablaColaboraciones(cbPeriodo.getValue());
            abrirArchivo(ruta + "/ReporteColaboraciones.pdf");
        } catch (DocumentException | IOException ex) {
            Utils.mostrarAlertaSimple("", "No se han podido cargar los datos", AlertType.ERROR);
        }
    }
    
    private void seleccionarDirectorio() {
        DirectoryChooser dialogoSeleccion = new DirectoryChooser();
        dialogoSeleccion.setTitle("Seleccionar carpeta de destino");
        Stage escenarioActual = (Stage) panelDeslisante.getScene().getWindow();
        carpeta = dialogoSeleccion.showDialog(escenarioActual);
    }

    private void abrirArchivo(String ruta) throws IOException {
        File archivo = new File(ruta);
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (archivo.exists()) {
                desktop.open(archivo);
            }
        }
    }    

}
