package dominio;

/**
 *
 * @author elcamacho, yavigutierrez
 */
public class Votante {
    //atributos
    private String nombres;
    private String apellidos;
    private long cedula;
    private String direccion;
    private Mesa_Votacion refMesaVot;
    
    //constructor
    public Votante(){}

    public Votante(String nombres, String apellidos, long cedula, String direccion, Mesa_Votacion refMesaVot) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.direccion = direccion;
        this.refMesaVot = refMesaVot;
    }

    //getters and setters

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Mesa_Votacion getRefMesaVot() {
        return refMesaVot;
    }

    public void setRefMesaVot(Mesa_Votacion refMesaVot) {
        this.refMesaVot = refMesaVot;
    }

}
