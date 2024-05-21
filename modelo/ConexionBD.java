package coilvic.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import coilvic.utilidades.Constantes;

public class ConexionBD {
    
    public static final String URI_CONEXION = 
            "jdbc:mysql://" + Constantes.HOSTNAME + 
            ":" + Constantes.PUERTO + 
            "/" + Constantes.NOMBRE_BD + 
            "?allowPublicKeyRetrieval=true&useSSL=false";
    
    public static Connection obtenerConexion() {
        Connection conexionBD = null;
        try {
            Class.forName(Constantes.DRIVER);
            conexionBD = DriverManager.getConnection(
                URI_CONEXION, 
                Constantes.USUARIO, 
                Constantes.PASSWORD);
        } 
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return conexionBD;
    }
}