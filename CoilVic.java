package coilvic;

import java.io.IOException;

import coilvic.modelo.ConexionBD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CoilVic extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        ConexionBD conexion = new ConexionBD();
        if(conexion !=  null) System.out.println("Conexion Exitosa");
        //Parent root = FXMLLoader.load(getClass().getResource("vista/FXMLVistaAdmin.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("../vista/FXMLLogin.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("vista/FXMLRegistrarProfesorExterno.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("vista/FXMLVistaProfesor.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}