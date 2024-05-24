package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.ProfesorExterno;
import coilvic.utilidades.Constantes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfesorExternoDAO {
    
    public static HashMap<String, Object> obtenerProfesoresExternos() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idProfesorExterno,"
                        + " nombre,"
                        + " correo,"
                        + " idioma,"
                        + " institucion,"
                        + " pais,"
                        + " telefono"
                        + " FROM profesor_externo";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ProfesorExterno> profesoresExternos = new ArrayList<>();
                while (resultado.next()) {
                    ProfesorExterno profesorExterno = new ProfesorExterno();
                    profesorExterno.setIdProfesorExterno(resultado.getInt("idProfesorExterno"));
                    profesorExterno.setNombre(resultado.getString("nombre"));
                    profesorExterno.setCorreo(resultado.getString("correo"));
                    profesorExterno.setIdioma(resultado.getString("idioma"));
                    profesorExterno.setInstitucion(resultado.getString("institucion"));
                    profesorExterno.setPais(resultado.getString("pais"));
                    profesorExterno.setTelefono(resultado.getString("telefono"));
                    profesoresExternos.add(profesorExterno);
                }
                respuesta.put("profesoresExternos", profesoresExternos);
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
