package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.utilidades.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLConsultaColaboracionController implements Initializable {

    ObservableList<String> observableElementosCombobox;
    String tipoConsulta;
    Colaboracion colaboracion;
    @FXML
    private Label lbNameColaboracion;
    @FXML
    private Label lbProfesor;
    @FXML
    private Label lbDepartamento;
    @FXML
    private Label lbRegion;
    @FXML
    private Label lbAsignatura;
    @FXML
    private Label lbAreaAcademica;
    @FXML
    private Label lbCorreo;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaFin;
    @FXML
    private Label lbNameProfesorExterno;
    @FXML
    private Label lbCorreoProfesorExterno;
    @FXML
    private Label lbInstitucion;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private AnchorPane clicBtnGuardar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(Colaboracion colaboracion, String tipoConsulta){
        this.tipoConsulta = tipoConsulta;
        this.colaboracion = colaboracion;
        fillComboboxEstado();
        cargarDatos();
    }
    
    public void fillComboboxEstado(){
        observableElementosCombobox = FXCollections.observableArrayList();
        observableElementosCombobox.add(colaboracion.getEstado());
        if(tipoConsulta.equals("Historial") || colaboracion.getEstado().equals("Activo")){
            cbEstado.setItems(observableElementosCombobox);
            cbEstado.setValue(observableElementosCombobox.get(0));
            cbEstado.setDisable(true);
            modifyButtons();
        }else{
            observableElementosCombobox.add("Activa");
            cbEstado.setItems(observableElementosCombobox);
            cbEstado.setValue(observableElementosCombobox.get(0));
        }
    }
    public void modifyButtons(){
        btnCancelar.setDisable(true);
        btnCancelar.setVisible(false);
        btnBuscar.setText("Regresar");
    }
    public void cargarDatos(){
        lbNameColaboracion.setText(colaboracion.getNombre());
        lbProfesor.setText(colaboracion.getNombreProfesorUV());
        lbDepartamento.setText(colaboracion.getNombreDepartamento());
        lbRegion.setText(colaboracion.getNombreRegion());
        lbAsignatura.setText(colaboracion.getNombreAsignatura());
        lbAreaAcademica.setText(colaboracion.getNombreArea());
        lbCorreo.setText(colaboracion.getCorreoProfesorUv());
        lbFechaInicio.setText(colaboracion.getFechaInicio());
        lbFechaFin.setText(colaboracion.getFechaFin());
        lbNameProfesorExterno.setText(colaboracion.getNombreProfesorExterno());
        lbCorreoProfesorExterno.setText(colaboracion.getCorreoProfesorExterno());
        lbInstitucion.setText(colaboracion.getInstitucionProfesorExterno());
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        if(btnBuscar.getText().equals("Regresar")){
            salirConsulta();
        }else{
            if(cbEstado.getSelectionModel().getSelectedItem().equals("Inactivo")) return;
            cambiarEstadoColaboracion();
        }
    }
    public void cambiarEstadoColaboracion(){
        HashMap<String, Object> respuesta = new HashMap<>();
        if(cbEstado.getSelectionModel().getSelectedItem().equals("Activa")){
            respuesta = ColaboracionDAO.modificarEstadoColaboracionPorId( "Activo", colaboracion.getIdColaboracion());
        }
        if(respuesta.containsKey("error")){
            boolean error = (boolean) respuesta.get("error");
            if(!error){
                Utils.mostrarAlertaSimple("Exito", "Se ha cambiado el estado de la colaboración", AlertType.INFORMATION, (Stage) lbNameColaboracion.getScene().getWindow());
                salirConsulta();
            }
        }else{
            Utils.mostrarAlertaSimple("Error", "No se ha podido cambiar el estado de la colaboración", AlertType.ERROR, (Stage) lbNameColaboracion.getScene().getWindow());
        }
    }
    public void salirConsulta(){
        try{
            Stage stage = (Stage) lbNameColaboracion.getScene().getWindow();
            FXMLLoader loader = null;
            if(tipoConsulta.equals("Historial")){
                loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLConsultarHistorial.fxml"));
            }else{
                loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLVistaRevisarRegistrosColaboracion.fxml"));
            }
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Consulta Historial Colaboraciones");
            stage.show();
        }catch(IOException error){
            error.printStackTrace();
        }
    
    }
    @FXML
    private void seleccionComboEstado(ActionEvent event) {
        if(cbEstado.getSelectionModel().getSelectedItem().equals("Activa")){
            System.out.println("Hoa");
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        salirConsulta();
    }
}
