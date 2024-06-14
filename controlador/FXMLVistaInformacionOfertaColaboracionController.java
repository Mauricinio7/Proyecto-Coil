/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.OfertaColaboracionDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.modelo.pojo.ProfesorUv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLVistaInformacionOfertaColaboracionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private ProfesorUv profesorSesion;
    private OfertaColaboracion ofertaColaboracionSesion;
    @FXML
    private Label lbNameColaboracion;
    @FXML
    private Label lbProfesor;
    @FXML
    private Label lbDepartamento;
    @FXML
    private Label lbAsignatura;
    @FXML
    private Label lbCorreo;
    @FXML
    private TextArea taObjetico;
    @FXML
    private TextArea taTema;
    @FXML
    private Button btnIniciar;
    @FXML
    private Button btnAceptar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(OfertaColaboracion ofertaColaboracionSesion, ProfesorUv profesorSesion){
        this.ofertaColaboracionSesion = ofertaColaboracionSesion;
        this.profesorSesion = profesorSesion;
        fillLabels();
    }

    public void fillLabels(){
        lbNameColaboracion.setText(ofertaColaboracionSesion.getNombre());
        HashMap<String, Object> profesor = ProfesorUvDAO.obtenerNombreProfesorPorId(ofertaColaboracionSesion.getIdProfesor());
        lbProfesor.setText((String) profesor.get("nombreProfesor"));
        HashMap <String, Object> departamento = DepartamentoDAO.obtenerNombreDepartamentoPorId(ofertaColaboracionSesion.getIdDepartamento());
        lbDepartamento.setText((String) departamento.get("nombreDepartamento"));
        HashMap <String, Object> asignatura = AsignaturaDAO.consultarNombreAsignaturaPorId(ofertaColaboracionSesion.getIdAsignatura());
        lbAsignatura.setText((String) asignatura.get("nombreAsignatura"));
        taObjetico.setText(ofertaColaboracionSesion.getObjetivoGeneral());
        taTema.setText(ofertaColaboracionSesion.getTemaInteres());
        taObjetico.setDisable(true);
        taTema.setDisable(true);
    }

    @FXML
    private void clicIniciar(ActionEvent event) {
    }

    @FXML
    private void clicBtnAceptar(ActionEvent event) {
        try{
            //abrir ventana consultar oferta colaboracion
            Stage stage = (Stage) btnAceptar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLConsultaOfertaColaboracion.fxml"));
            Parent root = loader.load();
            FXMLConsultaOfertaColaboracionController controller = loader.getController();
            controller.inicializarValores(profesorSesion);
            stage.getScene().setRoot(root);
            stage.setTitle("Consulta Oferta Colaboracion");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
