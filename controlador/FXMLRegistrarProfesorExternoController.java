package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.pojo.ProfesorExterno;
import coilvic.observador.ObservadorProfesorExterno;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLRegistrarProfesorExternoController implements Initializable, ObservadorProfesorExterno {

    private ObservableList<ProfesorExterno> profesoresExternos;
    private FilteredList<ProfesorExterno> profesoresExternosFiltrados;
    
    @FXML
    private Pane panelDeslisante;
    @FXML
    private TextField tfBusquedaProfesor;
    @FXML
    private TableView<ProfesorExterno> tvProfesoresExternos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colIdioma;
    @FXML
    private TableColumn colInstitucion;
    @FXML
    private TableColumn colPais;
    @FXML
    private TableColumn colTelefono;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosProfesoresExternos();
        configurarBusqueda();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        colInstitucion.setCellValueFactory(new PropertyValueFactory<>("institucion"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    private void cargarDatosProfesoresExternos() {
        profesoresExternos = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorExternoDAO.obtenerProfesoresExternos();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<ProfesorExterno> profesoresExternosConsulta = 
            (ArrayList<ProfesorExterno>) respuesta.get("profesoresExternos");
            profesoresExternos.addAll(profesoresExternosConsulta);
        } else {
            Utils.mostrarAlertaSimple("Error", ""+respuesta.get(Constantes.KEY_MENSAJE), AlertType.ERROR);
        }
    }

    private void configurarBusqueda() {
        profesoresExternosFiltrados = new FilteredList<>(profesoresExternos, p -> false); // Inicialmente filtra todo
        
        tfBusquedaProfesor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                profesoresExternosFiltrados.setPredicate(profesorExterno -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return false; // No mostrar nada si el campo de búsqueda está vacío
                    }
                    String valorMinuscula = newValue.toLowerCase();
                    return profesorExterno.getNombre().toLowerCase().contains(valorMinuscula);
                });
                actualizarTabla();
            }
        });
    }

    private void actualizarTabla() {
        SortedList<ProfesorExterno> profesoresExternosOrdenados = new SortedList<>(profesoresExternosFiltrados);
        profesoresExternosOrdenados.comparatorProperty().bind(tvProfesoresExternos.comparatorProperty());
        tvProfesoresExternos.setItems(profesoresExternosOrdenados);
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
    private void btnAnadir(ActionEvent event) {
        abrirFormularioProfesorExterno();
    }

    private void abrirFormularioProfesorExterno() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLFormularioAgregarProfesorExterno.fxml"));
            Parent root = loader.load();
            FXMLFormularioAgregarProfesorExternoController controlador = loader.getController();
            controlador.inicializarValores(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Utils.mostrarAlertaSimple("Error", "Error al abrir el formulario", AlertType.ERROR);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        irInicio();
    }

    @Override
    public void profesorGuardado() {
        cargarDatosProfesoresExternos();    
        configurarBusqueda();
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


}
