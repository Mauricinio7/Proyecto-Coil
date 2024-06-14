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
            consulta.append("id_region FROM region");
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
                nuevaRegion.setIdRegion(resultado.getInt("id_region"));
                listaRegion.add(nuevaRegion);
            }
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        return listaRegion;
    }

    public static HashMap<String, Object> consultarRegionPorId(int idRegion){
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT nombre FROM region WHERE id_region = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idRegion);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("region", resultado.getString("nombre"));
                }
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerNombreRegionPorId(Integer idRegion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT nombre ");
            consulta.append("FROM region ");
            consulta.append("WHERE id_region = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setInt(1, idRegion);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()){
                respuesta.put("nombreRegion", resultado.getString("nombre"));
            }
        }catch(SQLException errorSql){
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
            errorSql.printStackTrace();
        }
        if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, null);
        return respuesta;
    }
}
