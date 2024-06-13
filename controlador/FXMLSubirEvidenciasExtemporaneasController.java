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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXMLSubirEvidenciasExtemporaneasController implements Initializable {

    private File archivoEvidencia;
    private Evidencia evidencia = new Evidencia();
    private Pane contenedor;
    private double nextYPosition;
    private ArrayList<Evidencia> evidencias;
    private Colaboracion colaboracion;
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
    private ScrollPane scPanePrincipal;
    @FXML
    private Button btnSubirArchivo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Eliminar inicia
        obtenerColaboracion(4);
        obtenerListaEvidencias();

        //Eliminar termina

        limitarCaracteres();
    }    
    
    public void inicializarValores(Integer idColaboracion){
        obtenerColaboracion(idColaboracion);
        obtenerListaEvidencias();
        
    }

    private void obtenerColaboracion(Integer idColaboracion) {
        HashMap<String, Object> mapColaboracion = ColaboracionDAO.obtenerColaboracionPorId(idColaboracion);
        if (!(boolean)mapColaboracion.get("error")){
            colaboracion = (Colaboracion) mapColaboracion.get("Colaboracion");
        }else{
            Utils.mostrarAlertaSimple(null, "No se han podido cargar los datos", AlertType.ERROR);
        }
    }

    private void obtenerListaEvidencias() {
        HashMap<String, Object> mapEvidencias = 
        EvidenciaDAO.obtenerEvidenciasPorIdColaboracion(colaboracion.getIdColaboracion());
        if (!(boolean)mapEvidencias.get("error")){
            evidencias = (ArrayList<Evidencia>) mapEvidencias.get("evidencias");
        }else{
            Utils.mostrarAlertaSimple(null, ""+mapEvidencias.get(Constantes.KEY_MENSAJE), AlertType.ERROR);
        }
    }

    private void crearFichaEvidencia(String nombre) {
        Pane objeto = new Pane();
        objeto.setPrefSize(300, 80);
        objeto.setLayoutX(0);
        objeto.setLayoutY(nextYPosition);
        objeto.setStyle("-fx-background-color: #303030;");
        Label lbNombre = new Label(nombre);
        lbNombre.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 17px;");
        lbNombre.setLayoutX(10);
        lbNombre.setLayoutY(20);
        objeto.getChildren().add(lbNombre);
        contenedor.getChildren().add(objeto);
        nextYPosition += 82;
        contenedor.setPrefHeight(nextYPosition);
    }

    private void cargarPanelScroll(){
        nextYPosition = 0;
        contenedor = new Pane();
        contenedor.setPrefSize(470, 10);
        contenedor.setLayoutX(0);
        contenedor.setLayoutY(0);
        contenedor.setStyle("-fx-background-color: #FFFFFF;");
        scPanePrincipal.setContent(contenedor);
    }
    
    public void irPantallaOfertasColaboracion(ProfesorUv profesorUv){
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("/coilvic/vista/FXMLVistaOfertaColaboracion.fxml"));
            Parent root = cargarObjeto.load();
            FXMLVistaOfertaColaboracionController vistaOfertaCol = cargarObjeto.getController();
            vistaOfertaCol.inicializarValores(profesorUv);
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
        Utils.mostrarAlertaSimple(null, "Se han solicitado las constancias", AlertType.INFORMATION);
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (validarCamposVacios()) {
            Utils.mostrarAlertaSimple(null, "Se han dejado campos obligatorios vacíos", AlertType.WARNING);
        } else {
            obtenerDatosEvidencia();
            HashMap<String, Object> mapEvidencia = EvidenciaDAO.insertarEvidencia(evidencia);
            if (!(boolean)mapEvidencia.get("error")){
                Utils.mostrarAlertaSimple(null, ""+Constantes.KEY_MENSAJE, AlertType.INFORMATION);
            }else{
                Utils.mostrarAlertaSimple(null, ""+mapEvidencia.get(Constantes.KEY_MENSAJE), AlertType.ERROR);
            }
        }
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
        LocalDateTime fecha = ConexionApacheNet.obtenerFechaHoraServidorNTP(Constantes.SERVIDOR_NTP);
        String fechaformato = (fecha.getYear() + "-" + fecha.getMonthValue() + "-" + fecha.getDayOfMonth());
        evidencia.setFechaEntrega(fechaformato);
        try {
            byte[] archivo = Files.readAllBytes(archivoEvidencia.toPath());
            evidencia.setArchivo(archivo);
        } catch (IOException ex) {
            Utils.mostrarAlertaSimple(null, "Error al cargar el archivo", AlertType.ERROR);
        }
    }

    private boolean validarCamposVacios() {
        return tfNombre.getText().isEmpty() || taDescripcion.getText().isEmpty() || archivoEvidencia == null;
    }

    private void limitarCaracteres() {
        tfNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 45) {
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
        cerrarVentana();
    }

    private void cerrarVentana() {
        try {
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("/coilvic/vista/FXMLVistaProfesor.fxml");
            Parent root = loader.load();
            Scene escenaPrincipal = new Scene(root);
            stage.setScene(escenaPrincipal);
            stage.show();
        } catch (IOException ex) {
            Utils.mostrarAlertaSimple("Error", "Error al abrir la ventana", AlertType.ERROR);
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

}
