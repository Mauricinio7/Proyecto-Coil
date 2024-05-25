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
import coilvic.utilidades.Constantes;

public class ColaboracionDAO {
    
    public static HashMap<String, Object> guardarColaboracion(Colaboracion colaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
            String sentencia = "INSERT INTO colaboracion ("
                    + "estado, fecha_inicio, fecha_fin, idioma, nombre, objetivo_general,"
                    + " tema_interes, periodo, no_estudiantes_externos, ProfesorUV_idProfesorUV,"
                    + " Profesor_externo_idProfesorExterno, Asignatura_idAsignatura, Region_idRegion,"
                    + " Departamento_idDepartamento) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, colaboracion.getEstado());
            prepararSentencia.setString(2, colaboracion.getFechaInicio());
            prepararSentencia.setString(3, colaboracion.getFechaFin());
            prepararSentencia.setString(4, colaboracion.getIdioma());
            prepararSentencia.setString(5, colaboracion.getNombre());
            prepararSentencia.setString(6, colaboracion.getObjetivoGeneral());
            prepararSentencia.setString(7, colaboracion.getTemaInteres());
            prepararSentencia.setString(8, colaboracion.getPeriodo());
            prepararSentencia.setInt(9, colaboracion.getNoEstudiantesExternos());
            prepararSentencia.setInt(10, colaboracion.getIdProfesorUV());
            prepararSentencia.setInt(11, colaboracion.getIdProfesorExterno());
            prepararSentencia.setInt(12, colaboracion.getIdAsignatura());
            prepararSentencia.setInt(13, colaboracion.getIdRegion());
            prepararSentencia.setInt(14, colaboracion.getIdDepartamento());
            int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Colaboración registrada con éxito");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Hubo un error al guardar los datos de la colaboración"
                            + ", favor de verificar");
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

    public static HashMap<String, Object> obtenerColaboracionPorId(Integer idColaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT estado,"
                        + " fecha_inicio,"
                        + " fecha_fin,"
                        + " idioma,"
                        + " nombre,"
                        + " objetivo_general,"
                        + " tema_interes,"
                        + " periodo,"
                        + " no_estudiantes_externos,"
                        + " id_colaboracion,"
                        + " ProfesorUV_idProfesorUV,"
                        + " Profesor_externo_idProfesorExterno,"
                        + " Asignatura_idAsignatura,"
                        + " Region_idRegion,"
                        + " Departamento_idDepartamento"
                        + " FROM colaboracion "
                        + "WHERE id_colaboracion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultado = prepararSentencia.executeQuery();
                Colaboracion colaboracion = null;
                if (resultado.next()) {
                    colaboracion = new Colaboracion();
                    colaboracion.setEstado(resultado.getString("estado"));
                    colaboracion.setFechaInicio(resultado.getString("fecha_inicio"));
                    colaboracion.setFechaFin(resultado.getString("fecha_fin"));
                    colaboracion.setIdioma(resultado.getString("idioma"));
                    colaboracion.setNombre(resultado.getString("nombre"));
                    colaboracion.setObjetivoGeneral(resultado.getString("objetivo_general"));
                    colaboracion.setTemaInteres(resultado.getString("tema_interes"));
                    colaboracion.setPeriodo(resultado.getString("periodo"));
                    colaboracion.setNoEstudiantesExternos(resultado.getInt("no_estudiantes_externos"));
                    colaboracion.setIdColaboracion(resultado.getInt("id_colaboracion"));
                    colaboracion.setIdProfesorUV(resultado.getInt("ProfesorUV_idProfesorUV"));
                    colaboracion.setIdProfesorExterno(resultado.getInt("Profesor_externo_idProfesorExterno"));
                    colaboracion.setIdAsignatura(resultado.getInt("Asignatura_idAsignatura"));
                    colaboracion.setIdRegion(resultado.getInt("Region_idRegion"));
                    colaboracion.setIdDepartamento(resultado.getInt("Departamento_idDepartamento"));
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("Colaboracion", colaboracion);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerColaboracionesConcluidasPorPeriodo(String periodoSeleccionado) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT estado,"
                        + " idioma,"
                        + " nombre,"
                        + " objetivo_general,"
                        + " tema_interes,"
                        + " periodo,"
                        + " no_estudiantes_externos,"
                        + " id_colaboracion,"
                        + " ProfesorUV_idProfesorUV,"
                        + " Profesor_externo_idProfesorExterno,"
                        + " Asignatura_idAsignatura,"
                        + " Region_idRegion,"
                        + " Departamento_idDepartamento"
                        + " FROM colaboracion "
                        + "WHERE periodo = ? AND estado = 'Concluida'";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, periodoSeleccionado);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
                while (resultado.next()) {
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setEstado(resultado.getString("estado"));
                    colaboracion.setFechaInicio(resultado.getString("fecha_inicio"));
                    colaboracion.setFechaFin(resultado.getString("fecha_fin"));
                    colaboracion.setIdioma(resultado.getString("idioma"));
                    colaboracion.setNombre(resultado.getString("nombre"));
                    colaboracion.setObjetivoGeneral(resultado.getString("objetivo_general"));
                    colaboracion.setTemaInteres(resultado.getString("tema_interes"));
                    colaboracion.setPeriodo(resultado.getString("periodo"));
                    colaboracion.setNoEstudiantesExternos(resultado.getInt("no_estudiantes_externos"));
                    colaboracion.setIdColaboracion(resultado.getInt("id_colaboracion"));
                    colaboracion.setIdProfesorUV(resultado.getInt("ProfesorUV_idProfesorUV"));
                    colaboracion.setIdProfesorExterno(resultado.getInt("Profesor_externo_idProfesorExterno"));
                    colaboracion.setIdAsignatura(resultado.getInt("Asignatura_idAsignatura"));
                    colaboracion.setIdRegion(resultado.getInt("Region_idRegion"));
                    colaboracion.setIdDepartamento(resultado.getInt("Departamento_idDepartamento"));
                    colaboraciones.add(colaboracion);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("Colaboraciones", colaboraciones);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }

}
