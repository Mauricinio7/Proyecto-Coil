package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.OfertaColaboracion;
import coilvic.utilidades.Constantes;

public class OfertaColaboracionDAO {
    public static HashMap<String, Boolean> guardarOferta(OfertaColaboracion nuevaOferta){
        HashMap<String, Boolean> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("INSERT INTO oferta_colaboracion ");
            consulta.append("(idioma, nombre, objetivo_general, periodo, tema_interes, ");
            consulta.append("profesoruv_id_profesoruv, asignatura_id_asignatura) ");
            consulta.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement prepararSentencia = conexionDB.prepareStatement(consulta.toString());
            prepararSentencia.setString(1, nuevaOferta.getIdioma());
            prepararSentencia.setString(2, nuevaOferta.getNombre());
            prepararSentencia.setString(3, nuevaOferta.getObjetivoGeneral());
            prepararSentencia.setString(4, nuevaOferta.getPeriodo());
            prepararSentencia.setString(5, nuevaOferta.getTemaInteres());
            prepararSentencia.setInt(6, nuevaOferta.getIdProfesor());
            prepararSentencia.setInt(7, nuevaOferta.getIdAsignatura());
            int filasAfectada =prepararSentencia.executeUpdate();
            if(filasAfectada > 0){
                respuesta.put("ofertaColaboracion", true);
            }else{
                respuesta.put(Constantes.KEY_ERROR, false);
            } 
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, false);
        return respuesta;
    }
    public static HashMap<String, Object> consultarOferta(){
        HashMap<String, Object> respuesta = new HashMap<>();
        ArrayList<OfertaColaboracion> listaOferta = new ArrayList<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT idioma, nombre, objetivo_general, periodo, tema_interes, ");
            consulta.append("profesoruv_id_profesoruv, asignatura_id_asignatura ");
            consulta.append("FROM oferta_colaboracion");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while(resultado.next()){
                OfertaColaboracion nuevaOferta = new OfertaColaboracion();
                nuevaOferta.setIdioma(resultado.getString("idioma"));
                nuevaOferta.setNombre(resultado.getString("nombre"));
                nuevaOferta.setObjetivoGeneral(resultado.getString("objetivo_general"));
                nuevaOferta.setPeriodo(resultado.getString("periodo"));
                nuevaOferta.setTemaInteres(resultado.getString("tema_interes"));
                nuevaOferta.setIdProfesor(resultado.getInt("profesoruv_id_profesoruv"));
                nuevaOferta.setIdAsignatura(resultado.getInt("asignatura_id_asignatura"));
                listaOferta.add(nuevaOferta);
            }
            respuesta.put("consultaOferta", listaOferta);
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
            respuesta.put(Constantes.KEY_ERROR, null);
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> consultarOfertaColaboracionPorNombreEIdProfesor(String text, Integer idProfesorUv) {
        HashMap<String, Object> respuesta = new HashMap<>();
        ArrayList<OfertaColaboracion> listaOfertaColaboracion = new ArrayList<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT idioma, nombre, objetivo_general, periodo, tema_interes, ");
            consulta.append("profesoruv_id_profesoruv, asignatura_id_asignatura ");
            consulta.append("FROM oferta_colaboracion ");
            consulta.append("WHERE nombre LIKE ? AND profesoruv_id_profesoruv = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setString(1, "%" + text + "%");
            sentenciaPreparada.setInt(2, idProfesorUv);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while(resultado.next()){
                OfertaColaboracion nuevaOferta = new OfertaColaboracion();
                nuevaOferta.setIdioma(resultado.getString("idioma"));
                nuevaOferta.setNombre(resultado.getString("nombre"));
                nuevaOferta.setObjetivoGeneral(resultado.getString("objetivo_general"));
                nuevaOferta.setPeriodo(resultado.getString("periodo"));
                nuevaOferta.setTemaInteres(resultado.getString("tema_interes"));
                nuevaOferta.setIdProfesor(resultado.getInt("profesoruv_id_profesoruv"));
                nuevaOferta.setIdAsignatura(resultado.getInt("asignatura_id_asignatura"));
                listaOfertaColaboracion.add(nuevaOferta);
            }
            respuesta.put("listaOfertaColaboracion", listaOfertaColaboracion);
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
            respuesta.put(Constantes.KEY_ERROR, null);
        }
        return respuesta;
    }  
    
 }   
