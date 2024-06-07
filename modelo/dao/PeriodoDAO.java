package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.utilidades.Constantes;
import java.sql.PreparedStatement;

public class PeriodoDAO {
    
    public static HashMap<String, Object> obtenerPeriodosConcluidos() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT DISTINCT periodo FROM colaboracion WHERE estado = 'Finalizada completamente'";
                PreparedStatement prepararSentencia = (PreparedStatement) conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<String> periodosConcluidos = new ArrayList();
                while(resultado.next()) {
                    periodosConcluidos.add(resultado.getString("periodo"));
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("Periodos", periodosConcluidos);
                conexionBD.close();
            } catch(SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }
}
