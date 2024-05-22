package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Departamento;
import coilvic.modelo.pojo.Region;
import coilvic.utilidades.Constantes;

public class RegionDAO {
    public HashMap<String, Object> consultarListaRegion(){
        HashMap<String, Object> respuesta = new HashMap<>();
        ArrayList<Region> listaRegion = new ArrayList<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT nombre, ");
            consulta.append("idRegion FROM region");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while(resultado.next()){
                Region nuevaRegion = new Region();
                nuevaRegion.setNombre(resultado.getString("nombre"));
                nuevaRegion.setIdRegion(resultado.getInt("idRegion"));
            }
            respuesta.put("nuevaRegion", listaRegion);
        }catch(SQLException sqlError){
            sqlError.printStackTrace();
        }
        if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, null);
        return respuesta;
    }
    
}
