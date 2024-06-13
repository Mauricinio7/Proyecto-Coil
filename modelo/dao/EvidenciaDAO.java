package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Evidencia;
import coilvic.utilidades.Constantes;

public class EvidenciaDAO {
    
    public static HashMap<String, Object> obtenerEvidenciasPorIdColaboracion (Integer idColaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {

                String consulta = "SELECT id_evidencia,"
                        + " archivo_adjunto,"
                        + " descripcion,"
                        + " fecha_entrega,"
                        + " nombre,"
                        + " colaboracion_id_colaboracion"
                        + " FROM evidencia"
                        + " WHERE id_colaboracion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Evidencia> evidencias = new ArrayList<>();
                while (resultado.next()) {
                    Evidencia evidencia = new Evidencia();
                    evidencia.setIdEvidencia(resultado.getInt("id_evidencia"));
                    evidencia.setArchivo(resultado.getBytes("archivo_adjunto"));
                    evidencia.setDescripcion(resultado.getString("descripcion"));
                    evidencia.setFechaEntrega(resultado.getString("fecha_entrega"));
                    evidencia.setNombre(resultado.getString("nombre"));
                    evidencia.setIdColaboracion(resultado.getInt("colaboracion_id_colaboracion"));
                    evidencias.add(evidencia);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("evidencias", evidencias);
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
