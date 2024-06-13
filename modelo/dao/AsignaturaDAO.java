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
            consulta.append("SELECT DISTINCT a.id_asignatura, a.nombre, a.area_academica ");
            consulta.append("FROM asignatura a ");
            consulta.append("JOIN asignatura_has_programa_educativo ahpe ON a.id_asignatura = ahpe.asignatura_id_asignatura ");
            consulta.append("JOIN programa_educativo pe ON ahpe.programa_educativo_id_programa = pe.id_programa ");
            consulta.append("JOIN departamento_has_programa_educativo dhpe ON pe.id_programa = dhpe.programa_educativo_id_programa ");
            consulta.append("JOIN departamento d ON dhpe.departamento_id_departamento = d.id_departamento ");
            consulta.append("WHERE d.id_departamento = ?");            
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
            consulta.append("SELECT DISTINCT a.area_academica ");
            consulta.append("FROM asignatura a ");
            consulta.append("JOIN asignatura_has_programa_educativo ahpe ON a.id_asignatura = ahpe.asignatura_id_asignatura ");
            consulta.append("JOIN programa_educativo pe ON ahpe.programa_educativo_id_programa = pe.id_programa ");
            consulta.append("JOIN departamento_has_programa_educativo dhpe ON pe.id_programa = dhpe.programa_educativo_id_programa ");
            consulta.append("JOIN departamento d ON dhpe.departamento_id_departamento = d.id_departamento ");
            consulta.append("JOIN region_has_departamento rhd ON d.id_departamento = rhd.departamento_id_departamento ");
            consulta.append("JOIN region r ON rhd.region_id_region = r.id_region ");
            consulta.append("WHERE r.id_region = ?");

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
    public static HashMap<String, Object> consultarNombreAsignaturaPorId(Integer idAsignatura){
        HashMap<String, Object> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT nombre ");
            consulta.append("FROM asignatura ");
            consulta.append("WHERE id_asignatura = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setInt(1, idAsignatura);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()){
                respuesta.put("nombreAsignatura", resultado.getString("nombre"));
            }
        }catch(SQLException errorSql){
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
            errorSql.printStackTrace();
        }
        if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, null);
        return respuesta;
    }
    public static ArrayList<Asignatura> obtenerListaAsignatura(ResultSet resultado){
        ArrayList<Asignatura> listaAsignatura = new ArrayList<>();
        try{
            while(resultado.next()){
                Asignatura nuevAsignatura = new Asignatura();
                nuevAsignatura.setNombre(resultado.getString("nombre"));
                nuevAsignatura.setIdAsignatura(resultado.getInt("id_asignatura"));
                nuevAsignatura.setAreaAcademical(resultado.getString("area_academica"));
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
                listaAsignatura.add(resultado.getString("area_academica"));
            }
        }catch(SQLException error){
            error.printStackTrace();
        }
        return listaAsignatura;
    }
}