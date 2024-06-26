package coilvic.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.CoilVic;
import coilvic.modelo.ConexionApacheNet;
import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.modelo.pojo.PlanProyecto;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

public class FXMLRegistrarColaboracionSinOfertaController implements Initializable {

    private OfertaColaboracion ofertaColaboracion;
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
    @FXML
    private Button btnPlanProyecto;
    @FXML
    private ComboBox<String> cbPeriodo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarValores(ProfesorUv profesor){
        this.profesorUv = profesor;
        cargarDatosProfesor();
        cbDepartamento.setDisable(true);
        limitarCaracteres();
        cargarAreasAcademicas();
        cbDepartamento.setDisable(true);
        cbAsignatura.setDisable(true);
        configurarSeleccionDepartamento();
        configurarSeleccionAsignatura();
        configurarFechaFin();
        dpFechaFin.setDisable(true);

        cbDepartamento.setDisable(true);
        cargarEstadoComponentes();
    }

    public void inicializarValores(ProfesorUv profesor, OfertaColaboracion oferta) {
        this.profesorUv = profesor;
        this.ofertaColaboracion = oferta;
        cargarDatosProfesor();
        cargarDatosOfertaColaboracionSeleccionada();
        cargarEstadoComponentes();
    }

    private void cargarEstadoComponentes() {
        limitarCaracteres();
        cargarAreasAcademicas();
        cargarPeriodos();
        cbAsignatura.setDisable(true);
        configurarSeleccionDepartamento();
        configurarSeleccionAsignatura();
        configurarFechaFin();
        dpFechaFin.setDisable(true);
    }

    private void cargarDatosProfesor() {
        lbNombreProfesor.setText("Nombre profesor: " + profesorUv.getNombre());
        lbCorreoProfesor.setText("Correo: " + profesorUv.getCorreo());
        lbRegionProfesor.setText("Region: " + obtenerRegionProfesor());
    }

    private void cargarDatosOfertaColaboracionSeleccionada() {
        tfNombreColaboracion.setText(ofertaColaboracion.getNombre());
        tfIdioma.setText(ofertaColaboracion.getIdioma());
        taObjetivo.setText(ofertaColaboracion.getObjetivoGeneral());
        cbPeriodo.setValue(ofertaColaboracion.getPeriodo());
        taTemaInteres.setText(ofertaColaboracion.getTemaInteres());
        cbAreaAcademica.setValue(obtenerAreaAcademicaOfertaColaboracion().getAreaAcademical());
        cbAsignatura.setValue(obtenerAreaAcademicaOfertaColaboracion());
        tfNombreColaboracion.setDisable(true);
        tfIdioma.setDisable(true);
        taObjetivo.setDisable(true);
        cbPeriodo.setDisable(true);
        taTemaInteres.setDisable(true);
        cbAreaAcademica.setDisable(true);
        cbAsignatura.setDisable(true);
        cargarDepartamentosPorArea();;
    }

