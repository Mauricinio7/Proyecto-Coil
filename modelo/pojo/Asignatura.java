package coilvic.modelo.pojo;

public class Asignatura {
    String areaAcademical;
    String nombre;
    int idAsignatura;

    public Asignatura() {
    }

    public Asignatura(String areaAcademical, String nombre, int idAsignatura) {
        this.areaAcademical = areaAcademical;
        this.nombre = nombre;
        this.idAsignatura = idAsignatura;
    }

    public String getAreaAcademical() {
        return areaAcademical;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }
    public void setAreaAcademical(String areaAcademical) {
        this.areaAcademical = areaAcademical;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }
}
