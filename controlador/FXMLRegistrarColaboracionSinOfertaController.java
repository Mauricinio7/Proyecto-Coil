package coilvic.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Departamento;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    //private ProfesorUv profesorUv; //DATOS DEL PROFESOR UV
    private ObservableList<Asignatura> listaAsignaturas;
    private ObservableList<Departamento> listaDepartamentos;
    private ObservableList<Asignatura> listaAreasAcademicas;
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
    private ComboBox<Asignatura> cbAreaAcademica;
    @FXML
    private ComboBox<Asignatura> cbAsignatura;
    @FXML
    private ComboBox<Departamento> cbDepartamento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarAreasAcademicas();
        configurarSeleccionAsignatura();
    }    
    
    /*
    public void inicializarValores(ProfesorUv profesor){ //PIDE EL PROFESOR UV EN LOS PARAMETROS
        this.profesorUv = profesor;
        lbNombreProfesor.setText("Nombre profesor: " + profesor.getNombre());
        lbCorreoProfesor.setText("Correo: " + profesor.getCorreo());
        lbRegionProfesor.setText("Region: " + profesor.getRegion());
    }
    */

    private void cargarAreasAcademicas(){
        listaAreasAcademicas = FXCollections.observableArrayList();
        listaAreasAcademicas.addAll
        ((ArrayList<Asignatura>) AsignaturaDAO.consultarListaAsignatura().get("listaAsignatura"));
        cbAreaAcademica.setItems(listaAreasAcademicas);
    }

    private void configurarSeleccionAsignatura() {
        cbAreaAcademica.valueProperty().addListener(new ChangeListener<Asignatura>() {
            @Override
            public void changed(ObservableValue<? extends Asignatura> observable, Asignatura oldValue, Asignatura newValue) {
                cargarAsignaturas(newValue);
            }
        });
    }

    private void cargarAsignaturas(Asignatura asignatura) {
        listaAsignaturas = FXCollections.observableArrayList();
        listaAsignaturas.add(asignatura);
        cbAsignatura.setItems(listaAsignaturas);
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
        //LLAMA A LA VENTANA DE REGISTRAR ESTUDIANTES
    }

    @FXML
    private void btnAsociarProfesorExterno(ActionEvent event) {
        //LLAMA A LA VENTANA DE ASOCIAR PROFESOR EXTERNO
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        //REGRESA A LA VENTANA ANTERIOR
    }
}
