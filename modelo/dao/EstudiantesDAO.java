package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Estudiante;
import coilvic.utilidades.Constantes;

public class EstudiantesDAO {

    public static HashMap<String, Object> obtenerEstudiantes(Integer idColaboracion){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR,  true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT e.nombre, e.matricula, e.correo, e.idEstudiante, ec.Colaboracion_id_colaboracion AS idColaboracion " +
                "FROM estudiante e " +
                "INNER JOIN estudianteColaboracion ec ON e.idEstudiante = ec.estudiante_idEstudiante " +
                "WHERE ec.Colaboracion_id_colaboracion = ? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Estudiante> estudiantes = new ArrayList();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setCorreo(resultado.getString("correo"));
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setIdColaboracion(resultado.getInt("idColaboracion"));

                    estudiantes.add(estudiante);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("estudiantes", estudiantes);
                conexionBD.close();
            }catch (SQLException e){
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        }else {
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
        }
        return respuesta;
    }   

}
