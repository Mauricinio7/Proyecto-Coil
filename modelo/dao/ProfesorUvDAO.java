package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.ProfesorUv;
import coilvic.utilidades.Constantes;

public class ProfesorUvDAO {
    
    public static HashMap<String, Object> obtenerProfesorUvPorId(Integer idProfesorUv) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT "
                        + "nombre,"
                        + "correo, "
                        + "no_personal, "
                        + "id_profesoruv, "
                        + "region_id_region "
                        + " FROM profesoruv "
                        + "WHERE id_profesoruv = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProfesorUv);
                ResultSet resultado = prepararSentencia.executeQuery();
                ProfesorUv profesorUv = null;
                if (resultado.next()) {
                    profesorUv = new ProfesorUv();
                    profesorUv.setNombre(resultado.getString("nombre"));
                    profesorUv.setCorreo(resultado.getString("correo"));
                    profesorUv.setNoPersonal(resultado.getString("no_personal"));
                    profesorUv.setIdProfesorUv(resultado.getInt("id_profesoruv"));
                    profesorUv.setIdRegion(resultado.getInt("region_id_region"));
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("Profesor", profesorUv);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
                e.printStackTrace();
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }
    public static HashMap<String, Object> obtenerNombreProfesorPorId(int idProfesor){
        HashMap<String, Object> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT nombre ");
            consulta.append("FROM profesoruv ");
            consulta.append("WHERE id_profesoruv = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setInt(1, idProfesor);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()){
                respuesta.put("nombreProfesor", resultado.getString("nombre"));
            }
        }catch(SQLException errorSql){
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
            errorSql.printStackTrace();
        }
        return respuesta;
    }

}
