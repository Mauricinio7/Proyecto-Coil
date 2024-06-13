package coilvic.controlador;

import coilvic.CoilVic;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.EvidenciaDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Evidencia;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXMLSubirEvidenciasExtemporaneasController implements Initializable {

    private ArrayList<Evidencia> evidencias;
    private Colaboracion colaboracion;
    @FXML
    private Pane panelDeslisante;
    @FXML
    private ImageView ivMisOfertas;
    @FXML
    private Label lbMisOfertas;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Button btnSolicitarConstancias;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Eliminar inicia
        obtenerColaboracion(4);
        obtenerListaEvidencias();
        //Eliminar termina
    }    
    
    public void inicializarValores(Integer idColaboracion){
        obtenerColaboracion(idColaboracion);
        obtenerListaEvidencias();
    }

    private void obtenerColaboracion(Integer idColaboracion) {
        HashMap<String, Object> mapColaboracion = ColaboracionDAO.obtenerColaboracionPorId(idColaboracion);
        if (!(boolean)mapColaboracion.get("error")){
            colaboracion = (Colaboracion) mapColaboracion.get("Colaboracion");
        }else{
            Utils.mostrarAlertaSimple(null, "No se han podido cargar los datos", AlertType.ERROR);
        }
    }

    private void obtenerListaEvidencias() {
        HashMap<String, Object> mapEvidencias = 
        EvidenciaDAO.obtenerEvidenciasPorIdColaboracion(colaboracion.getIdColaboracion());
        if (!(boolean)mapEvidencias.get("error")){
            evidencias = (ArrayList<Evidencia>) mapEvidencias.get("evidencias");
        }else{
            Utils.mostrarAlertaSimple(null, ""+mapEvidencias.get(Constantes.KEY_MENSAJE), AlertType.ERROR);
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

    @FXML
    private void btnSubirArchivos(ActionEvent event) {
    }
    
}
