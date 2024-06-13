package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.ProfesorExterno;
import coilvic.utilidades.Constantes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfesorExternoDAO {
    
    public static HashMap<String, Object> obtenerProfesoresExternos() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_profesor_externo,"
                        + " nombre,"
                        + " correo,"
                        + " idioma,"
                        + " institucion,"
                        + " pais,"
                        + " telefono"
                        + " FROM profesor_externo";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ProfesorExterno> profesoresExternos = new ArrayList<>();
                while (resultado.next()) {
                    ProfesorExterno profesorExterno = new ProfesorExterno();
                    profesorExterno.setIdProfesorExterno(resultado.getInt("id_profesor_externo"));
                    profesorExterno.setNombre(resultado.getString("nombre"));
                    profesorExterno.setCorreo(resultado.getString("correo"));
                    profesorExterno.setIdioma(resultado.getString("idioma"));
                    profesorExterno.setInstitucion(resultado.getString("institucion"));
                    profesorExterno.setPais(resultado.getString("pais"));
                    profesorExterno.setTelefono(resultado.getString("telefono"));
                    profesoresExternos.add(profesorExterno);
                }
                respuesta.put("profesoresExternos", profesoresExternos);
                respuesta.put(Constantes.KEY_ERROR, false);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> comprobarProfesoresExternos() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        
        if (conexionBD != null) {
            try {
                String consulta = "SELECT COUNT(*) AS cantidad FROM profesor_externo";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                int cantidadProfesoresExternos = 0;
                if (resultado.next()) {
                    cantidadProfesoresExternos = resultado.getInt("cantidad");
                }
                respuesta.put("hayProfesoresExternos", cantidadProfesoresExternos > 0);
                respuesta.put(Constantes.KEY_ERROR, false);
                
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        
        return respuesta;
    }
    

    public static HashMap<String, Object> obtenerIdProfesor(ProfesorExterno profesorExterno) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_profesor_externo"
                        + " FROM profesor_externo"
                        + " WHERE nombre = ?"
                        + " AND correo = ?"
                        + " AND idioma = ?"
                        + " AND institucion = ?"
                        + " AND pais = ?"
                        + " AND telefono = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, profesorExterno.getNombre());
                prepararSentencia.setString(2, profesorExterno.getCorreo());
                prepararSentencia.setString(3, profesorExterno.getIdioma());
                prepararSentencia.setString(4, profesorExterno.getInstitucion());
                prepararSentencia.setString(5, profesorExterno.getPais());
                prepararSentencia.setString(6, profesorExterno.getTelefono());
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("idProfesorExterno", resultado.getInt("id_profesor_externo"));
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

    public static HashMap<String, Object> registrarProfesorExterno(ProfesorExterno profesorExterno) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
            String sentencia = "INSERT INTO profesor_externo"
                    + " (nombre, correo, idioma, institucion, pais, telefono)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, profesorExterno.getNombre());
            prepararSentencia.setString(2, profesorExterno.getCorreo());
            prepararSentencia.setString(3, profesorExterno.getIdioma());
            prepararSentencia.setString(4, profesorExterno.getInstitucion());
            prepararSentencia.setString(5, profesorExterno.getPais());
            prepararSentencia.setString(6, profesorExterno.getTelefono());            
            int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Datos registrados exitosamente");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "No se han podido guardar los datos");
                }
                conexionBD.close();
            } catch(SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerNombreProfesorExternoPorId(Integer idProfesorExterno) {
        HashMap<String, Object> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT nombre ");
            consulta.append("FROM profesor_externo ");
            consulta.append("WHERE id_profesor_externo = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setInt(1, idProfesorExterno);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()){
                respuesta.put("nombreProfesorExterno", resultado.getString("nombre"));
            }
        }catch(SQLException errorSql){
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
            errorSql.printStackTrace();
        }
        return respuesta;
    }

    

    public static HashMap<String, Object> obtenerProfesoresExternoPorNombre(String nombre) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                StringBuilder consulta = new StringBuilder();
                consulta.append("SELECT id_profesor_externo, nombre ");
                consulta.append("FROM profesor_externo ");
                consulta.append("WHERE nombre LIKE ?");
                PreparedStatement sentenciaPreparada = conexionBD.prepareStatement(consulta.toString());
                sentenciaPreparada.setString(1, "%" + nombre + "%");
                ResultSet resultado = sentenciaPreparada.executeQuery();
                ArrayList<ProfesorExterno> profesoresExternos = new ArrayList<>();
                while (resultado.next()) {
                    ProfesorExterno profesorExterno = new ProfesorExterno();
                    profesorExterno.setNombre(resultado.getString("nombre"));
                    profesorExterno.setIdProfesorExterno(resultado.getInt("id_profesor_externo"));

                    profesoresExternos.add(profesorExterno);
                }
                respuesta.put("profesoresExternos", profesoresExternos);
                respuesta.put(Constantes.KEY_ERROR, false);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }



    /* 
     * MariaDB [COIL]> describe profesor_externo;
+---------------------+--------------+------+-----+---------+----------------+
| Field               | Type         | Null | Key | Default | Extra          |
+---------------------+--------------+------+-----+---------+----------------+
| nombre              | varchar(100) | NO   |     | NULL    |                |
| id_profesor_externo | int(11)      | NO   | PRI | NULL    | auto_increment |
| correo              | varchar(50)  | YES  |     | NULL    |                |
| idioma              | varchar(20)  | YES  |     | NULL    |                |
| institucion         | varchar(100) | NO   |     | NULL    |                |
| pais                | varchar(30)  | NO   |     | NULL    |                |
| telefono            | varchar(10)  | NO   |     | NULL    |                |
+---------------------+--------------+------+-----+---------+----------------+
7 rows in set (0.001 sec)

     */

     public static HashMap<String, Object> obtenerCorreoProfesorExternoPorId(int idProfesorExterno){
        HashMap<String, Object> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT correo ");
            consulta.append("FROM profesor_externo ");
            consulta.append("WHERE id_profesor_externo = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setInt(1, idProfesorExterno);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()){
                respuesta.put("correo", resultado.getString("correo"));
            }
        }catch(SQLException errorSql){
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
            errorSql.printStackTrace();
        }
        return respuesta;
     }
     public static HashMap<String, Object> obtenerInstitucionPorIdProfesorExterno(int idProfesorExterno){
        HashMap<String, Object> respuesta = new HashMap<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT institucion ");
            consulta.append("FROM profesor_externo ");
            consulta.append("WHERE id_profesor_externo = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setInt(1, idProfesorExterno);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()){
                respuesta.put("institucion", resultado.getString("institucion"));
            }
        }catch(SQLException errorSql){
            respuesta.put(Constantes.KEY_MENSAJE, "No se han podido cargar los datos");
            errorSql.printStackTrace();
        }
        return respuesta;
     }
}