    private String obtenerRegionProfesor() {
        HashMap<String, Object> obtenerRegion = RegionDAO.consultarRegionPorId(profesorUv.getIdRegion());
        if (!(Boolean) obtenerRegion.get(Constantes.KEY_ERROR)) {
            return (String) obtenerRegion.get("region");
        } else {
            Utils.mostrarAlertaSimple("", "No se han podido cargar los datos", AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
            return null;
        }
    }

    private Asignatura obtenerAreaAcademicaOfertaColaboracion() {
        HashMap<String, Object> obtenerAreaAcademica = 
        AsignaturaDAO.consultarAsignaturaPorId(ofertaColaboracion.getIdAsignatura());
        if (!(Boolean) obtenerAreaAcademica.get(Constantes.KEY_ERROR)) {
            return (Asignatura) obtenerAreaAcademica.get("area");
        } else {
            Utils.mostrarAlertaSimple("", "No se han podido cargar los datos", AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
            return null;
        }
    }

    
    private void cargarAreasAcademicas() {
        listaAreasAcademicas = FXCollections.observableArrayList();
        HashMap<String, Object> obtenerAreaAcademica = 
        AsignaturaDAO.consultarAreaAcademicaPorRegion(profesorUv.getIdRegion());
        listaAreasAcademicas.addAll((ArrayList<String>) obtenerAreaAcademica.get("listaArea"));
        cbAreaAcademica.setItems(listaAreasAcademicas);
    }

    private void cargarPeriodos() {
        LocalDateTime fechaNTP = ConexionApacheNet.obtenerFechaHoraServidorNTP(Constantes.SERVIDOR_NTP);
        ObservableList<String> observablePeriodo =  FXCollections.observableArrayList();
        ArrayList<String> listaPeriodos = new ArrayList<>();
        String []periodos = {"ENER-JUN", "AGOST-DIC"};
        int mesActual = fechaNTP.getMonthValue();
            if(mesActual >= 6 && mesActual <= 11){
                listaPeriodos.add(periodos[1] + " " + fechaNTP.getYear());
                listaPeriodos.add(periodos[0] + " " + (fechaNTP.getYear() + 1));
            }else{
                listaPeriodos.add(periodos[0] + " " + fechaNTP.getYear());
                listaPeriodos.add(periodos[1] + " " + fechaNTP.getYear());
            }
        observablePeriodo.addAll(listaPeriodos);
        cbPeriodo.setItems(observablePeriodo);
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

    private void cargarDepartamentosPorArea(){
        listaDepartamentos = FXCollections.observableArrayList();
        HashMap<String, Object> obtenerDepartamento = DepartamentoDAO.consultarDepartamentoPorAreaAcad(cbAreaAcademica.getValue());
        listaDepartamentos.addAll((ArrayList<Departamento>) obtenerDepartamento.get("listaDepartamento"));
        cbDepartamento.setItems(listaDepartamentos);
        cbDepartamento.setValue(listaDepartamentos.get(0));
    }

    private void configurarFechaFin() {
        dpFechaInicio.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null) {
                    dpFechaFin.setValue(null);
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
                || tfIdioma.getText().isEmpty() || taObjetivo.getText().isEmpty() || cbPeriodo.getValue() == null
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
        colaboracion.setEstado("Activo");
        colaboracion.setFechaInicio(dpFechaInicio.getValue().toString());
        colaboracion.setFechaFin(dpFechaFin.getValue().toString());
        colaboracion.setIdioma(tfIdioma.getText());
        colaboracion.setObjetivoGeneral(taObjetivo.getText());
        colaboracion.setPeriodo(cbPeriodo.getValue());
        colaboracion.setTemaInteres(taTemaInteres.getText());
        colaboracion.setNoEstudiantesExternos(Integer.parseInt(tfNoEstudiantes.getText()));
        colaboracion.setIdAsignatura(cbAsignatura.getValue().getIdAsignatura());
        colaboracion.setIdDepartamento(cbDepartamento.getValue().getIdDepartamento());
        colaboracion.setIdProfesorUV(profesorUv.getIdProfesorUv());
        colaboracion.setIdRegion(profesorUv.getIdRegion());
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
            ex.printStackTrace();
        }
        return planProyecto;
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if(!camposVacios()) {
            if(validarFechas() && validarNoEstudiantes() && validarLongitudNombrePlanProyecto()) {
                btnPlanProyecto.setStyle("");
                Colaboracion colaboracion = obtenerDatosColaboracion();
                PlanProyecto planProyecto = obtenerDatosPlanProyecto();
                guardarColaboracionConPlanProyecto(colaboracion, planProyecto);
            } else {
                Utils.mostrarAlertaSimple
                ("", "Se han introducido datos inválidos", AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
            }
        } else {
            if (archivoPlan == null) {
                btnPlanProyecto.setStyle("-fx-border-width: 3px; -fx-border-color: lightcoral;");
            }
            Utils.mostrarAlertaSimple
            ("Rellenar campos obligatorios", "Se han dejado campos obligatorios vacíos", AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
        }
    }

    private void guardarColaboracionConPlanProyecto(Colaboracion colaboracion, PlanProyecto planProyecto) {
        HashMap<String, Object> respuesta = ColaboracionDAO.guardarColaboracionConPlanProyecto(colaboracion, planProyecto);
        if (!(Boolean) respuesta.get(Constantes.KEY_ERROR)) {
            Utils.mostrarAlertaSimple(null, ""+respuesta.get(Constantes.KEY_MENSAJE), AlertType.INFORMATION, (Stage) panelDeslisante.getScene().getWindow());
            cerrarVentana();
        } else {
            Utils.mostrarAlertaSimple("Error", ""+respuesta.get(Constantes.KEY_MENSAJE), AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void btnPlanProyecto(ActionEvent event) {
        FileChooser dialogoSeleccion = new FileChooser();
        dialogoSeleccion.setTitle("Seleccionar plan");
        String etiquetaTipoDato = "Archivos pdf (*.png, *.jpg, *.jpeg)";
        FileChooser.ExtensionFilter filtro = 
                new FileChooser.ExtensionFilter(etiquetaTipoDato, "*.png", "*.jpg", "*.jpeg");
        dialogoSeleccion.getExtensionFilters().add(filtro);
        Stage escenarioActual = (Stage) panelDeslisante.getScene().getWindow();
        archivoPlan = dialogoSeleccion.showOpenDialog(escenarioActual);
        if (archivoPlan != null) {
            btnPlanProyecto.setStyle("-fx-border-width: 3px; -fx-border-color: lightgreen;");
        } else {
            btnPlanProyecto.setStyle("");
        }
    }

    private void cerrarVentana() {
        irPantallaHome(profesorUv);
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
    private void clicMisOfertas(MouseEvent event) {
            irPantallaOfertasColaboracion(profesorUv);
    }

    @FXML
    private void clicMisColaboraciones(MouseEvent event) {
        irPantallaColaboraciones(profesorUv);
    }

    @FXML
    private void clicHome(MouseEvent event) {
        irPantallaHome(profesorUv);
    }

    public void irPantallaColaboraciones(ProfesorUv profesorUv){
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLConsultarColaboraciones.fxml"));
            Parent root = cargarObjeto.load();
            FXMLConsultarColaboraciones consultarColaboraciones = cargarObjeto.getController();
            consultarColaboraciones.inicializarValores(profesorUv);
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Consultar colaboraciones");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.show();
            Stage stagePrincipal = (Stage)lbCorreoProfesor.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    public void irPantallaOfertasColaboracion(ProfesorUv profesorUv){
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLConsultarOfertaColaboraciones.fxml"));
            Parent root = cargarObjeto.load();
            FXMLConsultaOfertaColaboracionesController vistaOfertaCol = cargarObjeto.getController();
            vistaOfertaCol.inicializarValores(profesorUv);
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Mis ofertas");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.show();
            Stage stagePrincipal = (Stage)lbCorreoProfesor.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    public void irPantallaHome(ProfesorUv profesorUv){
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLVistaProfesor.fxml"));
            Parent root = cargarObjeto.load();
            FXMLVistaProfesorController vistaHome = cargarObjeto.getController();
            vistaHome.inicializarValores(profesorUv.getIdProfesorUv());
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Registrar ofertas de colaboracion");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.show();
            Stage stagePrincipal = (Stage)lbCorreoProfesor.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
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
