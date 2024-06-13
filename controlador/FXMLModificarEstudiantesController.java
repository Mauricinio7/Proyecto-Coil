/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.EstudiantesDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Estudiante;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMLModificarEstudiantesController implements Initializable {

    @FXML
    private ScrollPane scPanePrincipal;
    @FXML
    private TextField txtBuscarNombre;
    Pane contenedor;
    private double nextYPosition;
    ObservableList<Estudiante> estudiantes;
    ArrayList<Estudiante> estudiantesBD;
    ArrayList<Estudiante> estudiantesBDEliminados = new ArrayList<>();
    private Colaboracion colaboracion;
    @FXML
    private Pane paneNoHayAlumnos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarColaboracion();
        cargarRegistroEstudiantes(colaboracion.getIdColaboracion());
    }    

        //TODO este es un metodo en lo que se pasa a la ventana la colab
    private void iniciarColaboracion(){
        HashMap<String, Object> consulta = ColaboracionDAO.obtenerColaboracionPorId(4); //TODO Hardcode
        colaboracion = (Colaboracion)consulta.get("Colaboracion");
    }

    @FXML
    private void clicBuscar(ActionEvent event) {
        if(!txtBuscarNombre.getText().equals("")){
            contenedor.getChildren().clear();
            cargarPanelScroll();
            cargarEstudiantesPorNombre(txtBuscarNombre.getText());
        }else {
            Utils.mostrarAlertaSimple("Error al filtrar", "No se escrito un nombre, favor de escribirlo", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        estudiantesBDEliminados.forEach(est -> System.out.println(est.getNombre() + ": " + est.getMatricula()));
        if(Utils.mostrarAlertaConfirmacion("Confirmacion", "¿Desea conservar los cambios realizados?", Alert.AlertType.INFORMATION, (Stage) scPanePrincipal.getScene().getWindow())){
            if(eliminarEstudiantes()){
                Utils.mostrarAlertaSimple("Éxito", "Se han eliminado los estudiantes correctamente", Alert.AlertType.INFORMATION, (Stage) scPanePrincipal.getScene().getWindow());
            }else{
                Utils.mostrarAlertaSimple("Error", "No se han podido guardar los datos", Alert.AlertType.ERROR, (Stage) scPanePrincipal.getScene().getWindow());
            }
        }
    }

    private boolean eliminarEstudiantes(){
        for (Estudiante estudiante : estudiantesBDEliminados) {
            HashMap<String, Object> cuenta = EstudiantesDAO.contarEstudianteColaboracion(estudiante.getIdEstudiante());
                if((boolean) cuenta.get("encontrado")){
                    System.out.println("Elimina la relacion");
                    HashMap<String, Object> respuesta = EstudiantesDAO.eliminarEstudianteRelacion(estudiante.getIdEstudiante(), colaboracion.getIdColaboracion());
                    if((boolean)respuesta.get(Constantes.KEY_ERROR)){ 
                        return false;
                    }
                }else{
                    System.out.println("Eliminar todo");
                    HashMap<String, Object> respuesta = EstudiantesDAO.eliminarEstudiante(estudiante.getIdEstudiante(), colaboracion.getIdColaboracion());
                    if((boolean)respuesta.get(Constantes.KEY_ERROR)){ 
                        return false;
                    }
                }
            }
            return true;
        }

    
    @FXML
    private void clicSalir(ActionEvent event) {
    }

    private void cargarPanelScroll(){
        nextYPosition = 0;
        contenedor = new Pane();
        contenedor.setPrefSize(500, 10);
        contenedor.setLayoutX(0);
        contenedor.setLayoutY(0);
        contenedor.setStyle("-fx-background-color: #FFFFFF;");
        scPanePrincipal.setContent(contenedor);
    }
    private void reiniciarPanelScroll(){
        contenedor.getChildren().clear();
        nextYPosition = 0;
        contenedor.setPrefSize(500, 10);
    }

    private void cargarRegistroEstudiantes(Integer idColaboracion){
        estudiantes = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = EstudiantesDAO.obtenerEstudiantes(idColaboracion);
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            estudiantesBD = (ArrayList) respuesta.get("estudiantes");
            if(estudiantesBD.size() < 1){
                paneNoHayAlumnos.setVisible(true);
            }else{
                cargarPanelScroll();
                for (Estudiante estudiante : estudiantesBD) {
                    crearFichaEstudiante(estudiante);
                }
            }
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR);
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
            Utils.mostrarAlertaSimple("Sin resultados", "No se han encontrado registros que coincidan con ese nombre", Alert.AlertType.WARNING);
        }
    }

        private void crearFichaEstudiante(Estudiante estudiante){
    
        Pane objeto = new Pane();
        objeto.setPrefSize(500, 120);
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

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        btnEliminar.setPrefSize(100, 40);
        btnEliminar.setLayoutY(40);
        btnEliminar.setLayoutX(370);
        btnEliminar.setOnAction(e -> {
            if(Utils.mostrarAlertaConfirmacion("Confirmación", "¿Seguro que deseas eliminar este estudiante?", Alert.AlertType.INFORMATION)){
                System.out.println("Se ha eliminado el estudiante: " + estudiante.getNombre());
                eliminarDeLista(estudiante.getMatricula());
            }
        });
        
        objeto.getChildren().add(lblNombreEstudiante);
        objeto.getChildren().add(lblMatriculaEstudiante);
        objeto.getChildren().add(lblCorreoEstudiante);
        objeto.getChildren().addAll(btnEliminar);
    
        contenedor.getChildren().add(objeto);
        nextYPosition += 123;
        contenedor.setPrefHeight(nextYPosition);
    
    }

    private void eliminarDeLista(String matricula){
        Optional<Estudiante> estudianteAEliminar = estudiantesBD.stream()
                .filter(estudiante -> estudiante.getMatricula().equals(matricula))
                .findFirst();

        estudianteAEliminar.ifPresent(estudiante -> {
            estudiantesBD.remove(estudiante);
            estudiantesBDEliminados.add(estudiante);
        });

        estudiantesBD.removeAll(estudiantesBDEliminados);
        reiniciarPanelScroll();
        for (Estudiante estudiante : estudiantesBD) {
            crearFichaEstudiante(estudiante);
        }
    }
    
}
