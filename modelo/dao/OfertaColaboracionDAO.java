package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.utilidades.Constantes;

public class OfertaColaboracionDAO {
    public HashMap<String, Boolean> guardarOferta(int idAsignatura, int idDepartamento, OfertaColaboracion nuevaOferta){
        HashMap<String, Boolean> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("INSERT INTO oferta_colaboracion ");
            consulta.append("(idioma, nombre, objetivo_general, periodo, tema_interes, ");
            consulta.append("ProfesorUV_idProfesorUV, idAsignatura, idDepartamento) ");
            consulta.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement prepararSentencia = conexionDB.prepareStatement(consulta.toString());
            prepararSentencia.setString(1, nuevaOferta.getIdioma());
            prepararSentencia.setString(2, nuevaOferta.getNombre());
            prepararSentencia.setString(3, nuevaOferta.getObjetivoGeneral());
            prepararSentencia.setString(4, nuevaOferta.getPeriodo());
            prepararSentencia.setString(5, nuevaOferta.getTemaInteres());
            prepararSentencia.setInt(6, nuevaOferta.getIdProfesor());
            prepararSentencia.setInt(7, idAsignatura);
            prepararSentencia.setInt(8, idDepartamento);
            int filasAfectada =prepararSentencia.executeUpdate();
            if(filasAfectada > 0){
                respuesta.put("guardado", true);
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
