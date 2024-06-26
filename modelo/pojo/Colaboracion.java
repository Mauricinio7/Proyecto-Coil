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
    private String nombreProfesorUV;
    private String nombreProfesorExterno;
    private String nombreAsignatura;
    private String nombreRegion;
    private String nombreDepartamento;
    private String nombreArea;
    private String correoProfesorUv;
    private String correoProfesorExterno;
    private String institucionProfesorExterno;
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

    public String getCorreoProfesorUv() {
        return correoProfesorUv;
    }

    public void setCorreoProfesorUv(String correoProfesorUv) {
        this.correoProfesorUv = correoProfesorUv;
    }

    public String getCorreoProfesorExterno() {
        return correoProfesorExterno;
    }

    public void setCorreoProfesorExterno(String correoProfesorExterno) {
        this.correoProfesorExterno = correoProfesorExterno;
    }

    public String getInstitucionProfesorExterno() {
        return institucionProfesorExterno;
    }

    public void setInstitucionProfesorExterno(String institucionProfesorExterno) {
        this.institucionProfesorExterno = institucionProfesorExterno;
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

    public void setNombreProfesorUV(String nombreProfesorUV) {
        this.nombreProfesorUV = nombreProfesorUV;
    }

    public void setNombreProfesorExterno(String nombreProfesorExterno) {
        this.nombreProfesorExterno = nombreProfesorExterno;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreProfesorUV() {
        return nombreProfesorUV;
    }

    public String getNombreProfesorExterno() {
        return nombreProfesorExterno;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
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

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
    
    
    
}
