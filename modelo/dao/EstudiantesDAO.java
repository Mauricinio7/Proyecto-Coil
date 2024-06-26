package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.Estudiante;
import coilvic.utilidades.Constantes;

public class EstudiantesDAO {

    public static HashMap<String, Object> obtenerEstudiantes(Integer idColaboracion){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR,  true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT e.nombre, e.matricula, e.correo, e.id_Estudiante, ec.Colaboracion_id_colaboracion AS idColaboracion " +
                "FROM estudiante e " +
                "INNER JOIN colaboracion_has_estudiante ec ON e.id_Estudiante = ec.estudiante_id_Estudiante " +
                "WHERE ec.Colaboracion_id_colaboracion = ? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Estudiante> estudiantes = new ArrayList();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setCorreo(resultado.getString("correo"));
                    estudiante.setIdEstudiante(resultado.getInt("id_Estudiante"));
                    estudiante.setIdColaboracion(resultado.getInt("idColaboracion"));

                    estudiantes.add(estudiante);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("estudiantes", estudiantes);
                conexionBD.close();
            }catch (SQLException e){
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        }else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }  

    public static HashMap<String, Object> comprobarExistenciaEstudiante(String matricula){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR,  true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT COUNT(*) as count, id_Estudiante FROM estudiante WHERE matricula = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, matricula);
                ResultSet resultado = prepararSentencia.executeQuery();
                respuesta.put(Constantes.KEY_ERROR, false);
                if(resultado.next()){
                    int count = resultado.getInt("count");
                    if(count > 0){
                        respuesta.put(Constantes.KEY_ERROR, false);
                        respuesta.put("encontrado", true);
                        respuesta.put("id_Estudiante", resultado.getInt("id_Estudiante"));
                    } else {
                        respuesta.put(Constantes.KEY_ERROR, false);
                        respuesta.put("encontrado", false);
                    }
                }
                conexionBD.close();
            }catch (SQLException e){
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        }else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }  

    public static HashMap<String, Object> comprobarExistenciaEstudianteColaboracion(String matricula, Colaboracion colaboracion){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR,  true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT COUNT(*) AS count " +
                "FROM colaboracion_has_estudiante " +
                "INNER JOIN estudiante ON estudiante.id_Estudiante = colaboracion_has_estudiante.estudiante_id_Estudiante " +
                "WHERE colaboracion_has_estudiante.Colaboracion_id_colaboracion = ? " +
                "AND estudiante.matricula = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, colaboracion.getIdColaboracion());
                prepararSentencia.setString(2, matricula);
                ResultSet resultado = prepararSentencia.executeQuery();
                respuesta.put(Constantes.KEY_ERROR, false);
                if(resultado.next()){
                    int count = resultado.getInt("count");
                    if(count > 0){
                        respuesta.put(Constantes.KEY_ERROR, false);
                        respuesta.put("encontrado", true);
                    } else {
                        respuesta.put(Constantes.KEY_ERROR, false);
                        respuesta.put("encontrado", false);
                    }
                }
                conexionBD.close();
            }catch (SQLException e){
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        }else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }
    
    

    public static HashMap<String, Object> guardarEstudiante(Estudiante estudiante, Colaboracion colaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                conexionBD.setAutoCommit(false);

                String sentencia = "INSERT INTO estudiante (nombre, matricula, correo) VALUES (?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
                prepararSentencia.setString(1, estudiante.getNombre());
                prepararSentencia.setString(2, estudiante.getMatricula());
                prepararSentencia.setString(3, estudiante.getCorreo());
                int filasAfectadasEstudiante = prepararSentencia.executeUpdate();

                ResultSet generatedKeys = prepararSentencia.getGeneratedKeys();
                int idEstudianteGenerado = 0;
                if (generatedKeys.next()) {
                    idEstudianteGenerado = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID del estudiante.");
                }
                generatedKeys.close();
                prepararSentencia.close();

                String sentencia2 = "INSERT INTO colaboracion_has_estudiante (estudiante_id_Estudiante, Colaboracion_id_colaboracion)" +
                "VALUES (?, ?)";
                PreparedStatement prepararSentencia2 = conexionBD.prepareStatement(sentencia2);
                prepararSentencia2.setInt(1, idEstudianteGenerado);
                prepararSentencia2.setInt(2, colaboracion.getIdColaboracion());
                int filasAfectadasEstudianteColaboracion = prepararSentencia2.executeUpdate();

                if (filasAfectadasEstudiante > 0 && filasAfectadasEstudianteColaboracion > 0) {
                    conexionBD.commit();
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Se ha añadido exitosamente");
                } else {
                    conexionBD.rollback();
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido guardar los datos");
                }
                conexionBD.close();
            } catch(SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> asociarEstudianteColaboracion(int idEstudiante, Colaboracion colaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO colaboracion_has_estudiante (estudiante_id_Estudiante, Colaboracion_id_colaboracion)" +
                "VALUES (?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEstudiante);
                prepararSentencia.setInt(2, colaboracion.getIdColaboracion());
                int filasAfectadasEstudianteColaboracion = prepararSentencia.executeUpdate();

                if (filasAfectadasEstudianteColaboracion > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Se ha añadido exitosamente");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido guardar los datos");
                }
                conexionBD.close();
            } catch(SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> contarEstudianteColaboracion(int idEstudiante){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR,  true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT COUNT(*) AS count FROM colaboracion_has_estudiante WHERE estudiante_id_Estudiante = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                respuesta.put(Constantes.KEY_ERROR, false);
                if(resultado.next()){
                    int count = resultado.getInt("count");
                    if(count > 1){
                        respuesta.put(Constantes.KEY_ERROR, false);
                        respuesta.put("encontrado", true);
                    } else {
                        respuesta.put(Constantes.KEY_ERROR, false);
                        respuesta.put("encontrado", false);
                    }
                }
                conexionBD.close();
            }catch (SQLException e){
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        }else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }

    public static HashMap<String, Object> eliminarEstudianteRelacion(int idEstudiante, int idColaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {    
                String sentencia = "DELETE From colaboracion_has_estudiante where estudiante_id_Estudiante = ? AND Colaboracion_id_colaboracion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEstudiante);
                prepararSentencia.setInt(2, idColaboracion);
                int filasAfectadas = prepararSentencia.executeUpdate();
    
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Se ha eliminado el estudiante correctamente");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, hubo un error al eliminar el estudiante, por favor revisa la información");
                }
                conexionBD.close();
    
            } catch (SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "Error en la conexión");
        }
    
        return respuesta;
    }

    public static HashMap<String, Object> eliminarEstudiante(int idEstudiante, int idColaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {    
                conexionBD.setAutoCommit(false);
                String sentenciaRelacion= "DELETE From colaboracion_has_estudiante where estudiante_id_Estudiante = ? AND Colaboracion_id_colaboracion = ?";
                PreparedStatement prepararSentenciaRelacion = conexionBD.prepareStatement(sentenciaRelacion);
                prepararSentenciaRelacion.setInt(1, idEstudiante);
                prepararSentenciaRelacion.setInt(2, idColaboracion);
                int filasAfectadasRelacion = prepararSentenciaRelacion.executeUpdate();

                String sentenciaEstudiante = "DELETE From estudiante where id_Estudiante = ?";
                PreparedStatement prepararSentenciaEstudiante = conexionBD.prepareStatement(sentenciaEstudiante);
                prepararSentenciaEstudiante.setInt(1, idEstudiante);
                int filasAfectadasEstudiante = prepararSentenciaEstudiante.executeUpdate();
    
                if (filasAfectadasRelacion > 0 && filasAfectadasEstudiante > 0) {
                    conexionBD.commit();
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Se ha eliminado el estudiante correctamente");
                } else {
                    conexionBD.rollback();
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, hubo un error al eliminar el estudiante, por favor revisa la información");
                }
                conexionBD.close();
    
            } catch (SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "Error en la conexión");
        }
    
        return respuesta;
    }

   
}


