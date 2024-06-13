package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Colaboracion;
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

    public static HashMap<String, Object> obtenerProfesoresPeriodoConcluido(String periodoSeleccionado) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT p.id_profesoruv, p.nombre, p.correo, p.no_personal, r.nombre "
                        + "AS region_nombre FROM profesoruv p "
                        + "JOIN colaboracion c "
                        + "ON p.id_profesoruv = c.profesoruv_id_profesoruv "
                        + "JOIN region r "
                        + "ON p.region_id_region = r.id_region "
                        + "WHERE c.estado = 'Finalizada completamente' AND c.periodo = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, periodoSeleccionado);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ProfesorUv> profesores = new ArrayList<>();
                while (resultado.next()) {
                    ProfesorUv profesorUv = new ProfesorUv();
                    profesorUv.setIdProfesorUv(resultado.getInt("id_profesoruv"));
                    profesorUv.setNombre(resultado.getString("nombre"));
                    profesorUv.setCorreo(resultado.getString("correo"));
                    profesorUv.setNoPersonal(resultado.getString("no_personal"));
                    profesorUv.setNombreRegion(resultado.getString("region_nombre"));
                    profesores.add(profesorUv);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("profesoresuv", profesores);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }
    public static HashMap<String, Object> obtenerCorreoPorIdProfesor(int idProfesoruv){
        HashMap<String, Object> respuesta = new  HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT correo ");
            consulta.append("FROM profesoruv ");
            consulta.append("WHERE id_profesoruv = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setInt(1, 1);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()){
                respuesta.put("correo", resultado.getString("correo"));
            }
        }catch(SQLException errorSql){
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
            errorSql.printStackTrace();
        }
        return respuesta;
    }

}
