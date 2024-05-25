package coilvic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import coilvic.modelo.ConexionBD;
import coilvic.modelo.pojo.Departamento;
import coilvic.utilidades.Constantes;

public class DepartamentoDAO {
    public static HashMap<String, Object> consultarDepartamentoPorAreaAcad(String areaAcad){
        HashMap<String, Object> respuesta = new HashMap<>();
        try (Connection conexionDB = ConexionBD.obtenerConexion()) {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT d.nombre, d.idDepartamento ");
            consulta.append("FROM departamento d ");
            consulta.append("JOIN departamento_has_programaeducativo dhpe ON d.idDepartamento = dhpe.Departamento_idDepartamento ");
            consulta.append("JOIN asignaturaprogramaeducativo ape ON dhpe.ProgramaEducativo_idPrograma = ape.ProgramaEducativo_idPrograma ");
            consulta.append("JOIN asignatura a ON ape.Asignatura_idAsignatura = a.idAsignatura ");
            consulta.append("WHERE a.areaAcademica = ?");
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
                nuevoDepartamento.setIdDepartamento(resultado.getInt("idDepartamento"));
                listaDepartamento.add(nuevoDepartamento);
            }
        }catch(SQLException errorSql){
            errorSql.printStackTrace();
        }
        return listaDepartamento;
    }
}
