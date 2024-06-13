
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;

import com.mysql.jdbc.Constants;
import com.mysql.jdbc.Util;

import coilvic.CoilVic;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.EstudiantesDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Estudiante;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXMLConsultarColaboraciones implements Initializable {

    @FXML
    private Pane panelDeslisante;
    @FXML
    private ImageView ivMisOfertas;
    @FXML
    private Label lbMisOfertas;
    @FXML
    private ComboBox<String> cbFiltro;
    Pane contenedor;
    int nextYPosition;
    ObservableList<Object> colaboraciones;
    private ProfesorUv profesorUv;
    @FXML
    private Label lblBuscar;
    @FXML
    private ScrollPane scPaneMostrarColaboraciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCombos();
        configurarSeleccionarFiltro();
        cargarPanelScroll();
    }    
    
    public void inicializarValores(ProfesorUv profesor){
        this.profesorUv = profesor;
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
        HashMap<String, Object> mapProfesorUV = ProfesorUvDAO.obtenerProfesorUvPorId(1); //TODO Profesor del login
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

    //Inicia el consultar colaboraciones

    public void cargarCombos(){
        cbFiltro.getItems().addAll(
            "Todos",
            "Activo",
            "Finalizada en periodo",
            "Finalizada completamente"
        );
    }
    private void configurarSeleccionarFiltro(){
        cbFiltro.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null){
                    if(newValue.equals("Todos")){
                        contenedor.getChildren().clear();
                        nextYPosition = 0;
                        cargarColaboracionesTodas();
                        System.out.println("Todos");
                    }
                    else{
                        //TODO cargar por filtro
                        contenedor.getChildren().clear();
                        nextYPosition = 0;
                        cargarColaboracionesFiltro(newValue);
                        //Filtrar
                        System.out.println("Filtro");
                    }
              }
            }
            
        });
    }

    private void cargarPanelScroll(){
        contenedor = new Pane();
        contenedor.setPrefSize(1020, 10);
        contenedor.setLayoutX(0);
        contenedor.setLayoutY(0);
        contenedor.setStyle("-fx-background-color: #FFFFFF;");
        scPaneMostrarColaboraciones.setContent(contenedor);
    }
    
     private void cargarColaboracionesTodas(){
        colaboraciones = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ColaboracionDAO.obtenerColaboracionesPorProfesor(1); //TODO Darle el profesor
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<Colaboracion> colaboracionesBD = (ArrayList) respuesta.get("colaboraciones");
            if(colaboracionesBD.size() > 0){
                for (Colaboracion colaboracion: colaboracionesBD) {
                    crearFichaColaboracion(colaboracion);
                }
            }  
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR);
        }
    }

    private void cargarColaboracionesFiltro(String filtro){
        colaboraciones = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ColaboracionDAO.obtenerColaboracionesPorProfesorConFiltro(1, filtro); //TODO Darle el profesor
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<Colaboracion> colaboracionesBD = (ArrayList) respuesta.get("colaboraciones");
            if(colaboracionesBD.size() > 0){
                for (Colaboracion colaboracion: colaboracionesBD) {
                    crearFichaColaboracion(colaboracion);
                }
            }  
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR);
        }
    }

    private void crearFichaColaboracion(Colaboracion colaboracion){
        contenedor.setPrefHeight(nextYPosition);
    
        Pane objeto = new Pane();
        objeto.setPrefSize(1010, 100);
        objeto.setLayoutX(0);
        objeto.setLayoutY(nextYPosition); 
        objeto.setStyle("-fx-background-color: #d1cfc9;");
    
        Label lblNombreColaboracion = new Label(colaboracion.getNombre());
        lblNombreColaboracion.setStyle("-fx-text-fill: #000000; -fx-font-size: 20px; -fx-font-weight: bold;");
        lblNombreColaboracion.setLayoutY(40);
        lblNombreColaboracion.setLayoutX(30);

        Label lblEstadoColaboracion = new Label(colaboracion.getEstado());
        lblEstadoColaboracion.setStyle("-fx-text-fill: #000000; -fx-font-size: 20px; -fx-font-weight: bold;");
        lblEstadoColaboracion.setLayoutY(40);
        lblEstadoColaboracion.setLayoutX(600);

        if(colaboracion.getEstado().equals("Activo")){
            Button btnVer = new Button("Ver");
        btnVer.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        btnVer.setPrefSize(70, 40);
        btnVer.setLayoutY(30);
        btnVer.setLayoutX(900);
        btnVer.setOnAction(e -> {
            llamarVer(colaboracion.getIdColaboracion());
        });
        objeto.getChildren().add(btnVer);
        }else if (colaboracion.getEstado().equals("Finalizada en periodo")){
            Button btnVerInformacion = new Button("Ver Información");
        btnVerInformacion.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        btnVerInformacion.setPrefSize(160, 7);
        btnVerInformacion.setLayoutY(10);
        btnVerInformacion.setLayoutX(840);
        btnVerInformacion.setOnAction(e -> {
            llamarVerInformacion(colaboracion.getIdColaboracion());
        });

        Button btnSubirReportes = new Button("Subir reportes");
        btnSubirReportes.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        btnSubirReportes.setPrefSize(160, 7);
        btnSubirReportes.setLayoutY(55);
        btnSubirReportes.setLayoutX(840);
        btnSubirReportes.setOnAction(e -> {
            //TODO Subir evidencias extemporaneas
        });

        objeto.getChildren().add(btnVerInformacion);
        objeto.getChildren().add(btnSubirReportes);
        }else{
            Button btnVerInformacion = new Button("Ver Información");
        btnVerInformacion.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        btnVerInformacion.setPrefSize(160, 9);
        btnVerInformacion.setLayoutY(35);
        btnVerInformacion.setLayoutX(840);
        btnVerInformacion.setOnAction(e -> {
            llamarVerInformacion(colaboracion.getIdColaboracion());
        });

        objeto.getChildren().add(btnVerInformacion);
        }

        objeto.getChildren().add(lblNombreColaboracion);
        objeto.getChildren().add(lblEstadoColaboracion);    
        contenedor.getChildren().add(objeto);
        nextYPosition += 103;
    
    }

    private void llamarVerInformacion(int idColaboracion){
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLVerInformacionColaboracion.fxml"));
            Parent root = cargarObjeto.load();
            FXMLVerInformacionColaboracionController verInformacion = cargarObjeto.getController();
            verInformacion.inicializarValores(idColaboracion);
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Ver ibnformacion de colaboracion");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.showAndWait();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    private void llamarVer(int idColaboracion){
        try{
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLVerInformacionColaboracion.fxml"));
            Parent root = cargarObjeto.load();
            FXMLVerInformacionColaboracionController verInformacion = cargarObjeto.getController();
            verInformacion.inicializarValores(idColaboracion);
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Colaboracion");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.showAndWait();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    @FXML
    private void btnClicNuevaColab(ActionEvent event) {
    }
}
