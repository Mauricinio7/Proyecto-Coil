package coilvic.modelo.pojo;

public class ProfesorExterno {
    
    private String nombre;
    private String correo;
    private String idioma;
    private String institucion;
    private String pais;
    private String telefono;
    private Integer idProfesorExterno;

    public ProfesorExterno() {
    }

    public ProfesorExterno(String nombre, String correo, String idioma, String institucion, String pais, String telefono, Integer idProfesorExterno) {
        this.nombre = nombre;
        this.correo = correo;
        this.idioma = idioma;
        this.institucion = institucion;
        this.pais = pais;
        this.telefono = telefono;
        this.idProfesorExterno = idProfesorExterno;
    }

    public Integer getIdProfesorExterno() {
        return idProfesorExterno;
    }

    public void setIdProfesorExterno(Integer idProfesorExterno) {
        this.idProfesorExterno = idProfesorExterno;
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
