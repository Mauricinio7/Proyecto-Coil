package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.ProfesorExternoColaboracion;
import coilvic.utilidades.Constantes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfesorExternoDAO {
    
    public static HashMap<String, Object> obtenerProfesoresExternosConColaboracion() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT pe.idProfesorExterno,"
                        + " pe.nombre,"
                        + " pe.correo,"
                        + " pe.idioma,"
                        + " pe.institucion,"
                        + " pe.pais,"
                        + " pe.telefono,"
                        + " COALESCE(c.nombre, 'Sin colaboración')"
                        + " AS nombre_colaboracion"
                        + " FROM profesor_externo pe"
                        + " LEFT JOIN colaboracion c "
                        + "ON pe.idProfesorExterno = c.Profesor_externo_idProfesorExterno";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ProfesorExternoColaboracion> profesoresExternosColaboracion = new ArrayList<>();
                while (resultado.next()) {
                    ProfesorExternoColaboracion profesorExternoColaboracion = new ProfesorExternoColaboracion();
                    profesorExternoColaboracion.getProfesorExterno().setIdProfesorExterno(resultado.getInt("idProfesorExterno"));
                    profesorExternoColaboracion.getProfesorExterno().setNombre(resultado.getString("nombre"));
                    profesorExternoColaboracion.getProfesorExterno().setCorreo(resultado.getString("correo"));
                    profesorExternoColaboracion.getProfesorExterno().setIdioma(resultado.getString("idioma"));
                    profesorExternoColaboracion.getProfesorExterno().setInstitucion(resultado.getString("institucion"));
                    profesorExternoColaboracion.getProfesorExterno().setPais(resultado.getString("pais"));
                    profesorExternoColaboracion.getProfesorExterno().setTelefono(resultado.getString("telefono"));
                    profesorExternoColaboracion.getColaboracion().setNombre(resultado.getString("nombre_colaboracion"));
                    profesoresExternosColaboracion.add(profesorExternoColaboracion);
                }
                respuesta.put("profesoresExternosColaboracion", profesoresExternosColaboracion);
                respuesta.put(Constantes.KEY_ERROR, false);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }
}
