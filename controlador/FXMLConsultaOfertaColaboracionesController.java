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

import coilvic.modelo.dao.OfertaColaboracionDAO;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.modelo.pojo.ProfesorUv;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLConsultaOfertaColaboracionesController implements Initializable {

    ProfesorUv profesorSesion;
    String expresionValidaNombreColaboracion = "[a-zA-Z0-9íáéóúüñÁÉÍÓÚÑÜ.\\- ]+";
    Pattern patronNombreColaboracion = Pattern.compile(expresionValidaNombreColaboracion);
    ObservableList<OfertaColaboracion> listaOfertaColaboracionObservable;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private TextField tfName;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<OfertaColaboracion> tvColaboraciones;
    @FXML
    private TableColumn clName;
    @FXML
    private TableColumn clPeriodo;
    @FXML
    private TableColumn clInformacion;
    @FXML
    private ImageView ivMisOfertas;
    @FXML
    private TableColumn clIdioma;
    @FXML
    private Label lbMisOfertas;
    @FXML
    private TableColumn clContador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        Platform.runLater(() -> {
            btnBuscar.requestFocus();
        });
        verifyNonValideTfName();
        tvColaboraciones.setVisible(false);
        //TODO quitar despues
        this.profesorSesion = new ProfesorUv();
        profesorSesion.setIdProfesorUv(3);
    }    
    public void inicializarValores(ProfesorUv profesorSesion){
        //TODO agregue un profesor de prueba
        this.profesorSesion = profesorSesion;
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

    public void configurarTabla(){
        clName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        clIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        clContador.setCellValueFactory(new PropertyValueFactory<>("contador"));
        clInformacion.setCellFactory(column -> { return new TableCell<OfertaColaboracion, String>() {
                Button botonInformacion = new Button("Información");
                {
                    botonInformacion.setOnAction(event -> {
                        OfertaColaboracion oferta = getTableView().getItems().get(getIndex());
                        try {
                            Stage stagePrincipal = (Stage)tfName.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLVistaInformacionOfertaColaboracion.fxml"));
                            Parent root = loader.load();
                            FXMLVistaInformacionOfertaColaboracionController controlador = loader.getController();
                            controlador.inicializarValores(oferta, profesorSesion);
                            Scene nuevaEscena = new Scene(root);
                            stagePrincipal.setScene(nuevaEscena);
                            stagePrincipal.setTitle("Información Oferta Colaboración");
                            stagePrincipal.setMinWidth(nuevaEscena.getWidth());
                            stagePrincipal.setMinHeight(nuevaEscena.getHeight());
                            stagePrincipal.show();
                        } catch (IOException error) {
                            error.printStackTrace();
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
    public void consultarOfertaColaboracionPorNombreEIdProfesor(){
        HashMap<String, Object> respuesta = OfertaColaboracionDAO.consultarOfertaColaboracionPorNombreEIdProfesor(tfName.getText(), profesorSesion.getIdProfesorUv());
        if(respuesta.containsKey("listaOfertaColaboracion")){
            listaOfertaColaboracionObservable = FXCollections.observableArrayList((ArrayList<OfertaColaboracion>) respuesta.get("listaOfertaColaboracion"));
            for(OfertaColaboracion oferta : listaOfertaColaboracionObservable){
                oferta.setContador(oferta.getContador() + 1);
            }
            tvColaboraciones.setItems(listaOfertaColaboracionObservable);
        }else{
            Utils.mostrarAlertaSimple("Colaboraciones no encontradas", "No se han encontrado ofertas de colaboración", AlertType.ERROR);
        }

    }


    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        if(tfName.getText().length() > 0){
            consultarOfertaColaboracionPorNombreEIdProfesor();
            tvColaboraciones.setVisible(true);
        }
    }

    @FXML
    private void clicMisOfertasIV(MouseEvent event) {
    }

    @FXML
    private void clicMisOfertasLB(MouseEvent event) {
    }
}