/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import coilvic.utilidades.Utils;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author outis
 */
public class FXMLVistaParaConsultasAdminController implements Initializable {

    @FXML
    private Pane panelDeslisante;
    @FXML
    private Button btnConsultaHistorial;
    @FXML
    private Button btnRevisarRegistros;
    @FXML
    private Button btnConsultaHistorial1;
    @FXML
    private Button btnConsultaHistorial11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void clicConsultaHistorial(ActionEvent event) {
        try{
            Stage stage = (Stage) btnConsultaHistorial.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLConsultarHistorial.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Consulta Historial de colaboraciones");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void clicRevisarRegistros(ActionEvent event) {
        try{
            Stage stage = (Stage) btnRevisarRegistros.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLVistaRevisarRegistrosColaboracion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Consulta Registros");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
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

    @FXML
    private void clicReporteColaboraciones(ActionEvent event) {
        try{
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLConsultarReporteColaboraciones.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Reporte Colaboraciones");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void clicReporteProfesores(ActionEvent event) {
        try{
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLConsultarReporteProfesores.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Reporte profesores");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

   
}
