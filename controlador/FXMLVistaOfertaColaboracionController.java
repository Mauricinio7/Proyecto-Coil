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
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import coilvic.modelo.ConexionApacheNet;
import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.modelo.pojo.Region;
import coilvic.utilidades.Constantes;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLVistaOfertaColaboracionController implements Initializable {

    
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
    }
    //metodos de crud
    public void fillRegion(){
        disableCombobox();
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
        }else{

        }
    }
    //listerners 
    public void modificarAreaAcademica(){
        cbRegion.valueProperty().addListener(new ChangeListener<Region>(){
            @Override
            public void changed(ObservableValue<? extends Region> observable, Region oldValue, Region newValue) {
                disableCombobox();
                if(newValue != null){
                    cbAreaAcad.setDisable(false);
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
    public void disableCombobox(){
        cbAreaAcad.setDisable(true);
        cbAsignatura.setDisable(true);
        cbDepartamento.setDisable(true);
    }
    @FXML
    private void clicSave(MouseEvent event) {
        if(!areComboboxEmpty() && validNameForColaboracion(tfNameCol.getText()) && validNameForLenguage(tfIdioma.getText()) && validObjetiveTopic(taObjetivo.getText())
            && validObjetiveTopic(taTemaInteres.getText()) && passRepetiveFilter()){
            System.out.println("aprobado");
         }else{

         }
    }

    @FXML
    private void clicCancel(MouseEvent event) {   
    }
    //validacions
    public boolean areComboboxEmpty(){
        if(cbAreaAcad.getSelectionModel().getSelectedItem() == null || cbRegion.getSelectionModel().getSelectedItem() == null 
            || cbAsignatura.getSelectionModel().getSelectedItem() == null || cbPeriodo.getSelectionModel().getSelectedItem() == null || cbDepartamento.getSelectionModel().getSelectedItem() == null){
                return true;
        }
        return false;
    }
    public static boolean validNameForColaboracion(String nombre){
        String regex = "[a-zA-Z0-9íáéóúñÁÉÍÓÚÑÜ. ]+";
        Pattern patronCoincidencias = Pattern.compile(regex);
        Matcher coincidencias = patronCoincidencias.matcher(nombre);
        return coincidencias.matches();
    }
    public static boolean validNameForLenguage(String idioma){
        String regex = "[a-zA-Z0-99íáéóúñÁÉÍÓÚÑÜ.]+";
        Pattern patron = Pattern.compile(regex);
        Matcher coincidencias = patron.matcher(idioma);
        return coincidencias.matches();
    }
    public static boolean validObjetiveTopic(String objetivoTema){
        String regex = "[a-zA-Z0-9()íáéóúñÁÉÍÓÚÑÜ¿?.\\[\\] ]+";
        Pattern patrones = Pattern.compile(regex);
        Matcher coincidencias = patrones.matcher(objetivoTema);
        return coincidencias.matches();
    }
    public static boolean containRepetiveWords(String cadena){
        String regex = "[a-zA-Z0-9()íáéóúñÁÉÍÓÚÑÜ¿?.\\[\\] ]+";
        Stack <String> colaTokens = new Stack<>();
        int countRepetiveWords = 0;
        Pattern patron = Pattern.compile(regex);
        Matcher coincidencias = patron.matcher(cadena);
        while(coincidencias.find()){
            String token = coincidencias.group();
            if(colaTokens.empty()){
                colaTokens.push(token);
                countRepetiveWords++;
            }else if(colaTokens.peek().equals(token)){
                colaTokens.push(token);
                countRepetiveWords++;
                if(isSpecialChar(token) && countRepetiveWords == 2){
                    return true;
                }
            }else{
                colaTokens.push(token);
                countRepetiveWords = 1; 
            }
        }
        return false;
    }
    public static boolean isSpecialChar(String token){
        if(token.equals(".") || token.equals("?")|| token.equals("¿") || token.equals("-")){
            return true;
        }
        return false;
    }
    public boolean passRepetiveFilter(){
        if(!(containRepetiveWords(tfNameCol.getText()) && containRepetiveWords(tfIdioma.getText()) && containRepetiveWords(taObjetivo.getText()) && containRepetiveWords(taTemaInteres.getText()))){
                return true;
        }
        return false;
    }
}
