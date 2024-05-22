package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Asignatura;

public class AsignaturaDAO {
    public HashMap<String, Asignatura> consultarAsignatura(){
        HashMap<String, Asignatura> respuesta = new HashMap<>();
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
                //nuevaAsignatura.setIdProfesorUV(0);
            }

        }catch(SQLException sqlError){

        }
        return respuesta;
    }
}
