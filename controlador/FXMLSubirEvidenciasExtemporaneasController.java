package coilvic.controlador;

import coilvic.CoilVic;
import coilvic.modelo.ConexionApacheNet;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.EvidenciaDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Evidencia;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXMLSubirEvidenciasExtemporaneasController implements Initializable {

    private LocalDateTime fecha;
    private File archivoEvidencia;
    private Evidencia evidencia = new Evidencia();
    private ObservableList<Evidencia> evidencias;
    private Colaboracion colaboracion;
    private ProfesorUv profesorUv;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private ImageView ivMisOfertas;
    @FXML
    private Label lbMisOfertas;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Button btnSubirArchivo;
    @FXML
    private TableView<Evidencia> tvArchivosEvidencias;
    @FXML
    private TableColumn tcArchivosEvidencias;
    @FXML
    private Button btnSolicitarConstancias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    private void configurarBtnSolicitarConstancias() {
        if (colaboracion.getEstado().equals("Finalizada completamente")) {
            btnSolicitarConstancias.setDisable(true);
        } else {
            btnSolicitarConstancias.setDisable(false);
        }
    }
    
    public void inicializarValores(Integer idColaboracion, ProfesorUv profesorUv){
        this.profesorUv = profesorUv;
        obtenerColaboracion(idColaboracion);
        obtenerListaEvidencias();

        limitarCaracteres();
        configurarTabla();
        configurarBtnSolicitarConstancias();
    }

    private void configurarTabla() {
        tcArchivosEvidencias.setCellValueFactory(new PropertyValueFactory("nombre"));
    }

    private void obtenerColaboracion(Integer idColaboracion) {
        HashMap<String, Object> mapColaboracion = ColaboracionDAO.obtenerColaboracionPorId(idColaboracion);
        if (!(boolean)mapColaboracion.get("error")){
            colaboracion = (Colaboracion) mapColaboracion.get("Colaboracion");
        }else{
            Utils.mostrarAlertaSimple(null, "No se han podido cargar los datos", AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
        }
    }

    private void obtenerListaEvidencias() {
        evidencias = FXCollections.observableArrayList();
        HashMap<String, Object> mapEvidencias = 
        EvidenciaDAO.obtenerEvidenciasPorIdColaboracion(colaboracion.getIdColaboracion());
        if (!(boolean)mapEvidencias.get("error")){
            ArrayList<Evidencia> listaEvidencias = (ArrayList<Evidencia>) mapEvidencias.get("evidencias");
            evidencias.addAll(listaEvidencias);
            tvArchivosEvidencias.setItems(evidencias);
        }else{
            Utils.mostrarAlertaSimple(null, ""+mapEvidencias.get(Constantes.KEY_MENSAJE), AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
        }
    }
    

    @FXML
    private void btnSubirArchivos(ActionEvent event) {
        FileChooser dialogoSeleccion = new FileChooser();
        dialogoSeleccion.setTitle("Selecciona las evidencias pdf");
        String etiquetaTipoDato = "Archivos pdf (*.pdf)";
        FileChooser.ExtensionFilter filtroExtension = 
        new FileChooser.ExtensionFilter(etiquetaTipoDato, "*.pdf");
        dialogoSeleccion.getExtensionFilters().add(filtroExtension);
        Stage escenarioActual = (Stage) panelDeslisante.getScene().getWindow();
        archivoEvidencia = dialogoSeleccion.showOpenDialog(escenarioActual);
        if (archivoEvidencia != null) {
            btnSubirArchivo.setStyle("-fx-border-width: 3px; -fx-border-color: lightgreen;");
        } else {
            btnSubirArchivo.setStyle("");
        }
    }

    @FXML
    private void btnSolicitarConstancias(ActionEvent event) {
        Utils.mostrarAlertaSimple(null, "Se han solicitado las constancias", AlertType.INFORMATION, (Stage) panelDeslisante.getScene().getWindow());
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (validarCamposVacios()) {
            Utils.mostrarAlertaSimple(null, "Se han dejado campos obligatorios vacíos", AlertType.WARNING, (Stage) panelDeslisante.getScene().getWindow());
        } else if (validarRegistroDuplicado()) {
            Utils.mostrarAlertaSimple(null, "El archivo que desea subir ya existe", AlertType.WARNING, (Stage) panelDeslisante.getScene().getWindow());
        } else {
            obtenerDatosEvidencia();
            HashMap<String, Object> mapEvidencia = EvidenciaDAO.insertarEvidencia(evidencia);
            if (!(boolean)mapEvidencia.get("error")){
                if (evidencias.size() < 6) {
                    Utils.mostrarAlertaSimple(null, ""+mapEvidencia.get(Constantes.KEY_MENSAJE), AlertType.INFORMATION, (Stage) panelDeslisante.getScene().getWindow());
                    evidencias.add(evidencia);
                    limpiarCampos();
                } else {
                    Utils.mostrarAlertaSimple(null, "No se pueden subir más de 6 archivos", AlertType.WARNING, (Stage) panelDeslisante.getScene().getWindow());
                }
            }else{
                Utils.mostrarAlertaSimple(null, ""+mapEvidencia.get(Constantes.KEY_MENSAJE), AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
            }
        }
        btnSubirArchivo.setStyle("");
    }

    private void limpiarCampos() {
        tfNombre.clear();
        taDescripcion.clear();
        evidencia = new Evidencia();
    }

    private void obtenerDatosEvidencia() {
        evidencia.setNombre(tfNombre.getText());
        evidencia.setDescripcion(taDescripcion.getText());
        evidencia.setIdColaboracion(colaboracion.getIdColaboracion());
        fecha = ConexionApacheNet.obtenerFechaHoraServidorNTP(Constantes.SERVIDOR_NTP);
        String fechaformato = (fecha.getYear() + "-" + fecha.getMonthValue() + "-" + fecha.getDayOfMonth());
        evidencia.setFechaEntrega(fechaformato);
        try {
            byte[] archivo = Files.readAllBytes(archivoEvidencia.toPath());
            evidencia.setArchivo(archivo);
        } catch (IOException ex) {
            Utils.mostrarAlertaSimple(null, "Error al cargar el archivo", AlertType.ERROR, (Stage) panelDeslisante.getScene().getWindow());
        }
    }

    private boolean validarCamposVacios() {
        return tfNombre.getText().isEmpty() || taDescripcion.getText().isEmpty() || archivoEvidencia == null;
    }

    private boolean validarRegistroDuplicado() {
        for (Evidencia evidencia : evidencias) {
            if (evidencia.getNombre().equals(tfNombre.getText())) {
                return true;
            }
        }
        return false;
    }

    private void limitarCaracteres() {
        tfNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20) {
                tfNombre.setText(oldValue);
            }
        });
        taDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                taDescripcion.setText(oldValue);
            }
        });
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        irPantallaHome(profesorUv);
    }


    private void actualizarTabla() {
        tvArchivosEvidencias.refresh();
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
    private void clicMisOfertasIV(MouseEvent event) {
        HashMap<String, Object> mapProfesorUV = ProfesorUvDAO.obtenerProfesorUvPorId(1);
        if(mapProfesorUV.containsKey("Profesor")){
            ProfesorUv profesorUvSesion = (ProfesorUv) mapProfesorUV.get("Profesor");
            irPantallaOfertasColaboracion(profesorUvSesion);
        }else{
            System.out.println("Error al conectar con la bd");
        }
        
    }

    @FXML
    private void clicMisOfertasLB(MouseEvent event) {
        HashMap<String, Object> mapProfesorUV = ProfesorUvDAO.obtenerProfesorUvPorId(1);
        if(mapProfesorUV.containsKey("Profesor")){
            ProfesorUv profesorUvSesion = (ProfesorUv) mapProfesorUV.get("Profesor");
            irPantallaOfertasColaboracion(profesorUvSesion);
        }else{
            System.out.println("Error al conectar con la bd");
        }
    }

    //TODO paquete de barra lateral inicio

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
            Stage stagePrincipal = (Stage)ivMisOfertas.getScene().getWindow();
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
            Stage stagePrincipal = (Stage)ivMisOfertas.getScene().getWindow();
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
            Stage stagePrincipal = (Stage)ivMisOfertas.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    //TODO fin paquete de barra lateral

}
