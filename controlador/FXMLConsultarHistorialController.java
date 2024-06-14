/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.PlanProyectoDAO;
import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.modelo.pojo.PlanProyecto;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.ThreadVerifyRepetitiveChars;
import coilvic.utilidades.Utils;
import coilvic.utilidades.VerifyValidCharsThread;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLConsultarHistorialController implements Initializable {

    String expresionValidaNombreColaboracion = "[a-zA-Z0-9íáéóúüñÁÉÍÓÚÑÜ.\\- ]+";
    Pattern patronNombreColaboracion = Pattern.compile(expresionValidaNombreColaboracion);
    ObservableList<Colaboracion> listaColaboracionObservable = FXCollections.observableArrayList();
    private ObservableList<String> listaEstado;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private TextField tfName;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Colaboracion> tvColaboraciones;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private TableColumn clName;
    @FXML
    private TableColumn clProfesor;
    @FXML
    private TableColumn clPeriodo;
    @FXML
    private TableColumn clAsignatura;
    @FXML
    private TableColumn clDepartamento;
    @FXML
    private TableColumn clRegion;
    @FXML
    private TableColumn clInformacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillEstado();
        configurarTabla();
        Platform.runLater(() -> {
            btnBuscar.requestFocus();
        });
        verifyNonValideTfName();
    }    
      @FXML
    private void salePanel(MouseEvent event) {
        TranslateTransition transicion = new TranslateTransition();
        transicion.setDuration(Duration.millis(500));
        transicion.setNode(panelDeslisante);
        transicion.setToX(0);
        tvColaboraciones.setVisible(true);
        tfName.setVisible(true);
        transicion.play();
    }

    @FXML
    private void entraPanel(MouseEvent event) {
        TranslateTransition transicion = new TranslateTransition();
        transicion.setDuration(Duration.millis(500));
        transicion.setNode(panelDeslisante);
        transicion.setToX(230);
        tvColaboraciones.setVisible(false);
        tfName.setVisible(false);
        
        transicion.play();
    }
    public void fillEstado(){
        listaEstado = FXCollections.observableArrayList();
        listaEstado.add("Colaboraciones Activas");
        listaEstado.add("Colaboraciones Pendientes");
        cbEstado.setItems(listaEstado);
        cbEstado.setValue(listaEstado.get(0));
    }
    public void configurarTabla(){
        clAsignatura.setCellValueFactory(new PropertyValueFactory<>("nombreAsignatura"));
        clDepartamento.setCellFactory(new PropertyValueFactory<>("nombreDepartamento"));
        clName.setCellFactory(new PropertyValueFactory<>("nombre"));
        clPeriodo.setCellFactory(new PropertyValueFactory<>("periodo"));
        clProfesor.setCellFactory(new PropertyValueFactory<>("nombreProfesorUV"));
        clRegion.setCellFactory(new PropertyValueFactory<>("nombreRegion"));
        clInformacion.setCellFactory(new PropertyValueFactory<>("idColaboracion"));
    }
    public void verifyNonValideTfName(){
        tfName.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0){
                    tfName.setText("");
                }else{                      
                    threadValidationInputText(oldValue, tfName);
                }
            }
            
        });
    }
    public void threadValidationInputText(String oldValue, TextInputControl textComponent){
        Platform.runLater(()-> {
            Thread validacionRepetidos = new Thread(new ThreadVerifyRepetitiveChars(textComponent, oldValue));
            Thread validacionCaracteresNoValidos = new Thread(new VerifyValidCharsThread(textComponent, patronNombreColaboracion, oldValue));
            validacionCaracteresNoValidos.start();
            validacionRepetidos.start();
        });
    }
    public void obtenerColaboracion(){
        String posibleEstado;
        String posibleEstado2;
        String seleccionCombobox = cbEstado.getSelectionModel().getSelectedItem();
        Stage stage = (Stage)tfName.getScene().getWindow();
        if(seleccionCombobox.equals("Colaboraciones Activas")){
            posibleEstado = "Activo";
            posibleEstado2 = "";
        }else{
            posibleEstado = "Finalizada en periodo";
            posibleEstado2 = "Finalizada completamente";
        }
        HashMap<String, Object> respuesta = ColaboracionDAO.consultarColaboracionPorEstado(posibleEstado, posibleEstado2);
        if(respuesta.containsKey("listaColaboracion")){
            ArrayList<Colaboracion> listaColaboracion = (ArrayList) respuesta.get("listaColaboracion");
            for(Colaboracion colaboracion : listaColaboracion){
                System.out.println(colaboracion.getNombre());
                colaboracion.getIdAsignatura();
                HashMap<String, Object> asignatura = AsignaturaDAO.consultarNombreAsignaturaPorId(colaboracion.getIdAsignatura());
                if(asignatura.containsKey("nombreAsignatura")){
                    colaboracion.setNombreAsignatura((String) asignatura.get("nombreAsignatura"));
                    System.out.println(colaboracion.getNombreAsignatura());
                }
                HashMap<String, Object> profesor = ProfesorUvDAO.obtenerNombreProfesorPorId(colaboracion.getIdProfesorUV());
                if(profesor.containsKey("nombreProfesor")){
                    colaboracion.setNombreProfesorUV((String) profesor.get("nombreProfesor"));
                    System.out.println(colaboracion.getNombreProfesorUV());
                }
                HashMap<String, Object> departamento = DepartamentoDAO.obtenerNombreDepartamentoPorId(colaboracion.getIdDepartamento());
                if(departamento.containsKey("nombreDepartamento")){
                    colaboracion.setNombreDepartamento((String) departamento.get("nombreDepartamento"));
                    System.out.println(colaboracion.getNombreDepartamento());
                }
                HashMap<String, Object> region = RegionDAO.obtenerNombreRegionPorId(colaboracion.getIdRegion());
                if(region.containsKey("nombreRegion")){
                    colaboracion.setNombreRegion((String) region.get("nombreRegion"));
                    System.out.println(colaboracion.getNombreRegion());
                }
                HashMap<String, Object> profesorExternoUv = ProfesorExternoDAO.obtenerNombreProfesorExternoPorId(colaboracion.getIdProfesorExterno());
                if(profesorExternoUv.containsKey("nombreProfesorExterno")){
                    colaboracion.setNombreProfesorExterno((String) profesorExternoUv.get("nombreProfesorExterno"));
                    System.out.println(colaboracion.getNombreProfesorExterno());
                }
                
                HashMap<String, Object> planProyecto = new HashMap<>();
                if(colaboracion.getIdColaboracion() != null){
                    System.out.println(colaboracion.getIdColaboracion());
                }else{
                    System.out.println("No hay id");
                }
                PlanProyectoDAO.obtenerPlanProyectoPorIdColaboracion(colaboracion.getIdColaboracion());
                if(planProyecto.containsKey("planProyecto")){
                    PlanProyecto nuevoPlanProyecto = (PlanProyecto) planProyecto.get("planProyecto");
                    if(planProyecto != null){
                        System.out.println(nuevoPlanProyecto.getNombre());
                    }
                }
            }
            // tvColaboraciones.setItems(listaColaboracionObservable);
        }else{
            Utils.mostrarAlertaSimple(Constantes.KEY_ERROR, Constantes.ERROR_CARGAR_DATOS, AlertType.ERROR, (Stage)tfName.getScene().getWindow());
        }
    }

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        obtenerColaboracion();
    }
}
