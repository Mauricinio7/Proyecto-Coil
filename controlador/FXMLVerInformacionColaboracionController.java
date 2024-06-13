package coilvic.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.PlanProyectoDAO;
import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.modelo.pojo.PlanProyecto;
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


public class FXMLVerInformacionColaboracionController implements Initializable {

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

                HashMap<String, Object> asignatura = AsignaturaDAO.consultarNombreAsignaturaPorId(colaboracion.getIdAsignatura());
                if(asignatura.containsKey("nombreAsignatura")){
                    colaboracion.setNombreAsignatura((String) asignatura.get("nombreAsignatura"));
                    lblAsignatura.setText(lblAsignatura.getText() + colaboracion.getNombreAsignatura());
                }
                HashMap<String, Object> areaAcademica = AsignaturaDAO.consultarAreaAcademicaPorId(colaboracion.getIdAsignatura());
                if(areaAcademica.containsKey("area")){
                    colaboracion.setNombreArea((String) areaAcademica.get("area"));
                    lblArea.setText(lblArea.getText() + colaboracion.getNombreArea());
                }
                HashMap<String, Object> departamento = DepartamentoDAO.obtenerNombreDepartamentoPorId(colaboracion.getIdDepartamento());
                if(departamento.containsKey("nombreDepartamento")){
                    colaboracion.setNombreDepartamento((String) departamento.get("nombreDepartamento"));
                    lblDepartamento.setText(lblDepartamento.getText() + colaboracion.getNombreDepartamento());
                }

                HashMap<String, Object> planProyecto = new HashMap<>();
                PlanProyectoDAO.obtenerPlanProyectoPorIdColaboracion(colaboracion.getIdColaboracion());
                if(planProyecto.containsKey("planProyecto")){
                    PlanProyecto nuevoPlanProyecto = (PlanProyecto) planProyecto.get("planProyecto");
                    if(planProyecto != null){
                        System.out.println(nuevoPlanProyecto.getNombre());
                    }
                }

            //TODO Mostrar Plan del proyecto
            //imgPlanProyecto.setImage(Utils.convertirImagen()); 
    }

    @FXML
    private void btnClicVolver(ActionEvent event) {
            Stage stage = (Stage) lblColaboracion.getScene().getWindow();
            stage.close();
    }
    
}
