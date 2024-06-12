package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Colaboracion;
import coilvic.modelo.pojo.PlanProyecto;
import coilvic.utilidades.Constantes;

public class PlanProyectoDAO {
    
    public static HashMap<String, Object> guardarPlanProyecto(PlanProyecto planProyecto) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
            String sentencia = "INSERT INTO planproyecto"
                    + " (archivoAdjunto, descripcion, nombre,"
                    + " Colaboracion_id_colaboracion, Colaboracion_ProfesorUV_idProfesorUV,"
                    + " Colaboracion_Profesor_externo_idProfesorExterno)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setBytes(1, planProyecto.getArchivoAdjunto());
            prepararSentencia.setString(2, planProyecto.getDescripcion());
            prepararSentencia.setString(3, planProyecto.getNombre());
            prepararSentencia.setInt(4, planProyecto.getIdColaboracion());
            prepararSentencia.setInt(5, planProyecto.getIdProfesorUv());
            prepararSentencia.setInt(6, planProyecto.getIdProfesorExt());
            int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Plan proyecto registrado con éxito");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Error al guardar los datos");
                }
                conexionBD.close();
            } catch(SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, "Error al cargar los datos");
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }
}
