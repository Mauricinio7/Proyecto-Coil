package coilvic.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class FXMLVerColaboracionController implements Initializable {

    @FXML
    private Label lblColaboracion;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaFin;
    @FXML
    private Label lblIdioma;
    @FXML
    private TextArea txtAreaObjetivo;
    @FXML
    private Label lblPeriodo;
    @FXML
    private Label lblTemaInteres;
    @FXML
    private Label lblArea;
    @FXML
    private Label lblAsignatura;
    @FXML
    private Label lblDepartamento;
    @FXML
    private ImageView imgPlanProyecto;
    Colaboracion colaboracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(int idColaboracion){ 
        HashMap<String, Object> respuesta = ColaboracionDAO.obtenerColaboracionPorId(idColaboracion);
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            colaboracion = (Colaboracion) respuesta.get("Colaboracion");
            cargarDatosColaboracion();
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosColaboracion(){
        lblColaboracion.setText(lblColaboracion.getText() + colaboracion.getNombre());
        lblFechaInicio.setText(lblFechaInicio.getText() + colaboracion.getFechaInicio());
        lblFechaFin.setText(lblFechaFin.getText() + colaboracion.getFechaFin());
        lblIdioma.setText(lblIdioma.getText() + colaboracion.getIdioma());
        txtAreaObjetivo.setText(colaboracion.getObjetivoGeneral());
        lblPeriodo.setText(lblPeriodo.getText() + colaboracion.getPeriodo());
        lblTemaInteres.setText(lblTemaInteres.getText() + colaboracion.getTemaInteres());
        //lblArea.setText(lblArea.getText() + colaboracion.getArea());
        //lblAsignatura.setText(lblAsignatura.getText() + colaboracion.getAsignatura()); //de otra tabla
        //lblDepartamento.setText(lblDepartamento.getText() + colaboracion.getDepartamento()); //de otra tabla
        //imgPlanProyecto.setImage(Utils.convertirImagen()); 
    }

    private void btnClicVolver(ActionEvent event) {
            Stage stage = (Stage) lblColaboracion.getScene().getWindow();
            stage.close();
    }

    @FXML
    private void btnClicRegistrarAlumnos(ActionEvent event) {
    }

    @FXML
    private void btnClicRegistrarProfesorEx(ActionEvent event) {
    }

    @FXML
    private void btnClicConcluir(ActionEvent event) {
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
    }
    
}
