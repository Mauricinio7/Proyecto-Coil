package coilvic.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.PlanProyecto;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Utils;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class FXMLRegistrarColaboracionSinOfertaController implements Initializable {

    private ProfesorUv profesorUv;
    private File archivoPlan;
    private ObservableList<Asignatura> listaAsignaturas;
    private ObservableList<Departamento> listaDepartamentos;
    private ObservableList<String> listaAreasAcademicas;
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
    private ComboBox<String> cbAreaAcademica;
    @FXML
    private ComboBox<Asignatura> cbAsignatura;
    @FXML
    private ComboBox<Departamento> cbDepartamento;
    @FXML
    private TextArea taDescripcionPlan;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Eliminar inicia
        profesorUv = new ProfesorUv();
        profesorUv.setNombre("Carlos Fuentes");
        profesorUv.setCorreo("cfuentes@uv.mx");
        profesorUv.setIdRegion(1);
        inicializarValores(profesorUv);
        //Eliminar termina

        limitarCaracteres();
        cargarAreasAcademicas();
        cbDepartamento.setDisable(true);
        cbAsignatura.setDisable(true);
        configurarSeleccionDepartamento();
        configurarSeleccionAsignatura();
        configurarFechaFin();
        dpFechaFin.setDisable(true);
    }    
    
    public void inicializarValores(ProfesorUv profesor){
        this.profesorUv = profesor;
        lbNombreProfesor.setText("Nombre profesor: " + profesorUv.getNombre());
        lbCorreoProfesor.setText("Correo: " + profesorUv.getCorreo());
        lbRegionProfesor.setText("Region: " + profesorUv.getIdRegion()); //CAMBIAR POR EL NOMBRE DE LA REGION
    }
    
    private void cargarAreasAcademicas() {
        listaAreasAcademicas = FXCollections.observableArrayList();
        HashMap<String, Object> obtenerAreaAcademica = 
        AsignaturaDAO.consultarAreaAcademicaPorRegion(profesorUv.getIdRegion());
        listaAreasAcademicas.addAll((ArrayList<String>) obtenerAreaAcademica.get("listaArea"));
        cbAreaAcademica.setItems(listaAreasAcademicas);
    }

    private void configurarSeleccionAsignatura() {
        cbDepartamento.valueProperty().addListener(new ChangeListener<Departamento>() {
            @Override
            public void changed
            (ObservableValue<? extends Departamento> observable, Departamento oldValue, Departamento newValue) {
                if (newValue != null) {
                    cbAsignatura.setDisable(false);
                    cargarAsignaturas(newValue);
                } else {
                    cbAsignatura.setDisable(true);
                }
            }
        });
    }

    private void cargarAsignaturas(Departamento asignatura) {
        listaAsignaturas = FXCollections.observableArrayList();
        HashMap<String, Object> obtenerAsignatura = 
        AsignaturaDAO.consultaAsignaturaDepartamento(asignatura.getIdDepartamento());
        listaAsignaturas.addAll((ArrayList<Asignatura>) obtenerAsignatura.get("listaAsignatura"));
        cbAsignatura.setItems(listaAsignaturas);
    }

    private void configurarSeleccionDepartamento() {
        cbAreaAcademica.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    cbDepartamento.setDisable(false);
                    cargarDepartamentos(newValue);
                } else {
                    cbDepartamento.setDisable(true);
                }
            }
        });
    }

    private void cargarDepartamentos(String areaAcademica) {
        listaDepartamentos = FXCollections.observableArrayList();
        HashMap<String, Object> obtenerDepartamento = DepartamentoDAO.consultarDepartamentoPorAreaAcad(areaAcademica);
        listaDepartamentos.addAll((ArrayList<Departamento>) obtenerDepartamento.get("listaDepartamento"));
        cbDepartamento.setItems(listaDepartamentos);
    }

    private void configurarFechaFin() {
        dpFechaInicio.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null) {
                    dpFechaFin.setDisable(false);
                    dpFechaFin.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item.isBefore(newValue.plusDays(1))) {
                                        setDisable(true);
                                    }
                                }
                            };
                        }
                    });
                } else {
                    dpFechaFin.setDisable(true);
                }
            }
        });
    }

    private boolean camposVacios(){
        return tfNombreColaboracion.getText().isEmpty() || dpFechaInicio.getValue() == null || dpFechaFin.getValue() == null
                || tfIdioma.getText().isEmpty() || taObjetivo.getText().isEmpty() || tfPeriodo.getText().isEmpty()
                || taTemaInteres.getText().isEmpty() || tfNoEstudiantes.getText().isEmpty() || cbAreaAcademica.getValue() == null
                || cbAsignatura.getValue() == null || cbDepartamento.getValue() == null || archivoPlan == null;
    }

    private boolean validarFechas(){
        return dpFechaInicio.getValue().isBefore(dpFechaFin.getValue());
    }

    private boolean validarNoEstudiantes(){
        return tfNoEstudiantes.getText().matches("[0-9]+") &&
         Integer.parseInt(tfNoEstudiantes.getText()) > 0 && 
         Integer.parseInt(tfNoEstudiantes.getText()) < 1000;
    }

    private boolean validarLongitudNombrePlanProyecto() {
        return archivoPlan.getName().length() <= 45;
    }

    private Colaboracion obtenerDatosColaboracion() {
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre(tfNombreColaboracion.getText());
        colaboracion.setFechaInicio(dpFechaInicio.getValue().toString());
        colaboracion.setFechaFin(dpFechaFin.getValue().toString());
        colaboracion.setIdioma(tfIdioma.getText());
        colaboracion.setObjetivoGeneral(taObjetivo.getText());
        colaboracion.setPeriodo(tfPeriodo.getText());
        colaboracion.setTemaInteres(taTemaInteres.getText());
        colaboracion.setNoEstudiantesExternos(Integer.parseInt(tfNoEstudiantes.getText()));
        colaboracion.setIdAsignatura(cbAsignatura.getValue().getIdAsignatura());
        colaboracion.setIdDepartamento(cbDepartamento.getValue().getIdDepartamento());
        colaboracion.setIdProfesorUV(profesorUv.getIdProfesorUv());
        return colaboracion;
    }

    private PlanProyecto obtenerDatosPlanProyecto() {
        PlanProyecto planProyecto = new PlanProyecto();
        planProyecto.setNombre(archivoPlan.getName());
        planProyecto.setDescripcion(taDescripcionPlan.getText());
        try {
            byte[] archivo = Files.readAllBytes(archivoPlan.toPath());
            planProyecto.setArchivoAdjunto(archivo);
        } catch (IOException ex) {
            //TODO
        }
        return planProyecto;
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
        if(!camposVacios()) {
            if(validarFechas() && validarNoEstudiantes() && validarLongitudNombrePlanProyecto()) {
                Colaboracion colaboracion = obtenerDatosColaboracion();
                PlanProyecto planProyecto = obtenerDatosPlanProyecto();
                //GUARDAR COLABORACION Y PLAN PROYECTO
            } else {
                Utils.mostrarAlertaSimple
                ("", "Se han introducido datos inválidos", AlertType.ERROR);
            }
        } else {
            Utils.mostrarAlertaSimple
            ("Rellenar campos obligatorios", "Se han dejado campos obligatorios vacíos", AlertType.ERROR);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        //REGRESA A LA VENTANA ANTERIOR
    }

    @FXML
    private void btnPlanProyecto(ActionEvent event) {
        FileChooser dialogoSeleccion = new FileChooser();
        dialogoSeleccion.setTitle("Seleccionar plan");
        String etiquetaTipoDato = "Archivos pdf (*.pdf)";
        FileChooser.ExtensionFilter filtro = 
                new FileChooser.ExtensionFilter(etiquetaTipoDato, "*.pdf");
        dialogoSeleccion.getExtensionFilters().add(filtro);
        Stage escenarioActual = (Stage) panelDeslisante.getScene().getWindow();
        archivoPlan = dialogoSeleccion.showOpenDialog(escenarioActual);
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

    private void limitarCaracteres() {
        tfNombreColaboracion.textProperty().addListener
        ((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() > 100) {
                tfNombreColaboracion.setText(oldValue);
            }
        });
        tfIdioma.textProperty().addListener
        ((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() > 20) {
                tfIdioma.setText(oldValue);
            }
        });
        taObjetivo.textProperty().addListener
        ((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() > 350) {
                taObjetivo.setText(oldValue);
            }
        });
        taTemaInteres.textProperty().addListener
        ((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() > 50) {
                taTemaInteres.setText(oldValue);
            }
        });
        taDescripcionPlan.textProperty().addListener
        ((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() > 100) {
                taDescripcionPlan.setText(oldValue);
            }
        });
    }

}