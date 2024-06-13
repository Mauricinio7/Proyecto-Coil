/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import coilvic.modelo.ConexionApacheNet;
import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.OfertaColaboracionDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.modelo.pojo.Region;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.ThreadVerifyRepetitiveChars;
import coilvic.utilidades.Utils;
import coilvic.utilidades.VerifyValidCharsThread;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLVistaOfertaColaboracionController implements Initializable {

    String expresionValidaNombreColaboracion = "[a-zA-Z0-9íáéóúüñÁÉÍÓÚÑÜ.\\- ]+";
    Pattern patronNombreColaboracion = Pattern.compile(expresionValidaNombreColaboracion);
    String expresionValidaNombreIdioma = "[a-zA-ZíáéóúñÁÉÍÓÚÑÜ. ]+";
    Pattern patronNombreIdioma = Pattern.compile(expresionValidaNombreIdioma);
    String expresionValidaTopic = "[a-zA-Z0-9()íáéóúñÁÉÍÓÚÑÜ¿?.\\[\\]\\- ]+";
    Pattern patronTopic= Pattern.compile(expresionValidaTopic);
    ProfesorUv profesorSesion;
    private ObservableList<Region> observadorRegion;
    private ObservableList<Asignatura> observadorAsignatura;
    private ObservableList<String> observadorAreaAcademica;
    private ObservableList<Departamento> observadorDepartamento;
    LocalDateTime fechaNTP;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private ComboBox<String> cbPeriodo;
    @FXML
    private ComboBox<Departamento> cbDepartamento;
    @FXML
    private TextArea taObjetivo;
    @FXML
    private TextArea taTemaInteres;
    @FXML
    private TextField tfIdioma;
    @FXML
    private TextField tfNameCol;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;
    @FXML
    private Label lbName;
    @FXML
    private Label lbPeriodo;
    @FXML
    private Label lbRegion;
    @FXML
    private Label lbDepartamento;
    @FXML
    private Label lbObjetivoGeneral;
    @FXML
    private ComboBox<Region> cbRegion;
    @FXML
    private ComboBox<String> cbAreaAcad;
    @FXML
    private ComboBox<Asignatura> cbAsignatura;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(ProfesorUv profesorSesion){
        this.profesorSesion = profesorSesion;
        asignarFechaActualNTP();
        fillPeriodo();
        fillRegion();
        modificarAreaAcademica();
        modificarDepartamento();
        modificarAsignatura();
        verifyNonValideCharsNameColaboracion();
        verifyNonValideCharsLenguage();
        verifyNonValideCharsObjetivo();
        verifyNonValidTopic();
    }
    //metodos de crud
    public void fillRegion(){
        HashMap<String, Object> obtenerRegion = RegionDAO.consultarListaRegion();
        verificarConsulta(obtenerRegion, cbRegion, observadorRegion, "listaRegion");
    }
    public void fillAreaAcademicaPorRegion(int idRegion){
        HashMap<String, Object> obtenerAreaAcademica = AsignaturaDAO.consultarAreaAcademicaPorRegion(idRegion);
        verificarConsulta(obtenerAreaAcademica, cbAreaAcad, observadorAreaAcademica, "listaArea");
    }
    public void fillDepartamentoPorAreaAcad(String areaAcad){
        HashMap<String, Object> obtenerDepartamento = DepartamentoDAO.consultarDepartamentoPorAreaAcad(areaAcad);
        verificarConsulta(obtenerDepartamento, cbDepartamento, observadorDepartamento, "listaDepartamento");
    }
    public void fillAsignaturaPorDepartamento(int idDepartamento){
        HashMap<String, Object> obtenerAsignatura = AsignaturaDAO.consultaAsignaturaDepartamento(idDepartamento);
        verificarConsulta(obtenerAsignatura, cbAsignatura, observadorAsignatura, "listaAsignatura");
    }
    public <T> void verificarConsulta(HashMap<String, Object>consulta, ComboBox<T> comboAModificar, ObservableList<T> observador, String key){
        if(consulta != null && consulta.containsKey(key)){
            observador = FXCollections.observableArrayList((ArrayList<T>)consulta.get(key));
            comboAModificar.setItems(observador);
            comboAModificar.setValue(observador.get(0));
            setComboboxValues(observador.get(0));
        }else{
            Stage stagePrincipal = (Stage) tfIdioma.getScene().getWindow();
            Utils.mostrarAlertaSimple(Constantes.TITTLE_ERROR_CONEXION, Constantes.ERROR_CARGAR_DATOS, AlertType.ERROR, stagePrincipal);
        }
    }
    public boolean setComboboxValues(Object classType){
        if(classType == null) return false;
        if(classType instanceof Region){
            fillAreaAcademicaPorRegion(cbRegion.getSelectionModel().getSelectedItem().getIdRegion());
            fillDepartamentoPorAreaAcad(cbAreaAcad.getSelectionModel().getSelectedItem());
            fillAsignaturaPorDepartamento(cbDepartamento.getSelectionModel().getSelectedItem().getIdDepartamento());
        }else if(classType instanceof Asignatura && classType instanceof String){
            fillDepartamentoPorAreaAcad(cbAreaAcad.getSelectionModel().getSelectedItem());
            fillAsignaturaPorDepartamento(cbDepartamento.getSelectionModel().getSelectedItem().getIdDepartamento());
        }else if(classType instanceof Departamento){
            fillAsignaturaPorDepartamento(cbDepartamento.getSelectionModel().getSelectedItem().getIdDepartamento());
        }
        return true;
    }
    //listerners 
    public void modificarAreaAcademica(){
        cbRegion.valueProperty().addListener(new ChangeListener<Region>(){
            @Override
            public void changed(ObservableValue<? extends Region> observable, Region oldValue, Region newValue) {
                if(newValue != null){
                    fillAreaAcademicaPorRegion(newValue.getIdRegion());
                }
            }
        });
    }
    public void modificarDepartamento(){
        cbAreaAcad.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null){
                    cbDepartamento.setDisable(false);
                    fillDepartamentoPorAreaAcad(newValue);
                }
            }
        });
    }
    public void modificarAsignatura(){
        cbDepartamento.valueProperty().addListener(new ChangeListener<Departamento>(){
            @Override
            public void changed(ObservableValue<? extends Departamento> observable, Departamento oldValue, Departamento newValue) {
                if(newValue != null){
                    cbAsignatura.setDisable(false);
                    fillAsignaturaPorDepartamento(newValue.getIdDepartamento());
                }
            }
        });
    }
    public void asignarFechaActualNTP(){
        try{
            LocalDateTime fechaActual = ConexionApacheNet.obtenerFechaHoraServidorNTP(Constantes.SERVIDOR_NTP);
            if(fechaActual != null){
                fechaNTP = fechaActual;
                System.out.println("fecha NTP");
            }else{

            }
        }catch(Exception error){
            error.printStackTrace();
        }
    }
    public void verifyNonValideCharsNameColaboracion(){
        tfNameCol.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0){
                    tfNameCol.setText("");
                }else{
                    threadValidationInputText(oldValue, tfNameCol);              
                }
            }
        });
    }
    public void verifyNonValideCharsLenguage(){
        tfIdioma.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0){
                    tfIdioma.setText("");
                }else{
                    threadValidationInputText(oldValue, tfIdioma);
                }
            }
            
        });
    }
    void verifyNonValideCharsObjetivo(){
        taObjetivo.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0){
                    taObjetivo.setText("");
                }else{
                    threadValidationInputText(oldValue, taObjetivo);
                }
            }
            
        });
    }
    void verifyNonValidTopic(){
        taTemaInteres.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0){
                    taTemaInteres.setText("");
                }else{
                    threadValidationInputText(oldValue, taTemaInteres);
                }
            }
            
        });
    }
    public void fillPeriodo(){
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
    public void threadValidationInputText(String oldValue, TextInputControl component){
        Thread threadVerifyValidCharsName = new Thread(new VerifyValidCharsThread(component, patronNombreColaboracion, oldValue));
        Thread threadVerifyRepetitiveChars = new Thread(new ThreadVerifyRepetitiveChars(component, oldValue));
        threadVerifyValidCharsName.start(); 
        threadVerifyRepetitiveChars.start(); 
    }
     //metodos de animacion 
     @FXML
     private void salePanel(MouseEvent event) {
         lbName.setVisible(true);
         lbDepartamento.setVisible(true);
         lbObjetivoGeneral.setVisible(true);
         lbPeriodo.setVisible(true);
         lbRegion.setVisible(true);
         taObjetivo.setVisible(true);
         TranslateTransition transicion = new TranslateTransition();
         transicion.setDuration(Duration.millis(500));
         transicion.setNode(panelDeslisante);
         transicion.setToX(0);
         
         transicion.play();
     }
 
     @FXML
     private void entraPanel(MouseEvent event) {
         lbName.setVisible(false);
         lbDepartamento.setVisible(false);
         lbObjetivoGeneral.setVisible(false);
         lbPeriodo.setVisible(false);
         lbRegion.setVisible(false);
         taObjetivo.setVisible(false);
         TranslateTransition transicion = new TranslateTransition();
         transicion.setDuration(Duration.millis(500));
         transicion.setNode(panelDeslisante);
         transicion.setToX(210);
         
         transicion.play();
     }
     @FXML
     private void btSaveAnimationEntered(MouseEvent event) {
         mouseEnteredButton(btSave);
     }
 
     @FXML
     private void btCancelAnimationEntered(MouseEvent event) {
         mouseEnteredButton(btCancel);
     }
 
     @FXML
     private void btSaveAnimationExited(MouseEvent event) {
         mouseExitedButton(btSave);
     } 
     @FXML
     private void btCancelAnimationExited(MouseEvent event) {
         mouseExitedButton(btCancel);
     }
     public void mouseEnteredButton(Button btScale){
        btScale.setScaleX(1.1);
        btScale.setScaleY(1.1);
    }
    public void mouseExitedButton(Button btScale){
        btScale.setScaleX(1);
        btScale.setScaleY(1);
    }
    @FXML
    private void clicSave(MouseEvent event) {
       if(!areComboboxEmpty() && isValidText(tfNameCol.getText(), patronNombreColaboracion) && isValidText(tfIdioma.getText(), patronNombreIdioma)
            && (taObjetivo.getText().length() == 0 || isValidText(taObjetivo.getText(), patronTopic)) && isValidText(taTemaInteres.getText(), patronTopic)){
                OfertaColaboracion nuevaOfertaColaboracion = new OfertaColaboracion();
                nuevaOfertaColaboracion.setNombre(tfNameCol.getText());
                nuevaOfertaColaboracion.setIdAsignatura(cbAsignatura.getSelectionModel().getSelectedItem().getIdAsignatura());
                nuevaOfertaColaboracion.setIdProfesor(profesorSesion.getIdProfesorUv());
                nuevaOfertaColaboracion.setIdioma(tfIdioma.getText());
                nuevaOfertaColaboracion.setObjetivoGeneral(taObjetivo.getText());
                nuevaOfertaColaboracion.setPeriodo(cbPeriodo.getSelectionModel().getSelectedItem());
                nuevaOfertaColaboracion.setTemaInteres(taTemaInteres.getText());
                HashMap <String, Boolean> mapGuardadoOferta = OfertaColaboracionDAO.guardarOferta(nuevaOfertaColaboracion);
                if(mapGuardadoOferta != null && mapGuardadoOferta.containsKey("ofertaColaboracion")){
                    Stage stagePrincipal = (Stage)tfIdioma.getScene().getWindow();
                    Utils.mostrarAlertaSimple("Guardado correcto", Constantes.SAVE_OFERTA_MESSAGE, AlertType.INFORMATION, stagePrincipal);
                }
       }else{
            Stage stagePrincipal = (Stage) tfIdioma.getScene().getWindow();
            Utils.mostrarAlertaSimple(Constantes.TITTLE_CAMPOS_VACIOS, Constantes.CAMPOS_VACIOS, AlertType.WARNING, stagePrincipal);
       }
    }

    @FXML
    private void clicCancel(MouseEvent event) {  
        Stage stagePrincipal = (Stage) tfIdioma.getScene().getWindow();
        boolean confirmarSalida = Utils.mostrarAlertaConfirmacion("Salir", "¿Seguro que desea salir sin registrar la oferta?", AlertType.CONFIRMATION, stagePrincipal);
        if(confirmarSalida){
            System.out.println("Clic Aceptar");
        }
    }
    //validacions
    public boolean areComboboxEmpty(){
        if(cbAreaAcad.getSelectionModel().getSelectedItem() == null || cbRegion.getSelectionModel().getSelectedItem() == null 
            || cbAsignatura.getSelectionModel().getSelectedItem() == null || cbPeriodo.getSelectionModel().getSelectedItem() == null || cbDepartamento.getSelectionModel().getSelectedItem() == null){
                return true;
        }
        return false;
    }
    public boolean isValidText(String text, Pattern patron){
        Matcher coincidencia = patron.matcher(text);
        return coincidencia.matches();
    }
}
