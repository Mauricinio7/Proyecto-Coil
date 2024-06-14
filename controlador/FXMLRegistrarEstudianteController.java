package coilvic.controlador;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.EstudiantesDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Estudiante;
import coilvic.observador.ObservadorEstudiante;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLRegistrarEstudianteController implements Initializable {

    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtNombre;
    private Colaboracion colaboracion;
    private ObservadorEstudiante observador;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarValores(Colaboracion colaboracion, ObservadorEstudiante observador){
        this.colaboracion = colaboracion;
        this.observador = observador;
    }

    @FXML
    private void clicAceptar(ActionEvent event) {
        if(!(camposVacios())){
            if(!datosInvalidos()){
                HashMap<String, Object> consulta = EstudiantesDAO.comprobarExistenciaEstudiante(txtMatricula.getText());
                if(consulta == null){
                    guardarEstudiante();
                    observador.operacionExitosa("Agregar", txtNombre.getText());
                    System.out.println("No existe");
                    cerrarVentana();
                    return;
                }
                
                if((boolean)consulta.get(Constantes.KEY_ERROR)){
                    Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR, (Stage) txtCorreo.getScene().getWindow());
                }else {
                    if((boolean)consulta.get("encontrado")){
                        if(!probarExisteEstudianteColaboracion()){
                            asociarEstudiante((int) consulta.get("idEstudiante"));
                            observador.operacionExitosa("Asociar", txtNombre.getText());
                            cerrarVentana();
                        }else{
                            Utils.mostrarAlertaSimple("Error", "El estudiante ya se encuentra registrado en esta colaboración", Alert.AlertType.WARNING, (Stage) txtNombre.getScene().getWindow());
                            }
                    }else{
                        guardarEstudiante();
                        observador.operacionExitosa("Agregar", txtNombre.getText());
                        System.out.println("No existe");
                        cerrarVentana();
                    }
                }
            }else{
                Utils.mostrarAlertaSimple("Rellenar campos correctamente", "Se han introducido datos inválidos", Alert.AlertType.WARNING, (Stage) txtNombre.getScene().getWindow());
            }
        }else {
            Utils.mostrarAlertaSimple("Rellenar campos obligatorios", "Se han dejado campos obligatorios vacíos", Alert.AlertType.WARNING, (Stage) txtNombre.getScene().getWindow());
        }
        
    }

    private boolean camposVacios(){
        return (txtNombre.getText().equals("") || txtMatricula.getText().equals("") || txtCorreo.getText().equals(""));
    }

    private boolean datosInvalidos(){
        return !(txtCorreo.getText().matches("^[^@]+@[^@]+$"));
    }

    private boolean probarExisteEstudianteColaboracion(){
        HashMap<String, Object> consulta = EstudiantesDAO.comprobarExistenciaEstudianteColaboracion(txtMatricula.getText(), colaboracion);
        if(!((boolean) consulta.get(Constantes.KEY_ERROR))){
            return (boolean)consulta.get("encontrado");
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR, (Stage) txtCorreo.getScene().getWindow());
            return false;
        }
    }

    public void guardarEstudiante(){
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(txtNombre.getText());
        estudiante.setCorreo(txtCorreo.getText());
        estudiante.setMatricula(txtMatricula.getText());
        HashMap<String, Object> respuesta = EstudiantesDAO.guardarEstudiante(estudiante, colaboracion);
        if(!((boolean) respuesta.get(Constantes.KEY_ERROR))){
            Utils.mostrarAlertaSimple("Éxito", "Se ha añadido exitosamente", Alert.AlertType.INFORMATION, (Stage) txtNombre.getScene().getWindow());
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido guardar los datos.", Alert.AlertType.ERROR, (Stage) txtCorreo.getScene().getWindow());
        }

    }

    public void asociarEstudiante(int idEstudiante){
        HashMap<String, Object> respuesta = EstudiantesDAO.asociarEstudianteColaboracion(idEstudiante, colaboracion);
        if(!((boolean) respuesta.get(Constantes.KEY_ERROR))){
            Utils.mostrarAlertaSimple("Éxito", "Se ha añadido exitosamente", Alert.AlertType.INFORMATION, (Stage) txtNombre.getScene().getWindow());
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido guardar los datos.", Alert.AlertType.ERROR, (Stage) txtCorreo.getScene().getWindow());
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana(){
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
    
}
