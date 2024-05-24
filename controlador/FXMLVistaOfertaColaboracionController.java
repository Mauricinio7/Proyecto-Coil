/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import coilvic.modelo.ConexionApacheNet;
import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.ProgramaEducativo;
import coilvic.modelo.pojo.Region;
import coilvic.utilidades.Constantes;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.ObjectExpression;
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

    
    private ObservableList<Region> observadorRegion;
    private ObservableList<Asignatura> observadorAsignatura;
    private ObservableList<String> observadorAreaAcademica;
    private ObservableList<Departamento> observadorDepartamento;
    int year;
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
    public void inicializarValores(){
        fillRegion();
        fillDepartamento();
        fillAreaAcademica();
        fillAsignatura();
        //obtenerYear();
    }
    //metodos de crud
    public void fillRegion(){
        HashMap<String, Object> obtenerRegion = RegionDAO.consultarListaRegion();
        if(obtenerRegion != null && obtenerRegion.containsKey("listaRegion")){
             observadorRegion = FXCollections.observableArrayList((ArrayList<Region>) obtenerRegion.get("listaRegion"));
             cbRegion.setItems(observadorRegion);
        }else{

        }
    }
    public void fillDepartamento(){
        HashMap<String, Object> obtenerDepartamento = DepartamentoDAO.consultarListaDepartamento();
        if(obtenerDepartamento != null && obtenerDepartamento.containsKey("listaDepartamento")){
            observadorDepartamento = FXCollections.observableArrayList((ArrayList<Departamento>) obtenerDepartamento.get("listaDepartamento"));
            cbDepartamento.setItems(observadorDepartamento);
        }
        
    }
    public void fillAreaAcademica(){
        HashMap<String, Object> obtenerArea = AsignaturaDAO.consultarAreaAcademica();
        if(obtenerArea != null && obtenerArea.containsKey("listaArea")){
            observadorAreaAcademica = FXCollections.observableArrayList((ArrayList<String>) obtenerArea.get("listaArea"));
            cbAreaAcad.setItems(observadorAreaAcademica);
        }
    }
    public void fillAsignatura(){
        HashMap<String, Object> obtenerAsignatura = AsignaturaDAO.consultarListaAsignatura();
        if(obtenerAsignatura != null && obtenerAsignatura.containsKey("listaAsignatura")){
            observadorAsignatura = FXCollections.observableArrayList((ArrayList<Asignatura>)obtenerAsignatura.get("listaAsignatura"));
            cbAsignatura.setItems(observadorAsignatura);
        }
    }
    public void obtenerYear(){
        LocalDateTime fechaActual = ConexionApacheNet.obtenerFechaHoraServidorNTP(Constantes.SERVIDOR_NTP);
        if(fechaActual != null){
            year = fechaActual.getYear();
        }else{
            System.out.println("Error al establecer la conexion con servidor ntp");
        }
    }
    //metodos para validar campos
    public static boolean validarNombreColaboracion(String nombre){
        String regex = "[a-zA-Z0-9íáéóúñÁÉÍÓÚÑÜ ]+";
        Pattern patronCoincidencias =Pattern.compile(regex);
        Matcher coincidencias = patronCoincidencias.matcher(nombre);
        return coincidencias.matches();
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
}
