package coilvic.modelo.pojo;

public class PlanProyecto {
    
    private byte[] archivoAdjunto;
    private String descripcion;
    private String nombre;
    private Integer idPlanProyecto;
    private Integer idColaboracion;
    private Integer idProfesorUv;
    private Integer idProfesorExt;

    public PlanProyecto() {
    }

    public PlanProyecto(byte[] archivoAdjunto, String descripcion, String nombre, Integer idPlanProyecto, Integer idColaboracion, Integer idProfesorUv, Integer idProfesorExt) {
        this.archivoAdjunto = archivoAdjunto;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.idPlanProyecto = idPlanProyecto;
        this.idColaboracion = idColaboracion;
        this.idProfesorUv = idProfesorUv;
        this.idProfesorExt = idProfesorExt;
    }

    public byte[] getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(byte[] archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdPlanProyecto() {
        return idPlanProyecto;
    }

    public void setIdPlanProyecto(Integer idPlanProyecto) {
        this.idPlanProyecto = idPlanProyecto;
    }

    public Integer getIdColaboracion() {
        return idColaboracion;
    }

    public void setIdColaboracion(Integer idColaboracion) {
        this.idColaboracion = idColaboracion;
    }

    public Integer getIdProfesorUv() {
        return idProfesorUv;
    }

    public void setIdProfesorUv(Integer idProfesorUv) {
        this.idProfesorUv = idProfesorUv;
    }

    public Integer getIdProfesorExt() {
        return idProfesorExt;
    }

    public void setIdProfesorExt(Integer idProfesorExt) {
        this.idProfesorExt = idProfesorExt;
    }

}
