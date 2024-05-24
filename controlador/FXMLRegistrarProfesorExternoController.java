package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.pojo.ProfesorExterno;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLRegistrarProfesorExternoController implements Initializable {

    private ObservableList<ProfesorExterno> profesoresExternos;
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
    @FXML
    private TableColumn colColaboracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
        cargarDatosProfesoresExternos();
        configurarBusqueda();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colIdioma.setCellValueFactory(new PropertyValueFactory("idioma"));
        colInstitucion.setCellValueFactory(new PropertyValueFactory("institucion"));
        colPais.setCellValueFactory(new PropertyValueFactory("pais"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
    }

    private void cargarDatosProfesoresExternos() {
        profesoresExternos = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorExternoDAO.obtenerProfesoresExternos();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<ProfesorExterno> profesoresExternosConsulta = 
            (ArrayList<ProfesorExterno>) respuesta.get("profesoresExternos");
            profesoresExternos.addAll(profesoresExternosConsulta);
            tvProfesoresExternos.setItems(profesoresExternos);
        } else {
            Utils.mostrarAlertaSimple("Error", ""+respuesta.get(Constantes.KEY_MENSAJE), AlertType.ERROR);
        }
    }

    private void configurarBusqueda() {
        if (profesoresExternos.size() > 0) {
            FilteredList<ProfesorExterno> profesoresExternosFiltrados = 
            new FilteredList<>(profesoresExternos, p -> true);
            tfBusquedaProfesor.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    profesoresExternosFiltrados.setPredicate(profesorExterno -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String valorMinuscula = newValue.toLowerCase();
                        if (profesorExterno.getNombre().toLowerCase().contains(valorMinuscula)) {
                            return true;
                        }
                        return false;
                    });
                }
            });
            SortedList<ProfesorExterno> profesoresExternosOrdenados = new SortedList<>(profesoresExternosFiltrados);
            profesoresExternosOrdenados.comparatorProperty().bind(tvProfesoresExternos.comparatorProperty());
            tvProfesoresExternos.setItems(profesoresExternosOrdenados);
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
        transicion.setToX(230);
        
        transicion.play();
    }

    @FXML
    private void btnAnadir(ActionEvent event) {
        abrirFormularioProfesorExterno();
    }

    private void abrirFormularioProfesorExterno() {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(coilvic.maincoilvic.CoilVic.class.getResource("vista/FXMLFormularioProfesorExterno.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            escenario.setScene(scene);
            escenario.setTitle("");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException e) {
            Utils.mostrarAlertaSimple("Error", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        //REGRESAR A LA VENTANA ANTERIOR
    }
    
}
