package coilvic.utilidades;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import coilvic.controlador.FXMLVistaProfesorController;
import coilvic.maincoilvic.CoilVic;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Utils {
    
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
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
    
    public static void irVistaPorComponent(TextField btVista, String path, List<Object> lista){
        try{
            Stage stagePrincipal = (Stage)btVista.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(CoilVic.class.getResource(path));
            Parent root =loader.load();
            FXMLVistaProfesorController pantallaPrincipalControlador = loader.getController();
            pantallaPrincipalControlador.inicializarValores();
            Scene nuevaEscena = new Scene(root);
            stagePrincipal.setScene(nuevaEscena);
            stagePrincipal.setTitle("Pantalla Profesor");
            stagePrincipal.show();
        }catch(IOException error){

        }
    }
}