package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;

public class OfertaColaboracionDAO {
    public HashMap<String, Boolean> guardarOferta(){
        HashMap<String, Boolean> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = 
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        return respuesta;
    }
}
