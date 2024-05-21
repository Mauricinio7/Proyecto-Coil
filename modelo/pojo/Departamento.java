package coilvic.modelo.pojo;

public class Departamento {
    String nombre;
    int idDepartamento;

    public Departamento() {
    }
    public Departamento(String nombre, int idDepartamento) {
        this.nombre = nombre;
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    
}
