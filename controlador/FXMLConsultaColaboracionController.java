/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import coilvic.modelo.pojo.Colaboracion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLConsultaColaboracionController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(Colaboracion colaboracion){
        this.colaboracion = colaboracion;
        cargarDatos();
    }
    public void cargarDatos(){
        lbNameColaboracion.setText(colaboracion.getNombre());
        lbProfesor.setText(colaboracion.getNombreProfesorUV());
        lbDepartamento.setText(colaboracion.getNombreDepartamento());
        lbRegion.setText(colaboracion.getNombreRegion());
        lbAsignatura.setText(colaboracion.getNombreAsignatura());
        lbAreaAcademica.setText(colaboracion.getNombreRegion());
        lbCorreo.setText(colaboracion.getNombreRegion());
        lbFechaInicio.setText(colaboracion.getFechaInicio());
        lbFechaFin.setText(colaboracion.getFechaFin());
        lbNameProfesorExterno.setText(colaboracion.getNombreProfesorExterno());
        lbCorreoProfesorExterno.setText(colaboracion.getNombreProfesorExterno());
        lbInstitucion.setText(colaboracion.getNombreProfesorExterno());
    }
}
