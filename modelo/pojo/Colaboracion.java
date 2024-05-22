package coilvic.modelo.pojo;

public class Colaboracion {
    
    private String estado;
    private String fechaInicio;
    private String fechaFin;
    private String idioma;
    private String nombre;
    private String objetivoGeneral;
    private String temaInteres;
    private String periodo;
    private Integer noEstudiantesExternos;
    private Integer idColaboracion;
    private Integer idProfesorUV;
    private Integer idProfesorExterno;
    private Integer idAsignatura;
    private Integer idRegion;
    private Integer idDepartamento;

    public Colaboracion() {
    }

    public Colaboracion(String estado, String fechaInicio, String fechaFin, String idioma, String nombre, String objetivoGeneral, String temaInteres, String periodo, Integer noEstudiantesExternos, Integer idColaboracion, Integer idProfesorUV, Integer idProfesorExterno, Integer idAsignatura, Integer idRegion, Integer idDepartamento) {
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idioma = idioma;
        this.nombre = nombre;
        this.objetivoGeneral = objetivoGeneral;
        this.temaInteres = temaInteres;
        this.periodo = periodo;
        this.noEstudiantesExternos = noEstudiantesExternos;
        this.idColaboracion = idColaboracion;
        this.idProfesorUV = idProfesorUV;
        this.idProfesorExterno = idProfesorExterno;
        this.idAsignatura = idAsignatura;
        this.idRegion = idRegion;
        this.idDepartamento = idDepartamento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public String getTemaInteres() {
        return temaInteres;
    }

    public void setTemaInteres(String temaInteres) {
        this.temaInteres = temaInteres;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getNoEstudiantesExternos() {
        return noEstudiantesExternos;
    }

    public void setNoEstudiantesExternos(Integer noEstudiantesExternos) {
        this.noEstudiantesExternos = noEstudiantesExternos;
    }

    public Integer getIdColaboracion() {
        return idColaboracion;
    }

    public void setIdColaboracion(Integer idColaboracion) {
        this.idColaboracion = idColaboracion;
    }

    public Integer getIdProfesorUV() {
        return idProfesorUV;
    }

    public void setIdProfesorUV(Integer idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
    }

    public Integer getIdProfesorExterno() {
        return idProfesorExterno;
    }

    public void setIdProfesorExterno(Integer idProfesorExterno) {
        this.idProfesorExterno = idProfesorExterno;
    }

    public Integer getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Integer idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    
    
}
