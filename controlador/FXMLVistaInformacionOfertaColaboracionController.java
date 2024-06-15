/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.CoilVic;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private Label lbAsignatura;
    @FXML
    private TextArea taObjetico;
    @FXML
    private TextArea taTema;
    @FXML
    private Button btnIniciar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Label lbPeriodo;
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
        HashMap <String, Object> asignatura = AsignaturaDAO.consultarNombreAsignaturaPorId(ofertaColaboracionSesion.getIdAsignatura());
        lbAsignatura.setText((String) asignatura.get("nombreAsignatura"));
        taObjetico.setText(ofertaColaboracionSesion.getObjetivoGeneral());
        taTema.setText(ofertaColaboracionSesion.getTemaInteres());
        taObjetico.setDisable(true);
        taTema.setDisable(true);
        lbPeriodo.setText(ofertaColaboracionSesion.getPeriodo());
    }

    @FXML
    private void clicIniciar(ActionEvent event) {
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLRegistrarColaboracionSinOferta.fxml"));
            Parent root = cargarObjeto.load();
            FXMLRegistrarColaboracionSinOfertaController vistaRegistrarColaboracion = cargarObjeto.getController();
            vistaRegistrarColaboracion.inicializarValores(profesorSesion, ofertaColaboracionSesion);
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Registrar colaboración con oferta");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.show();
            Stage stagePrincipal = (Stage)lbAsignatura.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    @FXML
    private void clicBtnAceptar(ActionEvent event) {
        irPantallaOfertasColaboracion(profesorSesion);
        
    }
    
    public void irPantallaOfertasColaboracion(ProfesorUv profesorUv){
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLConsultarOfertaColaboraciones.fxml"));
            Parent root = cargarObjeto.load();
            FXMLConsultaOfertaColaboracionesController vistaOfertaCol = cargarObjeto.getController();
            vistaOfertaCol.inicializarValores(profesorUv);
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Mis ofertas");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.show();
            Stage stagePrincipal = (Stage)taTema.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
    }
}
