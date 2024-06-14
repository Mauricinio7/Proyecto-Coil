package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringJoiner;

import coilvic.modelo.ConexionBD;
import coilvic.utilidades.Constantes;

public class LoginDAO {
    
    public static HashMap<String, Object> iniciarSesion(String nombreUsuario, String contrasena) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT profesoruv_id_profesoruv, tipo_usuario "
                        + " FROM usuario"
                        + " WHERE nombre_usuario = ? AND contrasena = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, nombreUsuario);
                prepararSentencia.setString(2, contrasena);
                ResultSet resultado = prepararSentencia.executeQuery();
                Integer idProfesorUv = null;
                String tipoUsuario = null;
                if (resultado.next()) {
                    idProfesorUv = resultado.getInt("profesoruv_id_profesoruv");
                    tipoUsuario = resultado.getString("tipo_usuario");
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("idProfesor", idProfesorUv);
                    respuesta.put("tipoUsuario", tipoUsuario);
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE,"Usuario y/o contraseña incorrectos");
                }
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
