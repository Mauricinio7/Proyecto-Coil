package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;


import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.OfertaColaboracionExterna;
import coilvic.utilidades.Constantes;

public class OfertaColaboracionExternaDAO {
   
    public static HashMap<String, Boolean> guardarOferta(OfertaColaboracionExterna nuevaOferta){
        HashMap<String, Boolean> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("INSERT INTO oferta_colaboracion_externa ");
            consulta.append("(nombre, objetivo_general, idioma, periodo, tema_interes, ");
            consulta.append("asignatura, profesor_externo_id_profesor_externo) ");
            consulta.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement prepararSentencia = conexionDB.prepareStatement(consulta.toString());
            prepararSentencia.setString(1, nuevaOferta.getNombre());
            prepararSentencia.setString(2, nuevaOferta.getObjetivoGeneral());
            prepararSentencia.setString(3, nuevaOferta.getIdioma());
            prepararSentencia.setString(4, nuevaOferta.getPeriodo());
            prepararSentencia.setString(5, nuevaOferta.getTemaInteres());
            prepararSentencia.setString(6, nuevaOferta.getAsignatura());
            prepararSentencia.setInt(7, nuevaOferta.getIdProfesorExterno());
            int filasAfectada =prepararSentencia.executeUpdate();
            if(filasAfectada > 0){
                respuesta.put("ofertaColaboracion", true);
            }else{
                respuesta.put(Constantes.KEY_ERROR, false);
            } 
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, false);
        return respuesta;
    }
}
