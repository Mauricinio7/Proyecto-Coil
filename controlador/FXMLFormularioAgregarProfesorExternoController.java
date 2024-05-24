package coilvic.controlador;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.pojo.ProfesorExterno;
import coilvic.observador.ObservadorProfesorExterno;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FXMLFormularioAgregarProfesorExternoController implements Initializable {

    private ObservadorProfesorExterno observadorProfesorExterno;
    private ProfesorExterno profesorExterno;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfIdioma;
    @FXML
    private TextField tfInstitucion;
    @FXML
    private TextField tfPais;
    @FXML
    private TextField tfTelefono;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        limitarCaracteresTextField(tfCorreo, 50);
        limitarCaracteresTextField(tfIdioma, 20);
        limitarCaracteresTextField(tfInstitucion, 100);
        limitarCaracteresTextField(tfNombre, 100);
        limitarCaracteresTextField(tfPais, 30);
        limitarCaracteresTextField(tfTelefono, 15);
    }    

    private void obtenerDatosProfesorExterno() {
        profesorExterno = new ProfesorExterno();
        profesorExterno.setNombre(tfNombre.getText());
        profesorExterno.setCorreo(tfCorreo.getText());
        profesorExterno.setIdioma(tfIdioma.getText());
        profesorExterno.setInstitucion(tfInstitucion.getText());
        profesorExterno.setPais(tfPais.getText());
        profesorExterno.setTelefono(tfTelefono.getText());
    }

    private void guardarProfesorExterno() {
        HashMap<String, Object> respuesta = ProfesorExternoDAO.registrarProfesorExterno(profesorExterno);
        if (!(Boolean) respuesta.get(Constantes.KEY_ERROR)) {
            Utils.mostrarAlertaSimple(null, ""+respuesta.get(Constantes.KEY_MENSAJE), AlertType.INFORMATION);
            observadorProfesorExterno.profesorGuardado();
        } else {
            Utils.mostrarAlertaSimple("Error", "No se han podido cargar los datos", AlertType.ERROR);
        }
    }

    private Boolean camposVacios() {
        return tfNombre.getText().isEmpty() ||
         tfCorreo.getText().isEmpty() || 
         tfIdioma.getText().isEmpty() || 
         tfInstitucion.getText().isEmpty() || 
         tfPais.getText().isEmpty() || 
         tfTelefono.getText().isEmpty();
    }

    private Boolean datosValidos() {
        return tfTelefono.getText().matches("[0-9]{10}") && 
        tfCorreo.getText().matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        if (camposVacios()) {
            Utils.mostrarAlertaSimple
            ("Rellenar campos obligatorios", "Se han dejado campos obligatorios vacíos", AlertType.WARNING);
        } else if (!datosValidos()) {
            Utils.mostrarAlertaSimple
            ("Datos inválidos", "Por favor, verifique los datos", AlertType.WARNING);
        } else {
            obtenerDatosProfesorExterno();
            if (idProfesorYaRegistrado() != null) {
                Utils.mostrarAlertaSimple("", "El profesor ya se encuentra registrado", AlertType.WARNING);
                return;
            } else {
                guardarProfesorExterno();
            }
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
    }
    
    private void limitarCaracteresTextField(TextField textField, int maxLength) {
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() > maxLength) {
                textField.setText(oldValue);
            }
        });
    }

    public void inicializarValores(ObservadorProfesorExterno observadorProfesorExterno) {
        this.observadorProfesorExterno = observadorProfesorExterno;
    }

    private Integer idProfesorYaRegistrado() {
        HashMap<String, Object> respuesta = ProfesorExternoDAO.obtenerIdProfesor(profesorExterno);
        if (!(Boolean) respuesta.get(Constantes.KEY_ERROR)) {
            return (Integer) respuesta.get("idProfesorExterno");
        }
        return null;
    }
}
