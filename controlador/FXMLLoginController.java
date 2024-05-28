package coilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.CoilVic;
import coilvic.modelo.dao.LoginDAO;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;

public class FXMLLoginController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnContinuar(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String contrasena = pfUsuario.getText();
        Utils.irVistaPorComponent(tfUsuario,"../vista/FXMLVistaProfesor.fxml", null);
        //Utils.irVistaPorComponent(tfUsuario,"../vista/FXMLVistaAdmin.fxml", null);
        if (validarCampos()) {
            HashMap<String, Object> respuesta = LoginDAO.iniciarSesion(usuario, contrasena);
            if( !(boolean) respuesta.get(Constantes.KEY_ERROR)) {

                //PASAR A LA SIGUIENTE VENTANA
                Utils.mostrarAlertaSimple("Prueba", "Prueba", AlertType.INFORMATION); //Eliminar
            } else {
                Utils.mostrarAlertaSimple("Error", ""+respuesta.get(Constantes.KEY_MENSAJE),AlertType.ERROR);
            }
        }
    }
    
    private boolean validarCampos() {
        if (pfUsuario.getText().isEmpty() || tfUsuario.getText().isEmpty()) {
            Utils.mostrarAlertaSimple("Advertencia", "Se han dejado campos obligatorios vacíos", AlertType.WARNING);
            return false;
        }
        return true;
    }
    public void irPantallaAdministrador(){
        try{
            Stage stagePrincipal = (Stage)tfUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(CoilVic.class.getResource("../vista/FXMLVistaAdmin.fxml"));
            Parent root =loader.load();
            FXMLVistaAdminController pantallaPrincipalControlador = loader.getController();
            pantallaPrincipalControlador.inicializarValores();
            Scene nuevaEscena = new Scene(root);
            stagePrincipal.setScene(nuevaEscena);
            stagePrincipal.setTitle("Pantalla Administrador");
            stagePrincipal.show();
        }catch(IOException error){

        }
    }
    public void irPantallaProfesor(){
        try{
            Stage stagePrincipal = (Stage)tfUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(CoilVic.class.getResource("../vista/FXMLVistaProfesor.fxml"));
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
