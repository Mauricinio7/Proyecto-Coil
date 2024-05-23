package coilvic.modelo.pojo;

public class ProgramaEducativo {
    String fechaCreacion;
    String nombre;
    int idPrograma;

    public ProgramaEducativo() {
    }

    public ProgramaEducativo(String fechaCreacion, String nombre, int idPrograma) {
        this.fechaCreacion = fechaCreacion;
        this.nombre = nombre;
        this.idPrograma = idPrograma;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }
    @Override
    public String toString(){
        return "-" + getNombre();
    }
}
