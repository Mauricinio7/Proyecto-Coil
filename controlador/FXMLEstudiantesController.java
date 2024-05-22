/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coilvic.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.EstudiantesDAO;
import coilvic.modelo.pojo.Estudiante;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class FXMLEstudiantesController implements Initializable {

    @FXML
    private ScrollPane scPanePrincipal;
    @FXML
    private TextField txtBuscarNombre;
    Pane contenedor;
    private double nextYPosition;
    ObservableList<Estudiante> estudiantes;
    ArrayList<Estudiante> estudiantesBD;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarPanelScroll();
        cargarRegistroEstudiantes(5); //TODO Hardcode
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
    private void clicAnadir(ActionEvent event) {
    }

    @FXML
    private void clicSalir(ActionEvent event) {
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
            //TODO Si no hay estudiantes mensaje y cambio if(estudiantesBD = null){cambiar añadir y label};
            for (Estudiante estudiante : estudiantesBD) {
                crearFichaEstudiante(estudiante);
            }
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR);
        }
    }

    private void cargarEstudiantesPorNombre(String nombre){
        for (Estudiante estudiante : estudiantesBD) {
            if(estudiante.getNombre().equals(nombre)){
                crearFichaEstudiante(estudiante);
            }
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
    
}
