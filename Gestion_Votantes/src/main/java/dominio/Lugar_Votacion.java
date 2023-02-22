
package dominio;

import java.util.ArrayList;

/**
 *
 * @author elcamacho, yavigutierrez
 */
public class Lugar_Votacion {
    //atributos
    private int codigo;
    private String nombre;
    private String direccion;
    private int num_mesas;
    //private int capacidad_votantes;
    private ArrayList<Mesa_Votacion> lstMesas;
    //constructor
    public Lugar_Votacion(){
        lstMesas = new ArrayList<>();
    }
    public Lugar_Votacion(int codigo, String nombre, String direccion, int num_mesas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.num_mesas = num_mesas;
        //this.capacidad_votantes = capacidad_votantes;
        this.lstMesas = new ArrayList<>();
    }
     
    //getters and setters

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNum_mesas() {
        return num_mesas;
    }

    public void setNum_mesas(int num_mesas) {
        this.num_mesas = num_mesas;
    }

    public ArrayList<Mesa_Votacion> getLstMesas() {
        return lstMesas;
    }

    public void setLstMesas(ArrayList<Mesa_Votacion> lstMesas) {
        this.lstMesas = lstMesas;
    }
    
    public Mesa_Votacion crearMesas(int capacidad, int codigo){
        Mesa_Votacion objMesa = new Mesa_Votacion(codigo, capacidad, this);
        this.lstMesas.add(objMesa);
        return objMesa;
    }
}
