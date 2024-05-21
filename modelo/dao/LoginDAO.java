package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import coilvic.modelo.ConexionBD;
import coilvic.utilidades.Constantes;

public class LoginDAO {
    
    public static HashMap<String, Object> iniciarSesion(String nombreUsuario, String contrasena) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_profesor"
                        + " FROM usuario"
                        + " WHERE nombre_usuario = ? AND contrasena = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, nombreUsuario);
                prepararSentencia.setString(2, contrasena);
                ResultSet resultado = prepararSentencia.executeQuery();
                Integer idProfesorUv = null;
                if (resultado.next()) {
                    idProfesorUv = resultado.getInt("id_profesor");
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("idProfesor",idProfesorUv);
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE,"Usuario y/o contraseña incorrectos");
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("idProfesor",idProfesorUv);
                conexionBD.close();
            } catch(SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }
}
