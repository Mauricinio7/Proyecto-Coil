package coilvic.modelo.pojo;

public class OfertaColaboracion {
    String idioma;
    String nombre;
    String objetivoGeneral;
    String periodo;
    String temaInteres;
    int idAsignatura;
    int idProfesor;

    public OfertaColaboracion() {
    }

    public OfertaColaboracion(String idioma, String nombre, String objetivoGeneral, String periodo, String temaInteres, int idProfesor, int idAsignatura) {
        this.idioma = idioma;
        this.nombre = nombre;
        this.objetivoGeneral = objetivoGeneral;
        this.periodo = periodo;
        this.temaInteres = temaInteres;
        this.idProfesor = idProfesor;
        this.idAsignatura = idAsignatura;
    }
    
    public int getIdAsignatura() {
        return idAsignatura;
    }
    public String getIdioma() {
        return idioma;
    }

    public String getNombre() {
        return nombre;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getTemaInteres() {
        return temaInteres;
    }

    public int getIdProfesor() {
        return idProfesor;
    }
    
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public void setTemaInteres(String temaInteres) {
        this.temaInteres = temaInteres;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }
    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }
}
