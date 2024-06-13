package coilvic.controlador;

import coilvic.CoilVic;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.pojo.ProfesorUv;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
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

public class FXMLSubirEvidenciasExtemporaneasController implements Initializable {

    private Integer idColaboracion;
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
    
    public void inicializarValores(Integer idColaboracion){
        this.idColaboracion = idColaboracion;
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
        HashMap<String, Object> mapProfesorUV = ProfesorUvDAO.obtenerProfesorUvPorId(1);
        if(mapProfesorUV.containsKey("Profesor")){
            ProfesorUv profesorUvSesion = (ProfesorUv) mapProfesorUV.get("Profesor");
            irPantallaOfertasColaboracion(profesorUvSesion);
        }else{
            System.out.println("Error al conectar con la bd");
        }
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
