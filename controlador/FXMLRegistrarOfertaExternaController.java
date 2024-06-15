/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import coilvic.modelo.dao.OfertaColaboracionExternaDAO;

import coilvic.modelo.ConexionApacheNet;
import coilvic.modelo.pojo.OfertaColaboracionExterna;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLRegistrarOfertaExternaController implements Initializable {

    private String expresionValidaNombreColaboracion;
    private Pattern patronNombreColaboracion;
    private String expresionValidaNombreIdioma;
    private Pattern patronNombreIdioma;
    private String expresionValidaTopic;
    private Pattern patronTopic;
    private LocalDateTime fechaNTP;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private Label lbName;
    @FXML
    private Label lbPeriodo;
    @FXML
    private Label lbObjetivoGeneral;
    @FXML
    private ComboBox<String> cbPeriodo;
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
    private TextField tfAsignatura;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        asignarFechaActualNTP();
        inicializarPattern();
        verifyNonValideCharsNameColaboracion();
        verifyNonValideCharsLenguage();
        verifyNonValideCharsObjetivo();
        verifyNonValidTopic();
        verifyNonValidAsignatura();
        fillPeriodo();
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
    private void clicSave(MouseEvent event) {
            if(!tfNameCol.getText().isEmpty() && !tfIdioma.getText().isEmpty() && !taTemaInteres.getText().isEmpty() && cbPeriodo.getValue() != null){
                HashMap<String, Boolean> respuesta = new HashMap<>();
                OfertaColaboracionExterna nuevaOferta = new OfertaColaboracionExterna();
                nuevaOferta.setNombre(tfNameCol.getText());
                nuevaOferta.setObjetivoGeneral(taObjetivo.getText());
                nuevaOferta.setIdioma(tfIdioma.getText());
                nuevaOferta.setPeriodo(cbPeriodo.getValue());
                nuevaOferta.setTemaInteres(taTemaInteres.getText());
                nuevaOferta.setAsignatura(tfAsignatura.getText());
                nuevaOferta.setIdProfesorExterno(1);
                respuesta = OfertaColaboracionExternaDAO.guardarOferta(nuevaOferta);
                if(respuesta.containsKey("ofertaColaboracion")){
                    Utils.mostrarAlertaConfirmacion("Guardado", "Se ha guardado correctamente", AlertType.INFORMATION);
                    
                }else{
                    Utils.mostrarAlertaConfirmacion("Error", Constantes.ERROR_CARGAR_DATOS, AlertType.ERROR);
                }
            }
        }

        @FXML
        private void clicCancel(MouseEvent event) {
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
    public void verifyNonValideCharsObjetivo(){
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
    public void verifyNonValidTopic(){
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
    public void verifyNonValidAsignatura(){
        tfAsignatura.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0){
                    tfAsignatura.setText("");
                }else{
                    threadValidationInputText(oldValue, tfAsignatura);
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

    public boolean isValidText(String text, Pattern patron){
        Matcher coincidencia = patron.matcher(text);
        return coincidencia.matches();
    }

    @FXML
    private void clicHome(MouseEvent event) {
        irInicio();
    }

    @FXML
    private void clicOfertas(MouseEvent event) {
	irOfertasExternas();
    }

    @FXML
    private void clicRegistrarProfesor(MouseEvent event) {
        irProfesorExterno();
    }

    @FXML
    private void clicConsultas(MouseEvent event) {
        irConsultas();
    }

public void irOfertasExternas(){
        try{
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLRegistrarOfertaExterna.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Registrar Profesor Externo");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void irInicio(){
        try{
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLVistaAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio");
            stage.show();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    public void irConsultas(){
        try{
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLVistaParaConsultasAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Consultas");
            stage.show();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    public void irProfesorExterno(){
        try{
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLRegistrarProfesorExterno.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Registrar Profesor Externo");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void inicializarPattern(){
        expresionValidaNombreColaboracion = "[a-zA-Z0-9íáéóúüñÁÉÍÓÚÑÜ.\\- ]+";
        patronNombreColaboracion = Pattern.compile(expresionValidaNombreColaboracion);
        expresionValidaNombreIdioma = "[a-zA-ZíáéóúñÁÉÍÓÚÑÜ. ]+";
        patronNombreIdioma = Pattern.compile(expresionValidaNombreIdioma);
        expresionValidaTopic = "[a-zA-Z0-9()íáéóúñÁÉÍÓÚÑÜ¿?.\\[\\]\\- ]+";
        patronTopic= Pattern.compile(expresionValidaTopic);
    }
}
