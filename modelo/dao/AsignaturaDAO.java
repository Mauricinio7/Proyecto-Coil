package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Asignatura;
import coilvic.modelo.pojo.Departamento;
import coilvic.utilidades.Constantes;

public class AsignaturaDAO {
    public static HashMap<String, Object> consultaAsignaturaDepartamento(int idDepartamento){
        HashMap<String, Object> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT a.idAsignatura, a.nombre, a.areaAcademica ");
            consulta.append("FROM asignatura a ");
            consulta.append("JOIN asignaturaprogramaeducativo ape ON a.idAsignatura = ape.Asignatura_idAsignatura ");
            consulta.append("JOIN programaeducativo pe ON ape.ProgramaEducativo_idPrograma = pe.idPrograma ");
            consulta.append("JOIN departamento_has_programaeducativo dhpe ON pe.idPrograma = dhpe.ProgramaEducativo_idPrograma ");
            consulta.append("JOIN departamento d ON dhpe.Departamento_idDepartamento = d.idDepartamento ");
            consulta.append("WHERE d.idDepartamento = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setInt(1, idDepartamento);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            respuesta.put("listaAsignatura", obtenerListaAsignatura(resultado));
        }catch(SQLException error){
            error.printStackTrace();
        }
        if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, null);
        return respuesta;
    }
    public static HashMap<String, Object> consultarAreaAcademicaPorRegion(int idRegion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        try (Connection conexionDB = ConexionBD.obtenerConexion()) {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT a.areaAcademica ");
            consulta.append("FROM asignatura a ");
            consulta.append("JOIN asignaturaprogramaeducativo ape ON a.idAsignatura = ape.Asignatura_idAsignatura ");
            consulta.append("JOIN programaeducativo pe ON ape.ProgramaEducativo_idPrograma = pe.idPrograma ");
            consulta.append("JOIN departamento_has_programaeducativo dhp ON pe.idPrograma = dhp.ProgramaEducativo_idPrograma ");
            consulta.append("JOIN departamento d ON dhp.Departamento_idDepartamento = d.idDepartamento ");
            consulta.append("JOIN region_has_departamento rhd ON d.idDepartamento = rhd.Departamento_idDepartamento ");
            consulta.append("JOIN region r ON rhd.Region_idRegion = r.idRegion ");
            consulta.append("WHERE r.idRegion = ?");
            PreparedStatement consultaPreparada = conexionDB.prepareStatement(consulta.toString());
            consultaPreparada.setInt(1, idRegion);
            ResultSet resultado = consultaPreparada.executeQuery();
            respuesta.put("listaArea", obtenerListaArea(resultado));
        } catch (SQLException errorSql) {
            errorSql.printStackTrace();
        }
        if (respuesta.isEmpty()) {
            respuesta.put(Constantes.KEY_ERROR, null);
        }
        return respuesta;
    }
    
    public static ArrayList<Asignatura> obtenerListaAsignatura(ResultSet resultado){
        ArrayList<Asignatura> listaAsignatura = new ArrayList<>();
        try{
            while(resultado.next()){
                Asignatura nuevAsignatura = new Asignatura();
                nuevAsignatura.setNombre(resultado.getString("nombre"));
                nuevAsignatura.setIdAsignatura(resultado.getInt("idAsignatura"));
                nuevAsignatura.setAreaAcademical(resultado.getString("areaAcademica"));
                listaAsignatura.add(nuevAsignatura);
            }
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        return listaAsignatura;
    }
    public static ArrayList<String> obtenerListaArea(ResultSet resultado){
        ArrayList<String> listaAsignatura = new ArrayList<>();
        try{
            while(resultado.next()){
                listaAsignatura.add(resultado.getString("areaAcademica"));
            }
        }catch(SQLException error){
            error.printStackTrace();
        }
        return listaAsignatura;
    }
}