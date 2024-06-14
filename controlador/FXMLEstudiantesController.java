package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.EstudiantesDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Estudiante;
import coilvic.observador.ObservadorEstudiante;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMLEstudiantesController implements Initializable, ObservadorEstudiante {

    @FXML
    private ScrollPane scPanePrincipal;
    @FXML
    private TextField txtBuscarNombre;
    Pane contenedor;
    private double nextYPosition;
    ObservableList<Estudiante> estudiantes;
    ArrayList<Estudiante> estudiantesBD;
    private Colaboracion colaboracion;
    @FXML
    private Pane paneNoHayAlumnos;
    @FXML
    private Button btnAnadir;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void inicializarValores(Colaboracion colaboracion){
        this.colaboracion = colaboracion;

        cargarRegistroEstudiantes(colaboracion.getIdColaboracion());
    }
    

    @FXML
    private void clicBuscar(ActionEvent event) {
        if(!txtBuscarNombre.getText().equals("")){
            contenedor.getChildren().clear();
            cargarPanelScroll();
            cargarEstudiantesPorNombre(txtBuscarNombre.getText());
        }else {
            Utils.mostrarAlertaSimple("Error al filtrar", "No se ha escrito un nombre, favor de escribirlo", Alert.AlertType.WARNING, (Stage) scPanePrincipal.getScene().getWindow());
        }
    }

    @FXML
    private void clicAnadir(ActionEvent event) {
        irRegistrarEstudiante();
    }

    private void irRegistrarEstudiante(){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLRegistrarEstudiante.fxml");
            Parent root = loader.load();
            FXMLRegistrarEstudianteController controlador = loader.getController();
            controlador.inicializarValores(colaboracion, this);
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Registrar Estudiantes");
            escenario.initOwner(((Node) btnAnadir).getScene().getWindow());
            escenario.initModality(Modality.APPLICATION_MODAL);
            
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    @FXML
    private void clicSalir(ActionEvent event) {
        ((Stage) scPanePrincipal.getScene().getWindow()).close();
    }

    private void cargarPanelScroll(){
        nextYPosition = 0;
        contenedor = new Pane();
        contenedor.setPrefSize(470, 10);
        contenedor.setLayoutX(0);
        contenedor.setLayoutY(0);
        contenedor.setStyle("-fx-background-color: #FFFFFF;");
        scPanePrincipal.setContent(contenedor);
    }

    private void cargarRegistroEstudiantes(Integer idColaboracion){
        estudiantes = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = EstudiantesDAO.obtenerEstudiantes(idColaboracion);
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            estudiantesBD = (ArrayList) respuesta.get("estudiantes");
            if(estudiantesBD.size() < 1){
                paneNoHayAlumnos.setVisible(true);
                btnAnadir.setVisible(false);
            }else{
                cargarPanelScroll();
                for (Estudiante estudiante : estudiantesBD) {
                    crearFichaEstudiante(estudiante);
                }
            }
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR, (Stage) scPanePrincipal.getScene().getWindow());
        }
    }

    private void cargarEstudiantesPorNombre(String nombre){
        boolean band = false;
        for (Estudiante estudiante : estudiantesBD) {
            if(estudiante.getNombre().equals(nombre)){
                crearFichaEstudiante(estudiante);
                band = true;
            }
        }
        if(!band){
            for (Estudiante estudiante : estudiantesBD) {
                crearFichaEstudiante(estudiante);
            }
            Utils.mostrarAlertaSimple("Sin resultados", "No se han encontrado registros que coincidan con ese nombre", Alert.AlertType.WARNING, (Stage) scPanePrincipal.getScene().getWindow());
        }
    }

        private void crearFichaEstudiante(Estudiante estudiante){
    
        Pane objeto = new Pane();
        objeto.setPrefSize(470, 120);
        objeto.setLayoutX(0);
        objeto.setLayoutY(nextYPosition); 
        objeto.setStyle("-fx-background-color: #303030;");
    
        Label lblNombreEstudiante = new Label("Nombre: " + estudiante.getNombre());
        lblNombreEstudiante.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 17px; -fx-font-weight: bold;");
        lblNombreEstudiante.setLayoutY(15);
        lblNombreEstudiante.setLayoutX(30);

        Label lblMatriculaEstudiante = new Label("Matricula: " + estudiante.getMatricula());
        lblMatriculaEstudiante.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 17px; -fx-font-weight: bold;");
        lblMatriculaEstudiante.setLayoutY(45);
        lblMatriculaEstudiante.setLayoutX(30);

        Label lblCorreoEstudiante = new Label("Correo: " + estudiante.getCorreo());
        lblCorreoEstudiante.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 17px; -fx-font-weight: bold;");
        lblCorreoEstudiante.setLayoutY(75);
        lblCorreoEstudiante.setLayoutX(30);
        
        objeto.getChildren().add(lblNombreEstudiante);
        objeto.getChildren().add(lblMatriculaEstudiante);
        objeto.getChildren().add(lblCorreoEstudiante);
    
        contenedor.getChildren().add(objeto);
        nextYPosition += 123;
        contenedor.setPrefHeight(nextYPosition);
    
    }

    @Override
    public void operacionExitosa(String tipoOperacion, String nombreEstudiante) {
        System.out.println("Operación: " + tipoOperacion);
        System.out.println("Estudiante: " + nombreEstudiante);
        if (contenedor != null && contenedor.getChildren() != null && !contenedor.getChildren().isEmpty()) {
            contenedor.getChildren().clear();
            cargarPanelScroll();
            cargarRegistroEstudiantes(colaboracion.getIdColaboracion());
        }else{
            cargarPanelScroll();
            cargarRegistroEstudiantes(colaboracion.getIdColaboracion());
        }
    }
    
}
