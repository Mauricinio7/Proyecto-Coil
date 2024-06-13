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

public class DepartamentoDAO {

    public static HashMap<String, Object> consultarDepartamentoPorAreaAcad(String areaAcad){
        HashMap<String, Object> respuesta = new HashMap<>();
        try (Connection conexionDB = ConexionBD.obtenerConexion()) {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT d.nombre, d.id_departamento ");
            consulta.append("FROM departamento d ");
            consulta.append("JOIN departamento_has_programa_educativo dhpe ON d.id_departamento = dhpe.departamento_id_departamento ");
            consulta.append("JOIN asignatura_has_programa_educativo ahpe ON dhpe.programa_educativo_id_programa = ahpe.programa_educativo_id_programa ");
            consulta.append("JOIN asignatura a ON ahpe.asignatura_id_asignatura = a.id_asignatura ");
            consulta.append("WHERE a.area_academica = ?");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            sentenciaPreparada.setString(1, areaAcad);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            respuesta.put("listaDepartamento", listaDepartamento(resultado));
        } catch (SQLException errorSql) {
            errorSql.printStackTrace();
        }
        if (respuesta.isEmpty()) {
            respuesta.put(Constantes.KEY_ERROR, null);
        }
        return respuesta;

    }

    public static ArrayList<Departamento> listaDepartamento(ResultSet resultado){
        ArrayList<Departamento> listaDepartamento = new ArrayList<>();
        try{
            while(resultado.next()){
                Departamento nuevoDepartamento = new Departamento();
                nuevoDepartamento.setNombre(resultado.getString("nombre"));
                nuevoDepartamento.setIdDepartamento(resultado.getInt("id_departamento"));
                listaDepartamento.add(nuevoDepartamento);
            }
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        return listaDepartamento;
    }

    public static HashMap<String, Object> consultarDepartamentoPorId(int idDepartamento) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT nombre FROM departamento WHERE id_departamento = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idDepartamento);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    Departamento departamento = new Departamento();
                    departamento.setNombre(resultado.getString("nombre"));
                    departamento.setIdDepartamento(idDepartamento);
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("departamento", departamento);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return respuesta;
    }
}
