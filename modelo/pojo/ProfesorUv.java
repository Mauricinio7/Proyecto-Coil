package coilvic.modelo.pojo;

public class ProfesorUv {
    
    private String nombre;
    private String correo;
    private String noPersonal;
    private Integer idProfesorUv;
    private Integer idRegion;
    private String nombreRegion;

    public ProfesorUv() {
    }

    public ProfesorUv(String nombre, String correo, String noPersonal, Integer idProfesorUv, Integer idRegion, String nombreRegion) {
        this.nombre = nombre;
        this.correo = correo;
        this.noPersonal = noPersonal;
        this.idProfesorUv = idProfesorUv;
        this.idRegion = idRegion;
        this.nombreRegion = nombreRegion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    public Integer getIdProfesorUv() {
        return idProfesorUv;
    }

    public void setIdProfesorUv(Integer idProfesorUv) {
        this.idProfesorUv = idProfesorUv;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

}