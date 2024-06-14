//TODO el profesor se debe de obtener del login
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.mysql.jdbc.Constants;
import com.mysql.jdbc.Util;

import coilvic.CoilVic;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Utils;
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
    ProfesorUv profesorUvSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(int idProfesorUv){
        HashMap<String, Object> mapProfesorUV = ProfesorUvDAO.obtenerProfesorUvPorId(idProfesorUv);
        if(mapProfesorUV.containsKey("Profesor")){
            profesorUvSesion = (ProfesorUv) mapProfesorUV.get("Profesor");
            System.out.println(profesorUvSesion.getNombre());
        }else{
            System.out.println("Error al conectar con la bd");
        }
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
        HashMap<String, Object> mapProfesorUV = ProfesorUvDAO.obtenerProfesorUvPorId(1);
        if(mapProfesorUV.containsKey("Profesor")){
            ProfesorUv profesorUvSesion = (ProfesorUv) mapProfesorUV.get("Profesor");
            irPantallaOfertasColaboracion(profesorUvSesion);
        }else{
            System.out.println("Error al conectar con la bd");
        }
        
    }

    @FXML
    private void clicMisOfertasLB(MouseEvent event) {
            irPantallaOfertasColaboracion(profesorUvSesion);
    }

    public void irPantallaOfertasColaboracion(ProfesorUv profesorUv){
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("/coilvic/vista/FXMLVistaOfertaColaboracion.fxml"));
            Parent root = cargarObjeto.load();
            FXMLVistaOfertaColaboracionController vistaOfertaCol = cargarObjeto.getController();
            vistaOfertaCol.inicializarValores(profesorUv);
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Registrar ofertas de colaboracion");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.show();
            Stage stagePrincipal = (Stage)ivMisOfertas.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
    }
}
