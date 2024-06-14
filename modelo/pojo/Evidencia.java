package coilvic.modelo.pojo;

public class Evidencia {
    
    private Integer idEvidencia;
    private byte[] archivo;
    private String descripcion;
    private String fechaEntrega;
    private String nombre;
    private Integer idColaboracion;

    public Evidencia() {
    }

    public Evidencia(Integer idEvidencia, byte[] archivo, String descripcion, String fechaEntrega, String nombre, Integer idColaboracion) {
        this.idEvidencia = idEvidencia;
        this.archivo = archivo;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.nombre = nombre;
        this.idColaboracion = idColaboracion;
    }

    public Integer getIdEvidencia() {
        return idEvidencia;
    }

    public void setIdEvidencia(Integer idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdColaboracion() {
        return idColaboracion;
    }

    public void setIdColaboracion(Integer idColaboracion) {
        this.idColaboracion = idColaboracion;
    }
}
