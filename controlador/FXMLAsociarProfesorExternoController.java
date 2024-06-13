package coilvic.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.ProfesorExterno;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FXMLAsociarProfesorExternoController implements Initializable {

    @FXML
    private ScrollPane scPaneLista;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txtNombre;
    Colaboracion colaboracion;
    ObservableList<Object> profesoresExternos;
    ToggleGroup grupoRadios = new ToggleGroup();
    Stage stage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(Colaboracion colaboracion, Stage stage){
        this.colaboracion = colaboracion;
        this.stage = stage;
        comprobarProfesoresExternos();
    }

    private void comprobarProfesoresExternos(){
        HashMap<String, Object> respuesta = ProfesorExternoDAO.comprobarProfesoresExternos();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            if(!(boolean) respuesta.get("hayProfesoresExternos")){
                Utils.mostrarAlertaSimple("Error", "Aun no hay profesores externos registrados", Alert.AlertType.ERROR, (Stage) scPaneLista.getScene().getWindow());
            }    
        } else {
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR, (Stage) scPaneLista.getScene().getWindow());
        }
    }

    private void cerrarVentana(){
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void btnClicBuscar(ActionEvent event) {
        cargarProfesorExterno();
    }

    private void cargarProfesorExterno() {
        grupoRadios.selectToggle(null);
        profesoresExternos = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorExternoDAO.obtenerProfesoresExternoPorNombre(txtNombre.getText());
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);

        if (!isError) {
            ArrayList<ProfesorExterno> profesoresExternosBD = (ArrayList<ProfesorExterno>) respuesta.get("profesoresExternos");
            if (profesoresExternosBD.size() > 0) {
                VBox contenedor = new VBox();
                contenedor.setSpacing(10);
                for (ProfesorExterno profesorExterno : profesoresExternosBD) {
                    RadioButton radioButton = new RadioButton(profesorExterno.getNombre());
                    radioButton.setUserData(profesorExterno.getIdProfesorExterno());
                    radioButton.setToggleGroup(grupoRadios);
                    contenedor.getChildren().add(radioButton);
                }
                scPaneLista.setContent(contenedor);
                grupoRadios.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    if (grupoRadios.getSelectedToggle() != null) {
                        btnAgregar.setVisible(true);
                    } else {
                        btnAgregar.setVisible(false);
                    }
                });
    
            } else {
                Utils.mostrarAlertaSimple("Error", "No hay profesores externos que coincidan con la búsqueda", Alert.AlertType.ERROR, (Stage) scPaneLista.getScene().getWindow());
            }
        } else {
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR, (Stage) scPaneLista.getScene().getWindow());
        }
    }


    @FXML
    private void clicBtnCancelar(ActionEvent event) {
            cerrarVentana();
    }

    @FXML
    private void clicbtnAgregar(ActionEvent event) {
        if (grupoRadios.getSelectedToggle() != null) {
            int idProfesorExternoSeleccionado = (int) grupoRadios.getSelectedToggle().getUserData();
            HashMap<String, Object> respuesta = ColaboracionDAO.asociarProfesorExternoColaboracion(colaboracion.getIdColaboracion(), idProfesorExternoSeleccionado);
            if (!(Boolean) respuesta.get(Constantes.KEY_ERROR)) {
                Utils.mostrarAlertaSimple("Éxito", ""+respuesta.get(Constantes.KEY_MENSAJE), AlertType.INFORMATION, (Stage) scPaneLista.getScene().getWindow());
                cerrarVentana();
                stage.close();
            } else {
                Utils.mostrarAlertaSimple("Error", ""+respuesta.get(Constantes.KEY_MENSAJE), AlertType.ERROR, (Stage) scPaneLista.getScene().getWindow());
            }
        } else {
            Utils.mostrarAlertaSimple("Error", "Debe seleccionar un profesor externo", AlertType.ERROR, (Stage) scPaneLista.getScene().getWindow());
        }
    }

    
}
