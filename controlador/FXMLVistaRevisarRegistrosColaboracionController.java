/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Colaboracion;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
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
public class FXMLVistaRevisarRegistrosColaboracionController implements Initializable {

    private String expresionValidaNombreColaboracion;
    private Pattern patronNombreColaboracion;
    ObservableList<Colaboracion> listaColaboracionObservable;
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
    public void fillEstado(){
        listaEstado = FXCollections.observableArrayList();
        listaEstado.add("Colaboraciones Pendientes");
        listaEstado.add("Colaboraciones Aceptadas");
        cbEstado.setItems(listaEstado);
        cbEstado.setValue(listaEstado.get(0));
    }
    public void configurarTabla(){
        clAsignatura.setCellValueFactory(new PropertyValueFactory<>("nombreAsignatura"));
        clDepartamento.setCellValueFactory(new PropertyValueFactory<>("nombreDepartamento"));
        clName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        clProfesor.setCellValueFactory(new PropertyValueFactory<>("nombreProfesorUV"));
        clRegion.setCellValueFactory(new PropertyValueFactory<>("nombreRegion"));
        clInformacion.setCellFactory(column -> { return new TableCell<Colaboracion, String>() {
                Button botonInformacion = new Button("Información");

                {
                    botonInformacion.setOnAction(event -> {
                        Colaboracion colaboracion = getTableView().getItems().get(getIndex());
                        try{
                            Stage stagePrincipal = (Stage)tfName.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLConsultaColaboracion.fxml"));
                            Parent root = loader.load();
                            FXMLConsultaColaboracionController controlador = loader.getController();
                            String estado = cbEstado.getSelectionModel().getSelectedItem();
                            controlador.inicializarValores(colaboracion, "Registro");
                            Scene nuevaEscena = new Scene(root);
                            stagePrincipal.setScene(nuevaEscena);
                            stagePrincipal.setTitle("Consulta Colaboración");
                            stagePrincipal.show();
                        }catch(IOException error){

                        }
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(botonInformacion);
                    }
                }
            };
        });
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
        HashMap<String, Object> respuesta = new HashMap<>();
        String posibleEstado;
        String posibleEstado2;
        String seleccionCombobox = cbEstado.getSelectionModel().getSelectedItem();
        if(seleccionCombobox.equals("Colaboraciones Pendientes")){
            posibleEstado = "Inactivo";
            posibleEstado2 = "";
        }else{
            posibleEstado = "Activo";
            posibleEstado2 = "";
        }
        if(tfName.getText().isEmpty()){
            respuesta = ColaboracionDAO.consultarColaboracionPorEstado(posibleEstado, posibleEstado2);
        }else{
            respuesta = ColaboracionDAO.consultarColaboracionPorSimilitudDeNombreYEstado(tfName.getText(), posibleEstado, posibleEstado2);
        }
        if(respuesta.containsKey("listaColaboracion")){
            ArrayList<Colaboracion> listaColaboracion = (ArrayList) respuesta.get("listaColaboracion");
            if(listaColaboracion.isEmpty()){
                Utils.mostrarAlertaSimple(Constantes.KEY_ERROR, "No se han encontrado colaboraciones", AlertType.WARNING);
                tvColaboraciones.getItems().clear();
                return;
            }
            for(Colaboracion colaboracion : listaColaboracion){
                System.out.println(colaboracion.getNombre());
                colaboracion.getIdAsignatura();
                fillElementosFaltantesColaboracion(colaboracion);
            }
            listaColaboracionObservable = FXCollections.observableArrayList(listaColaboracion);
            tvColaboraciones.setItems(listaColaboracionObservable);
        }else{
            Utils.mostrarAlertaSimple(Constantes.KEY_ERROR, Constantes.ERROR_CARGAR_DATOS, AlertType.ERROR, (Stage)tfName.getScene().getWindow());
        }
    }

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        obtenerColaboracion();
    }
    public void fillElementosFaltantesColaboracion(Colaboracion colaboracion){
        HashMap<String, Object> asignatura = AsignaturaDAO.consultarNombreAsignaturaPorId(colaboracion.getIdAsignatura());
                if(asignatura.containsKey("nombreAsignatura")){
                    colaboracion.setNombreAsignatura((String) asignatura.get("nombreAsignatura"));
                }
                HashMap<String, Object> profesor = ProfesorUvDAO.obtenerNombreProfesorPorId(colaboracion.getIdProfesorUV());
                if(profesor.containsKey("nombreProfesor")){
                    colaboracion.setNombreProfesorUV((String) profesor.get("nombreProfesor"));
                }
                HashMap<String, Object> departamento = DepartamentoDAO.obtenerNombreDepartamentoPorId(colaboracion.getIdDepartamento());
                if(departamento.containsKey("nombreDepartamento")){
                    colaboracion.setNombreDepartamento((String) departamento.get("nombreDepartamento"));
                }
                HashMap<String, Object> region = RegionDAO.obtenerNombreRegionPorId(colaboracion.getIdRegion());
                if(region.containsKey("nombreRegion")){
                    colaboracion.setNombreRegion((String) region.get("nombreRegion"));
                }
                HashMap<String, Object> profesorExternoUv = ProfesorExternoDAO.obtenerNombreProfesorExternoPorId(colaboracion.getIdProfesorExterno());
                if(profesorExternoUv.containsKey("nombreProfesorExterno")){
                    colaboracion.setNombreProfesorExterno((String) profesorExternoUv.get("nombreProfesorExterno"));
                }
                HashMap<String, Object> areaAcademica = AsignaturaDAO.consultarAreaAcademicaPorId(colaboracion.getIdAsignatura());
                if(areaAcademica.containsKey("area")){
                    colaboracion.setNombreArea((String) areaAcademica.get("area"));
                }
                HashMap<String, Object> correoProfesorUv = ProfesorUvDAO.obtenerCorreoPorIdProfesor(colaboracion.getIdProfesorUV());
                if(correoProfesorUv.containsKey("correo")){
                    colaboracion.setCorreoProfesorUv((String) correoProfesorUv.get("correo"));
                }
                HashMap<String, Object> correoProfesorExterno = ProfesorExternoDAO.obtenerCorreoProfesorExternoPorId(colaboracion.getIdProfesorExterno());
                if(correoProfesorExterno.containsKey("correo")){
                    colaboracion.setCorreoProfesorExterno((String) correoProfesorExterno.get("correo"));
                }
                HashMap<String, Object> institucionProfesorExterno = ProfesorExternoDAO.obtenerInstitucionPorIdProfesorExterno(colaboracion.getIdProfesorExterno());
                if(institucionProfesorExterno.containsKey("institucion")){
                    colaboracion.setInstitucionProfesorExterno((String) institucionProfesorExterno.get("institucion"));
                }
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
    }
}
