package coilvic.utilidades;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import coilvic.CoilVic;
import coilvic.controlador.FXMLVistaProfesorController;
import coilvic.CoilVic;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Utils {
    
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.initModality(Modality.APPLICATION_MODAL);
        alerta.showAndWait();
    }
    
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipoAlerta, Stage stagePrincipal) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.initModality(Modality.APPLICATION_MODAL);
        alerta.initOwner(stagePrincipal);
        alerta.showAndWait();
    }
    
    public static Boolean mostrarAlertaConfirmacion(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        Optional<ButtonType> botonSeleccionado = alerta.showAndWait();
        return (botonSeleccionado.get() == ButtonType.OK);
    }

    public static Boolean mostrarAlertaConfirmacion(String titulo, String mensaje, Alert.AlertType tipoAlerta, Stage stagePrincipal) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.initOwner(stagePrincipal);
        ButtonType btnAceptar = new ButtonType("Aceptar");
        ButtonType btnCancelar = new ButtonType("Cancelar");
        alerta.getButtonTypes().setAll(btnCancelar, btnAceptar);
        Optional<ButtonType> botonSeleccionado = alerta.showAndWait();
        return (botonSeleccionado.isPresent() && botonSeleccionado.get() == btnAceptar);
    }
    

    public static FXMLLoader obtenerLoader(String ruta){
        return new FXMLLoader(coilvic.CoilVic.class.getResource(ruta));
    }


    // ObservableList<String> obtenerAreasAcademicas(){
    //     ObservableList areasAcademicas = FXCollections.observableArrayList();
    //     areasAcademicas.add("Área Académica de Artes");
    //     areasAcademicas.add("Área Académica de Ciencias Biológicas y Agropecuarias");

    // }
}