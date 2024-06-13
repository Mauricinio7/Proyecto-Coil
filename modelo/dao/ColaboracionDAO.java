package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.PlanProyecto;
import coilvic.utilidades.Constantes;
import java.sql.Statement;

public class ColaboracionDAO {
    
    public static HashMap<String, Object> guardarColaboracion(Colaboracion colaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
            String sentencia = "INSERT INTO colaboracion ("
                    + "estado, fecha_inicio, fecha_fin, idioma, nombre, objetivo_general,"
                    + " tema_interes, periodo, no_estudiante_externo, profesoruv_id_profesoruv,"
                    + " profesor_externo_id_profesor_externo, asignatura_id_asignatura, region_id_region,"
                    + " departamento_id_departamento) "
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
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido guardar los datos.");
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

    public static HashMap<String, Object> asociarProfesorExternoColaboracion(int idColaboracion, int idProfesorExterno) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
            String sentencia = "UPDATE colaboracion SET profesor_externo_id_profesor_externo = ? WHERE id_colaboracion = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, idProfesorExterno);
            prepararSentencia.setInt(2, idColaboracion);
            int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Profesor externo asociado con éxito");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido guardar los datos.");
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
                        + " no_estudiante_externo,"
                        + " id_colaboracion,"
                        + " ProfesorUV_id_ProfesorUV,"
                        + " Profesor_externo_id_Profesor_Externo,"
                        + " Asignatura_id_Asignatura,"
                        + " Region_id_Region,"
                        + " Departamento_id_Departamento"
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
                    colaboracion.setNoEstudiantesExternos(resultado.getInt("no_estudiante_externo"));
                    colaboracion.setIdColaboracion(resultado.getInt("id_colaboracion"));
                    colaboracion.setIdProfesorUV(resultado.getInt("ProfesorUV_id_ProfesorUV"));
                    colaboracion.setIdProfesorExterno(resultado.getInt("Profesor_externo_id_Profesor_Externo"));
                    colaboracion.setIdAsignatura(resultado.getInt("Asignatura_id_Asignatura"));
                    colaboracion.setIdRegion(resultado.getInt("Region_id_Region"));
                    colaboracion.setIdDepartamento(resultado.getInt("Departamento_id_Departamento"));
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
                        + " no_estudiante_externo,"
                        + " id_colaboracion,"
                        + " profesoruv_id_profesoruv,"
                        + " profesor_externo_id_profesor_externo,"
                        + " asignatura_id_asignatura,"
                        + " region_id_region,"
                        + " departamento_id_departamento"
                        + " FROM colaboracion "
                        + "WHERE periodo = ? AND estado = 'Finalizada completamente'";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, periodoSeleccionado);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
                while (resultado.next()) {
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setEstado(resultado.getString("estado"));
                    colaboracion.setIdioma(resultado.getString("idioma"));
                    colaboracion.setNombre(resultado.getString("nombre"));
                    colaboracion.setObjetivoGeneral(resultado.getString("objetivo_general"));
                    colaboracion.setTemaInteres(resultado.getString("tema_interes"));
                    colaboracion.setPeriodo(resultado.getString("periodo"));
                    colaboracion.setNoEstudiantesExternos(resultado.getInt("no_estudiante_externo"));
                    colaboracion.setIdColaboracion(resultado.getInt("id_colaboracion"));
                    colaboracion.setIdProfesorUV(resultado.getInt("profesoruv_id_Profesoruv"));
                    colaboracion.setIdProfesorExterno(resultado.getInt("profesor_externo_id_profesor_externo"));
                    colaboracion.setIdAsignatura(resultado.getInt("asignatura_id_asignatura"));
                    colaboracion.setIdRegion(resultado.getInt("region_id_region"));
                    colaboracion.setIdDepartamento(resultado.getInt("departamento_id_departamento"));
                    colaboraciones.add(colaboracion);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("colaboraciones", colaboraciones);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerColaboracionesPorProfesor(int idProfesorUV) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT estado,"
                        + " nombre,"
                        + "estado,"
                        + "id_colaboracion "
                        + "FROM colaboracion "
                        + "WHERE profesoruv_id_profesoruv = ? "
                        + "AND estado <> 'cancelado'";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProfesorUV);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
                while (resultado.next()) {
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setEstado(resultado.getString("estado"));
                    colaboracion.setNombre(resultado.getString("nombre"));
                    colaboracion.setIdColaboracion(resultado.getInt("id_colaboracion"));
                    colaboraciones.add(colaboracion);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("colaboraciones", colaboraciones);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerColaboracionesPorProfesorConFiltro(int idProfesorUV, String filtro) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT estado,"
                        + " nombre,"
                        + "id_colaboracion "
                        + "FROM colaboracion "
                        + "WHERE profesoruv_id_profesoruv = ? AND estado = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProfesorUV);
                prepararSentencia.setString(2, filtro);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
                while (resultado.next()) {
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setEstado(filtro);
                    colaboracion.setNombre(resultado.getString("nombre"));
                    colaboracion.setIdColaboracion(resultado.getInt("id_colaboracion"));
                    colaboraciones.add(colaboracion);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("colaboraciones", colaboraciones);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }

    public static HashMap<String, Object> guardarColaboracionConPlanProyecto (Colaboracion colaboracion, PlanProyecto planProyecto) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                conexionBD.setAutoCommit(false);
                String sentenciaColaboracion = "INSERT INTO colaboracion ("
                        + "estado, fecha_inicio, fecha_fin, idioma, nombre, objetivo_general,"
                        + " tema_interes, periodo, no_estudiante_externo, profesoruv_id_profesoruv,"
                        + " asignatura_id_asignatura, region_id_region,"
                        + " departamento_id_departamento) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement prepararSentenciaColaboracion = conexionBD.prepareStatement(sentenciaColaboracion, Statement.RETURN_GENERATED_KEYS);
                prepararSentenciaColaboracion.setString(1, colaboracion.getEstado());
                prepararSentenciaColaboracion.setDate(2, Date.valueOf(colaboracion.getFechaInicio()));
                prepararSentenciaColaboracion.setDate(3, Date.valueOf(colaboracion.getFechaFin()));
                prepararSentenciaColaboracion.setString(4, colaboracion.getIdioma());
                prepararSentenciaColaboracion.setString(5, colaboracion.getNombre());
                prepararSentenciaColaboracion.setString(6, colaboracion.getObjetivoGeneral());
                prepararSentenciaColaboracion.setString(7, colaboracion.getTemaInteres());
                prepararSentenciaColaboracion.setString(8, colaboracion.getPeriodo());
                prepararSentenciaColaboracion.setInt(9, colaboracion.getNoEstudiantesExternos());
                prepararSentenciaColaboracion.setInt(10, colaboracion.getIdProfesorUV());
                prepararSentenciaColaboracion.setInt(11, colaboracion.getIdAsignatura());
                prepararSentenciaColaboracion.setInt(12, colaboracion.getIdRegion());
                prepararSentenciaColaboracion.setInt(13, colaboracion.getIdDepartamento());
                int filasAfectadasColaboracion = prepararSentenciaColaboracion.executeUpdate();
                if (filasAfectadasColaboracion > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Colaboración registrada con éxito");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido guardar los datos.");
                }
                ResultSet generatedKeys = prepararSentenciaColaboracion.getGeneratedKeys();
                int colaboracionId = 0;
                if (generatedKeys.next()) {
                    colaboracionId = generatedKeys.getInt(1);
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido guardar los datos.");
                }
                String sentenciaPlanProyecto = "INSERT INTO plan_proyecto "
                        + "(archivo_adjunto, descripcion, nombre, colaboracion_id_colaboracion) "
                        + "VALUES (?, ?, ?, ?)";
                PreparedStatement prepararSentenciaPlanProyecto = conexionBD.prepareStatement(sentenciaPlanProyecto);
                prepararSentenciaPlanProyecto.setBytes(1, planProyecto.getArchivoAdjunto());
                prepararSentenciaPlanProyecto.setString(2, planProyecto.getDescripcion());
                prepararSentenciaPlanProyecto.setString(3, planProyecto.getNombre());
                prepararSentenciaPlanProyecto.setInt(4, colaboracionId);
                int filasAfectadasPlanProyecto = prepararSentenciaPlanProyecto.executeUpdate();
                if (filasAfectadasPlanProyecto > 0) {
                    conexionBD.commit();
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Colaboración registrada con éxito");
                } else {
                    conexionBD.rollback();
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido guardar los datos.");
                }
                conexionBD.close();
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    respuesta.put(Constantes.KEY_MENSAJE, "Registro duplicado");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
                }
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }
    /* 
     * MariaDB [COIL]> describe colaboracion;
+--------------------------------------+--------------+------+-----+---------+----------------+
| Field                                | Type         | Null | Key | Default | Extra          |
+--------------------------------------+--------------+------+-----+---------+----------------+
| id_colaboracion                      | int(11)      | NO   | PRI | NULL    | auto_increment |
| estado                               | varchar(50)  | YES  |     | NULL    |                |
| fecha_inicio                         | date         | NO   |     | NULL    |                |
| fecha_fin                            | date         | NO   |     | NULL    |                |
| idioma                               | varchar(20)  | NO   |     | NULL    |                |
| nombre                               | varchar(100) | NO   | UNI | NULL    |                |
| objetivo_general                     | varchar(350) | YES  |     | NULL    |                |
| tema_interes                         | varchar(50)  | NO   |     | NULL    |                |
| periodo                              | varchar(20)  | NO   |     | NULL    |                |
| no_estudiante_externo                | int(11)      | NO   |     | NULL    |                |
| profesoruv_id_profesoruv             | int(11)      | NO   | MUL | NULL    |                |
| departamento_id_departamento         | int(11)      | NO   | MUL | NULL    |                |
| region_id_region                     | int(11)      | NO   | MUL | NULL    |                |
| asignatura_id_asignatura             | int(11)      | NO   | MUL | NULL    |                |
| profesor_externo_id_profesor_externo | int(11)      | NO   | MUL | NULL    |                |
+--------------------------------------+--------------+------+-----+---------+----------------+

     */
    public static HashMap<String, Object> consultarColaboracionPorSimilitudDeNombreYEstado(String nombreColaboracion, String posibleEstado, String posibleEstado2){
        HashMap <String, Object> respuesta = new HashMap<>();
        ArrayList<Colaboracion> listaColaboracion = new ArrayList<>();
        try(Connection conexionBD = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT estado, fecha_inicio, fecha_fin, idioma, nombre, objetivo_general, tema_interes, periodo, no_estudiante_externo, ");
;           consulta.append("profesoruv_id_profesoruv, profesor_externo_id_profesor_externo, asignatura_id_asignatura, region_id_region, departamento_id_departamento, id_colaboracion ");
            consulta.append("FROM colaboracion ");
            consulta.append("WHERE nombre LIKE ? AND (estado = ? OR estado = ?)");
            PreparedStatement consultaPreparada = conexionBD.prepareStatement(consulta.toString());
            consultaPreparada.setString(1, "%" + nombreColaboracion + "%");
            consultaPreparada.setString(2, posibleEstado);
            consultaPreparada.setString(3, posibleEstado2);
            ResultSet resultado = consultaPreparada.executeQuery();
            while(resultado.next()){
                Colaboracion colaboracion = new Colaboracion();
                colaboracion.setEstado(resultado.getString("estado"));
                colaboracion.setFechaInicio(resultado.getString("fecha_inicio"));
                colaboracion.setFechaFin(resultado.getString("fecha_fin"));
                colaboracion.setIdioma(resultado.getString("idioma"));
                colaboracion.setNombre(resultado.getString("nombre"));
                colaboracion.setObjetivoGeneral(resultado.getString("objetivo_general"));
                colaboracion.setTemaInteres(resultado.getString("tema_interes"));
                colaboracion.setPeriodo(resultado.getString("periodo"));
                colaboracion.setNoEstudiantesExternos(resultado.getInt("no_estudiante_externo"));
                colaboracion.setIdProfesorUV(resultado.getInt("profesoruv_id_profesoruv"));
                colaboracion.setIdProfesorExterno(resultado.getInt("profesor_externo_id_profesor_externo"));
                colaboracion.setIdAsignatura(resultado.getInt("asignatura_id_asignatura"));
                colaboracion.setIdRegion(resultado.getInt("region_id_region"));
                colaboracion.setIdDepartamento(resultado.getInt("departamento_id_departamento"));
                colaboracion.setIdColaboracion(resultado.getInt("id_colaboracion"));
                listaColaboracion.add(colaboracion);
            }   
            respuesta.put("listaColaboracion", listaColaboracion);
            if(listaColaboracion.isEmpty()){
                respuesta.put(Constantes.KEY_ERROR, true);
            }
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
            respuesta.put(Constantes.KEY_ERROR, true);
        }
        return respuesta;
    }
    public static HashMap<String, Object> consultarColaboracionPorEstado(String estadoPosible, String estadoPosible2){
        HashMap <String, Object> respuesta = new HashMap<>();
        ArrayList<Colaboracion> listaColaboracion = new ArrayList<>();
        try(Connection conexionBD = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT estado, fecha_inicio, fecha_fin, idioma, nombre, objetivo_general, tema_interes, periodo, no_estudiante_externo, ");
;           consulta.append("profesoruv_id_profesoruv, profesor_externo_id_profesor_externo, asignatura_id_asignatura, region_id_region, departamento_id_departamento, id_colaboracion ");
            consulta.append("FROM colaboracion ");
            consulta.append("WHERE estado = ? OR estado = ?");
            PreparedStatement consultaPreparada = conexionBD.prepareStatement(consulta.toString());
            consultaPreparada.setString(1, estadoPosible);
            consultaPreparada.setString(2, estadoPosible2);
            ResultSet resultado = consultaPreparada.executeQuery();
            while(resultado.next()){
                Colaboracion colaboracion = new Colaboracion();
                colaboracion.setEstado(resultado.getString("estado"));
                colaboracion.setFechaInicio(resultado.getString("fecha_inicio"));
                colaboracion.setFechaFin(resultado.getString("fecha_fin"));
                colaboracion.setIdioma(resultado.getString("idioma"));
                colaboracion.setNombre(resultado.getString("nombre"));
                colaboracion.setObjetivoGeneral(resultado.getString("objetivo_general"));
                colaboracion.setTemaInteres(resultado.getString("tema_interes"));
                colaboracion.setPeriodo(resultado.getString("periodo"));
                colaboracion.setNoEstudiantesExternos(resultado.getInt("no_estudiante_externo"));
                colaboracion.setIdProfesorUV(resultado.getInt("profesoruv_id_profesoruv"));
                colaboracion.setIdProfesorExterno(resultado.getInt("profesor_externo_id_profesor_externo"));
                colaboracion.setIdAsignatura(resultado.getInt("asignatura_id_asignatura"));
                colaboracion.setIdRegion(resultado.getInt("region_id_region"));
                colaboracion.setIdDepartamento(resultado.getInt("departamento_id_departamento"));
                colaboracion.setIdColaboracion(resultado.getInt("id_colaboracion"));
                listaColaboracion.add(colaboracion);
            }   
            respuesta.put("listaColaboracion", listaColaboracion);
            if(listaColaboracion.isEmpty()){
                respuesta.put(Constantes.KEY_ERROR, true);
            }
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
            respuesta.put(Constantes.KEY_ERROR, true);
        }
        return respuesta;
    }
}
