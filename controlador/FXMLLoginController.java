package coilvic.controlador;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.LoginDAO;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
}
