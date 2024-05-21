package coilvic.modelo.pojo;

public class Asignatura {
    String areaAcademical;
    String nombre;
    int idAsignatura;
    int idProfesorUV;

    public Asignatura() {
    }

    public Asignatura(String areaAcademical, String nombre, int idAsignatura, int idProfesorUV) {
        this.areaAcademical = areaAcademical;
        this.nombre = nombre;
        this.idAsignatura = idAsignatura;
        this.idProfesorUV = idProfesorUV;
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

    public int getIdProfesorUV() {
        return idProfesorUV;
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

    public void setIdProfesorUV(int idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
    }
    
}
