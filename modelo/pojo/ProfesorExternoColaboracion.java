package coilvic.modelo.pojo;

public class ProfesorExternoColaboracion {

    private ProfesorExterno profesorExterno;
    private Colaboracion colaboracion;

    public ProfesorExternoColaboracion() {
    }

    public ProfesorExternoColaboracion(ProfesorExterno profesorExterno, Colaboracion colaboracion) {
        this.profesorExterno = profesorExterno;
        this.colaboracion = colaboracion;
    }

    public ProfesorExterno getProfesorExterno() {
        return profesorExterno;
    }

    public void setProfesorExterno(ProfesorExterno profesorExterno) {
        this.profesorExterno = profesorExterno;
    }

    public Colaboracion getColaboracion() {
        return colaboracion;
    }

    public void setColaboracion(Colaboracion colaboracion) {
        this.colaboracion = colaboracion;
    }

}
