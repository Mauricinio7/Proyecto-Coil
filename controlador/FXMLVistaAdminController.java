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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class FXMLVistaAdminController implements Initializable {

    @FXML
    private Pane panelDeslisante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    public void inicializarValores(){
        
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

    public void irOfertasExternas(){
        try{
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLRegistrarOfertaExterna.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Registrar oferta externa");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void clicBtnCerrarSesion(ActionEvent event) {
        try{
            Stage stage = (Stage) panelDeslisante.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/coilvic/vista/FXMLLogin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio de sesion");
            stage.show();
        }catch(IOException error){
            error.printStackTrace();
        }
    }
    
}