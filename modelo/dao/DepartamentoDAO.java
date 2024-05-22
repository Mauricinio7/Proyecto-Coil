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
    public HashMap<String, Object> consultarListaDepartamento(){
        HashMap<String, Object> respuesta = new HashMap<>();
        ArrayList<Departamento> listaDepartamentos = new ArrayList<>();
        try(Connection conexionDB = ConexionBD.obtenerConexion()){
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT nombre, ");
            consulta.append("idDepartamento FROM departamento");
            PreparedStatement sentenciaPreparada = conexionDB.prepareStatement(consulta.toString());
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while(resultado.next()){
                Departamento nuevoDepartamento = new Departamento();
                nuevoDepartamento.setNombre(resultado.getString("nombre"));
                nuevoDepartamento.setIdDepartamento(resultado.getInt("idDepartamento"));
                listaDepartamentos.add(nuevoDepartamento);
            }
            respuesta.put("nuevoDepartamento", listaDepartamentos);
        }catch(SQLException sqlError){
            sqlError.printStackTrace();
        }
        if(respuesta.isEmpty()) respuesta.put(Constantes.KEY_ERROR, null);
        return respuesta;
    }
}
