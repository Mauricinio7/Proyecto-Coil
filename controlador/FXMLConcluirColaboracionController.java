package coilvic.controlador;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import coilvic.CoilVic;
import coilvic.modelo.dao.AsignaturaDAO;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.DepartamentoDAO;
import coilvic.modelo.dao.PlanProyectoDAO;
import coilvic.modelo.dao.ProfesorExternoDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.modelo.dao.RegionDAO;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.modelo.pojo.PlanProyecto;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Constantes;
import coilvic.utilidades.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLConcluirColaboracionController implements Initializable {

    @FXML
    private Label lblColaboracion;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblIdioma;
    @FXML
    private TextArea txtAreaObjetivo;
    @FXML
    private Label lblTemaInteres;
    @FXML
    private ImageView imgPlanProyecto;
    Colaboracion colaboracion;
    private File imagenSelecionada;
    private byte[] imagenPlanProyecto = null;
    @FXML
    private Label lblProfesorUV;
    @FXML
    private Label lblEstado;
    @FXML
    private Label lblProfesorExterno;
    @FXML
    private ScrollPane scPaneEvidencias;
    @FXML
    private DatePicker dateFechaFin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarValores(int idColaboracion){ 
        HashMap<String, Object> respuesta = ColaboracionDAO.obtenerColaboracionPorId(idColaboracion);
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            colaboracion = (Colaboracion) respuesta.get("Colaboracion");
            cargarDatosColaboracion();
        }else{
            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosColaboracion(){
        lblColaboracion.setText(lblColaboracion.getText() + colaboracion.getNombre());
        lblFechaInicio.setText(lblFechaInicio.getText() + colaboracion.getFechaInicio());
        lblIdioma.setText(lblIdioma.getText() + colaboracion.getIdioma());
        txtAreaObjetivo.setText(colaboracion.getObjetivoGeneral());
        lblTemaInteres.setText(lblTemaInteres.getText() + colaboracion.getTemaInteres());
        lblEstado.setText(lblEstado.getText() + colaboracion.getEstado());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        try {
            LocalDate fecha = LocalDate.parse(colaboracion.getFechaFin(), formatter);
            dateFechaFin.setValue(fecha);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido");
        }
        HashMap<String, Object> profesorUv = ProfesorUvDAO.obtenerNombreProfesorPorId(colaboracion.getIdProfesorUV());
        if(profesorUv.containsKey("nombreProfesor")){
            colaboracion.setNombreProfesorUV((String) profesorUv.get("nombreProfesor"));
            lblProfesorUV.setText(lblProfesorUV.getText() + colaboracion.getNombreProfesorUV());
        }
        HashMap<String, Object> profesorExternoUv = ProfesorExternoDAO.obtenerNombreProfesorExternoPorId(colaboracion.getIdProfesorExterno());
        if(profesorExternoUv.containsKey("nombreProfesorExterno")){
            colaboracion.setNombreProfesorExterno((String) profesorExternoUv.get("nombreProfesorExterno"));
            lblProfesorExterno.setText(lblProfesorExterno.getText() + colaboracion.getNombreProfesorExterno());
        }

         HashMap<String, Object> planProyecto = PlanProyectoDAO.obtenerPlanProyectoPorIdColaboracion(colaboracion.getIdColaboracion());
                if(planProyecto.containsKey("planProyecto")){
                    PlanProyecto nuevoPlanProyecto = (PlanProyecto) planProyecto.get("planProyecto");
                    if(planProyecto != null){
                        try {
                            ByteArrayInputStream inputPlan = new ByteArrayInputStream(nuevoPlanProyecto.getArchivoAdjunto());
                            imagenPlanProyecto = nuevoPlanProyecto.getArchivoAdjunto();
                            Image image = new Image(inputPlan);
                            imgPlanProyecto.setImage(image);
                        } catch (NullPointerException ex) {
                            Utils.mostrarAlertaSimple("Error en la conexión", "No se han podido cargar los datos.", Alert.AlertType.ERROR);
                        }
                    }
                }else{
                    System.out.println("No se ha encontrado el plan del proyecto");
                }

    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        Stage stage = (Stage) lblColaboracion.getScene().getWindow();
            stage.close();
    }

    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if(validarCamposVacios()){
            Utils.mostrarAlertaSimple("Error", "No se han llenado todos los campos", Alert.AlertType.ERROR, (Stage) lblColaboracion.getScene().getWindow());
        }
        else if(validarCamposInvalidos()){
            Utils.mostrarAlertaSimple("Error", "Los campos no son válidos", Alert.AlertType.ERROR, (Stage) lblColaboracion.getScene().getWindow());
        }else{
            if(Utils.mostrarAlertaConfirmacion("Confirmar", "¿Está seguro que desea concluir esta colaboración?", Alert.AlertType.WARNING, (Stage) lblColaboracion.getScene().getWindow())){
                guardarCambios();
            } 
        }
    }

    private void guardarCambios(){
        LocalDate fechaFinLocalDate = dateFechaFin.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFin = fechaFinLocalDate.format(formatter);
        byte[] imagen = imagenPlanProyecto;
    
        if(imagenSelecionada != null){
            try {
                imagen = Files.readAllBytes(imagenSelecionada.toPath());
            } catch (IOException e) {
                Utils.mostrarAlertaSimple("Error", "No se pudo leer la imagen seleccionada", Alert.AlertType.ERROR, (Stage) lblColaboracion.getScene().getWindow());
                return;
            }
        } 
    
        HashMap<String, Object> respuesta = ColaboracionDAO.guardarConcluirColaboracion(colaboracion.getIdColaboracion(), fechaFin,  imagen,"Finalizada en periodo");
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            Utils.mostrarAlertaSimple("Éxito", "La colaboración se ha concluido exitosamente", Alert.AlertType.INFORMATION, (Stage) lblColaboracion.getScene().getWindow());
            Stage stage = (Stage) lblColaboracion.getScene().getWindow();
            stage.close();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR, (Stage) lblColaboracion.getScene().getWindow());
        }
    }

    private boolean validarCamposInvalidos(){
            LocalDate fechaFin = dateFechaFin.getValue();
            LocalDate fechaInicio = LocalDate.parse(colaboracion.getFechaInicio());
            LocalDate fechaActual = LocalDate.now();
            if(fechaFin.isBefore(fechaInicio)){
                return true;
            } else if (fechaFin.isAfter(fechaActual)) {
                return true;
            } else if (fechaFin.isAfter(fechaInicio.plusMonths(6))) {
                return true;
            }else{
                return false;
            }
    }

    private boolean validarCamposVacios(){
        return (dateFechaFin.getValue() == null || imgPlanProyecto.getImage() == null);
    }

    @FXML
    private void btnClicSubirEvidencias(ActionEvent event) {
        try{
            ProfesorUv profesorUv = new ProfesorUv();
            profesorUv.setIdProfesorUv(colaboracion.getIdProfesorUV());
            Stage stageInformacion = new Stage();
            stageInformacion.initStyle(StageStyle.UTILITY);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLSubirEvidenciasExtemporaneas.fxml"));
            Parent root = cargarObjeto.load();
            FXMLSubirEvidenciasExtemporaneasController vistaSubirEvidencias = cargarObjeto.getController();
            vistaSubirEvidencias.inicializarValores(colaboracion.getIdColaboracion(), profesorUv);
            Scene nuevaScena = new Scene(root);
            stageInformacion.setTitle("Subir evidencias");
            stageInformacion.setScene(nuevaScena);
            stageInformacion.show();
            Stage stagePrincipal = (Stage)lblColaboracion.getScene().getWindow();
            stagePrincipal.close();
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    @FXML
    private void btnClicModificarPlan(ActionEvent event) {
        FileChooser dialogoSelecion = new FileChooser();
        dialogoSelecion.setTitle("Selecionar plan de proyecto");
        String etiquetaTipoDato = "Archivo de imagen(*.png, *.jpg, +.jpeg)";
        FileChooser.ExtensionFilter filtroImagenes = new FileChooser.ExtensionFilter(etiquetaTipoDato, "*.png", "*.jpg" , "*.jpeg");
        dialogoSelecion.getExtensionFilters().add(filtroImagenes);
        Stage escenarioActual = (Stage) lblColaboracion.getScene().getWindow();
        imagenSelecionada = dialogoSelecion.showOpenDialog(escenarioActual);
        if(imagenSelecionada != null){
            mostrarPlanProyecto(imagenSelecionada);
        }
    }

        private void mostrarPlanProyecto(File foto){
        try{
            BufferedImage buffer = ImageIO.read(foto);
            Image imageFoto = SwingFXUtils.toFXImage(buffer, null);
            imgPlanProyecto.setImage(imageFoto);
            
        }catch (IOException e){
            Utils.mostrarAlertaSimple("Error", "Lo sentimos no se pudo cargar la imagen seleccionada", Alert.AlertType.ERROR, (Stage) lblColaboracion.getScene().getWindow());
        }
    }

    @FXML
    private void btnClicModificarEstudiantes(ActionEvent event) {
        llamarModificarEstudiantes();
    }

    private void llamarModificarEstudiantes(){
        try{
            Stage stageModificarEstudiantes = new Stage();
            stageModificarEstudiantes.initStyle(StageStyle.UTILITY);
            Stage primaryStage = (Stage) lblColaboracion.getScene().getWindow();
            stageModificarEstudiantes.initOwner(primaryStage);
            stageModificarEstudiantes.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader cargarObjeto = new FXMLLoader(CoilVic.class.getResource("vista/FXMLModificarEstudiantes.fxml"));
            Parent root = cargarObjeto.load();
            FXMLModificarEstudiantesController modificarEstudiantes = cargarObjeto.getController();
            modificarEstudiantes.inicializarValores(colaboracion);
            Scene nuevaScena = new Scene(root);
            stageModificarEstudiantes.setTitle("Modificar estudiantes");
            stageModificarEstudiantes.setScene(nuevaScena);
            stageModificarEstudiantes.showAndWait();
        }catch(IOException error){
            error.printStackTrace();
        }
    }
    
}
