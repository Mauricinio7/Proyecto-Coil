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
    public static ArrayList<Region> obtenerListaRegion(ResultSet resultado){
        ArrayList<Region> listaRegion = new ArrayList<>();
        try{
            while(resultado.next()){
                Region nuevaRegion = new Region();
                nuevaRegion.setNombre(resultado.getString("nombre"));
                nuevaRegion.setIdRegion(resultado.getInt("idRegion"));
                listaRegion.add(nuevaRegion);
            }
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        return listaRegion;
    }
}
