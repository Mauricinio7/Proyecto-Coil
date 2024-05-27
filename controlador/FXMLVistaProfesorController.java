
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import coilvic.maincoilvic.CoilVic;
import coilvic.modelo.pojo.ProfesorUv;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXMLVistaProfesorController implements Initializable {

    @FXML
    private Pane panelDeslisante;
    @FXML
    private ImageView ivMisOfertas;
    @FXML
    private Label lbMisOfertas;

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
        transicion.setToX(210);
        
        transicion.play();
    }

    @FXML
    private void clicMisOfertasIV(MouseEvent event) {
        ProfesorUv profesorUv = new ProfesorUv();
        irPantallaOfertasColaboracion(profesorUv);
        
    }

    @FXML
    private void clicMisOfertasLB(MouseEvent event) {
        ProfesorUv profesorUv = new ProfesorUv();
        irPantallaOfertasColaboracion(profesorUv);
    }

    public void irPantallaOfertasColaboracion(ProfesorUv profesorUv){
        try{
            Stage stageOferta = new Stage();
            stageOferta.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("../vista/FXMLVistaOfertaColaboracion.fxml"));
            Parent root = cargarObjeto.load();
            FXMLVistaOfertaColaboracionController vistaOfertaCol = cargarObjeto.getController();
            vistaOfertaCol.inicializarValores(profesorUv);
            Scene nuevaScena = new Scene(root);
            stageOferta.setTitle("Registrar ofertas de colaboracion");
            stageOferta.setScene(nuevaScena);
            stageOferta.show();
            Stage stagePrincipal = (Stage)ivMisOfertas.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
    }
}
