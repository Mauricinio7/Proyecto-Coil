package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Region;
import coilvic.utilidades.Constantes;

public class RegionDAO {
public static HashMap<String, Object> consultarListaRegion(){
    HashMap<String, Object> respuesta = new HashMap<>();
    try(Connection conexionDB = ConexionBD.obtenerConexion()){
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT nombre, ");
        consulta.append("idRegion FROM region");
        PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
        ResultSet resultado = sentenciaPreparada.executeQuery();
        respuesta.put("listaRegion", obtenerListaRegion(resultado));
    }catch(SQLException sqlError){
        sqlError.printStackTrace();
    }
    if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, null);
    return respuesta;
}
public static HashMap<String, Object> consultarRegionPorAreaAcad(String areaAcad){
    HashMap<String, Object> respuesta = new HashMap<>();
    try(Connection conexionDB = ConexionBD.obtenerConexion()){
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT DISTINCT r.nombre, r.idRegion ");
        consulta.append("FROM region r ");
        consulta.append("JOIN region_has_departamento rhd ON r.idRegion = rhd.Region_idRegion ");
        consulta.append("JOIN departamento_has_programaeducativo dhpe ON rhd.Departamento_idDepartamento = dhpe.Departamento_idDepartamento ");
        consulta.append("JOIN asignaturaprogramaeducativo ape ON dhpe.ProgramaEducativo_idPrograma = ape.ProgramaEducativo_idPrograma ");
        consulta.append("JOIN asignatura a ON ape.Asignatura_idAsignatura = a.idAsignatura ");
        consulta.append("WHERE a.areaAcademica = ?");            
        PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
        sentenciaPreparada.setString(1, areaAcad);
        ResultSet resultado = sentenciaPreparada.executeQuery();
        respuesta.put("listaRegion", obtenerListaRegion(resultado));
    }catch(SQLException errorSql){
        errorSql.printStackTrace();
    }
    return respuesta;
}
public static HashMap<String, Object> consultarRegionPorAsignatura(int idAsignatura){
    HashMap<String, Object> respuesta = new HashMap<>();
    try(Connection conexionDB = ConexionBD.obtenerConexion()){
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT DISTINCT r.nombre AS nombreRegion ");
        consulta.append("FROM region r ");
        consulta.append("JOIN region_has_departamento rhd ON r.idRegion = rhd.Region_idRegion ");
        consulta.append("JOIN departamento_has_programaeducativo dhpe ON rhd.Departamento_idDepartamento = dhpe.Departamento_idDepartamento ");
        consulta.append("JOIN asignaturaprogramaeducativo ape ON dhpe.ProgramaEducativo_idPrograma = ape.ProgramaEducativo_idPrograma ");
        consulta.append("JOIN asignatura a ON ape.Asignatura_idAsignatura = a.idAsignatura ");
        consulta.append("WHERE a.idAsignatura = ?"); 
        PreparedStatement prepararSentencia = conexionDB.prepareStatement(consulta.toString());
        ResultSet resultado = prepararSentencia.executeQuery();
        respuesta.put("listaRegion", obtenerListaRegion(resultado));
    }catch(SQLException errorSql){
        errorSql.printStackTrace();
    }
    return respuesta;
}
public static ArrayList<Region> obtenerListaRegion(ResultSet resultado){
    ArrayList<Region> listaRegion = new ArrayList<>();
    try{
        while(resultado.next()){
            Region nuevaRegion = new Region();
            nuevaRegion.setIdRegion(resultado.getInt("idRegion"));
            nuevaRegion.setNombre(resultado.getString("nombre"));
            listaRegion.add(nuevaRegion);
        }
    }catch(SQLException errorSql){
        errorSql.printStackTrace();
    }
    return listaRegion;
}
}
