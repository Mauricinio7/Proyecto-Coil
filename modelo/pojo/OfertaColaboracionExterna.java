package coilvic.modelo.pojo;

public class OfertaColaboracionExterna {
    int idOfertaExterna;
    String nombre;
    String objetivoGeneral;
    String idioma;
    String periodo;
    String temaInteres;
    String asignatura;
    int idProfesorExterno;
    public OfertaColaboracionExterna() {
    }
    public OfertaColaboracionExterna(int idOfertaExterna, String nombre, String objetivoGeneral, String idioma, String periodo, String temaInteres, String asignatura, int idProfesorExterno) {
        this.idOfertaExterna = idOfertaExterna;
        this.nombre = nombre;
        this.objetivoGeneral = objetivoGeneral;
        this.idioma = idioma;
        this.periodo = periodo;
        this.temaInteres = temaInteres;
        this.asignatura = asignatura;
        this.idProfesorExterno = idProfesorExterno;
    }
    public int getIdOfertaExterna() {
        return idOfertaExterna;
    }
    public String getNombre() {
        return nombre;
    }
    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }
    public String getIdioma() {
        return idioma;
    }
    public String getPeriodo() {
        return periodo;
    }
    public String getTemaInteres() {
        return temaInteres;
    }
    public String getAsignatura() {
        return asignatura;
    }
    public int getIdProfesorExterno() {
        return idProfesorExterno;
    }
    public void setIdOfertaExterna(int idOfertaExterna) {
        this.idOfertaExterna = idOfertaExterna;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    public void setTemaInteres(String temaInteres) {
        this.temaInteres = temaInteres;
    }
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
    public void setIdProfesorExterno(int idProfesorExterno) {
        this.idProfesorExterno = idProfesorExterno;
    }
}
