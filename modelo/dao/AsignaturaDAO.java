package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Asignatura;
import coilvic.utilidades.Constantes;

public class AsignaturaDAO {
    public static HashMap<String, Object> consultarListaAsignatura(){
        HashMap<String, Object> respuesta = new HashMap<>();
        ArrayList<Asignatura> listaAsignaturas = new ArrayList<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT areaAcademica, ");
            consulta.append("nombre, idAsignatura FROM asignatura");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while(resultado.next()){
                Asignatura nuevaAsignatura = new Asignatura();
                nuevaAsignatura.setAreaAcademical(resultado.getString("areaAcademica"));
                nuevaAsignatura.setIdAsignatura(resultado.getInt("idAsignatura"));
                nuevaAsignatura.setNombre(resultado.getString("nombre"));
                listaAsignaturas.add(nuevaAsignatura);
            }
            respuesta.put("listaAsignatura", listaAsignaturas);
        }catch(SQLException sqlError){
            sqlError.printStackTrace();
        }
        if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, null);
        return respuesta;
    }
}
