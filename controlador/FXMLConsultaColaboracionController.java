package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import coilvic.modelo.pojo.Colaboracion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLConsultaColaboracionController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(Colaboracion colaboracion, String tipoConsulta, String estado){
        this.tipoConsulta = tipoConsulta;
        cbEstado.setDisable(true);
        if(tipoConsulta.equals("Inactiva")){
            cbEstado.setDisable(false);
        }
        this.colaboracion = colaboracion;
        cargarDatos();
        cbEstado.setValue(estado);
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
}
