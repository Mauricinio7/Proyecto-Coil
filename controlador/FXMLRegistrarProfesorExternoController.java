package coilvic.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.pojo.ProfesorExternoColaboracion;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FXMLRegistrarProfesorExternoController implements Initializable {

    private ObservableList<ProfesorExternoColaboracion> profesoresExternosColaboracion;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private TextField tfBusquedaProfesor;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<ProfesorExternoColaboracion> tvProfesoresExternos;
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
        cargarDatosProfesoresExternos();
    }

    private void cargarDatosProfesoresExternos() {
        profesoresExternosColaboracion = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorExternoDAO.obtenerProfesoresExternosConColaboracion();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<ProfesorExternoColaboracion> profesoresExternos = 
            (ArrayList<ProfesorExternoColaboracion>) respuesta.get("profesoresExternosColaboracion");
            profesoresExternosColaboracion.addAll(profesoresExternos);
            tvProfesoresExternos.setItems(profesoresExternosColaboracion);
        } else {
            Utils.mostrarAlertaSimple("Error", ""+respuesta.get(Constantes.KEY_MENSAJE), AlertType.ERROR);
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

    @FXML
    private void btnAnadir(ActionEvent event) {
    }
    
}
