package coilvic.modelo.pojo;

public class Estudiante {

    private String nombre;
    private String matricula;
    private String correo;
    private Integer idEstudiante;
    private Integer idColaboracion;

    public Estudiante(){

    }

    public Estudiante(String nombre, String matricula, String correo, Integer idEstudiante) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.correo = correo;
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public Integer getIdEstudiante() {
        return idEstudiante;
    }
    
    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Integer getIdColaboracion() {
        return idColaboracion;
    }

    public void setIdColaboracion(Integer idColaboracion) {
        this.idColaboracion = idColaboracion;
    }

    

}
