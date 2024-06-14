package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;


import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.OfertaColaboracionExterna;
import coilvic.utilidades.Constantes;

public class OfertaColaboracionExternaDAO {
    /* 
     * atabase changed
MariaDB [COIL]> describe oferta_colaboracion_externa;
+--------------------------------------+--------------+------+-----+---------+----------------+
| Field                                | Type         | Null | Key | Default | Extra          |
+--------------------------------------+--------------+------+-----+---------+----------------+
| id_oferta_externa                    | int(11)      | NO   | PRI | NULL    | auto_increment |
| nombre                               | varchar(100) | NO   | UNI | NULL    |                |
| objetivo_general                     | varchar(350) | YES  |     | NULL    |                |
| idioma                               | varchar(20)  | NO   |     | NULL    |                |
| periodo                              | varchar(20)  | NO   |     | NULL    |                |
| tema_interes                         | varchar(50)  | NO   |     | NULL    |                |
| asignatura                           | varchar(50)  | YES  |     | NULL    |                |
| profesor_externo_id_profesor_externo | int(11)      | NO   | MUL | NULL    |                |
+--------------------------------------+--------------+------+-----+---------+----------------+

     */
    public static HashMap<String, Boolean> guardarOferta(OfertaColaboracionExterna nuevaOferta){
        HashMap<String, Boolean> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("INSERT INTO oferta_colaboracion_externa ");
            consulta.append("(nombre, objetivo_general, idioma, periodo, tema_interes, ");
            consulta.append("asignatura, profesor_externo_id_profesor_externo) ");
            consulta.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement prepararSentencia = conexionDB.prepareStatement(consulta.toString());
            prepararSentencia.setString(1, nuevaOferta.getNombre());
            prepararSentencia.setString(2, nuevaOferta.getObjetivoGeneral());
            prepararSentencia.setString(3, nuevaOferta.getIdioma());
            prepararSentencia.setString(4, nuevaOferta.getPeriodo());
            prepararSentencia.setString(5, nuevaOferta.getTemaInteres());
            prepararSentencia.setString(6, nuevaOferta.getAsignatura());
            prepararSentencia.setInt(7, nuevaOferta.getIdProfesorExterno());
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
}
