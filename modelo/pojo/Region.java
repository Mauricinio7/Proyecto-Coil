package coilvic.modelo.pojo;

public class Region {
    String nombre;
    int idRegion;

    public Region() {
    }

    public Region(String nombre, int idRegion) {
        this.nombre = nombre;
        this.idRegion = idRegion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }
    
}
