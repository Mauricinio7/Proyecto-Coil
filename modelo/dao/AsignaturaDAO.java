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
    public static HashMap<String, Object> consultarAreaAcademica(){
        HashMap<String, Object> respuesta = new HashMap<>();
        ArrayList<String> listaAreaAcad = new ArrayList<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT areaAcademica ");
            consulta.append("FROM asignatura;");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            ResultSet resultadoConsulta = sentenciaPreparada.executeQuery();
            while(resultadoConsulta.next()){
                String area = resultadoConsulta.getString("areaAcademica");
                listaAreaAcad.add(area);
            }
            respuesta.put("listaArea", listaAreaAcad);
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        return respuesta;
    }
    public static HashMap<String, Object> consultarAreaAcademicaPorRegion(int idRegion){
        HashMap<String, Object> respuesta = new HashMap<>();
        ArrayList<String> listaAreaAcad = new ArrayList<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT a.areaAcademica ");
            consulta.append("FROM asignatura a ");
            consulta.append("JOIN asignaturaprogramaeducativo ape ON a.idAsignatura = ape.Asignatura_idAsignatura ");
            consulta.append("JOIN departamento_has_programaeducativo dhpe ON ape.ProgramaEducativo_idPrograma = dhpe.ProgramaEducativo_idPrograma ");
            consulta.append("JOIN region_has_departamento rhd ON dhpe.Departamento_idDepartamento = rhd.Departamento_idDepartamento ");
            consulta.append("WHERE rhd.Region_idRegion = ?");
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta.toString());
            consultaPreparada.setInt(1, idRegion);
            ResultSet resultado = consultaPreparada.executeQuery();
        }catch(SQLException errorSql){

        }
        return null;
    }
}
