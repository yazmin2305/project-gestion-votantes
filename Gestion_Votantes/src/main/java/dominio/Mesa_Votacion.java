
package dominio;

import java.util.ArrayList;

/**
 *
 * @author elcamacho, yavigutierrez
 */
public class Mesa_Votacion {
    //atributos
    private int codigo_mesa;
    private int capacidad_votantes;
    private Lugar_Votacion refLugarVot;
    private ArrayList<Votante> lstVotantes;
    
    //Constructor
    public Mesa_Votacion() {
        lstVotantes = new ArrayList<>();
    }

    public Mesa_Votacion(int codigo_mesa, int capacidad_votantes, Lugar_Votacion refLugarVot) {
        this.codigo_mesa = codigo_mesa;
        this.capacidad_votantes = capacidad_votantes;
        this.refLugarVot = refLugarVot;
        lstVotantes = new ArrayList<>();
    }  
       
    //Getters and Setters
    public int getCodigo_mesa() {
        return codigo_mesa;
    }
    
    public void setCodigo_mesa(int codigo_mesa) {
        this.codigo_mesa = codigo_mesa;
    }

    public int getCapacidad_votantes() {
        return capacidad_votantes;
    }

    public void setCapacidad_votantes(int capacidad_votantes) {
        this.capacidad_votantes = capacidad_votantes;
    }

    public Lugar_Votacion getRefLugarVot() {
        return refLugarVot;
    }

    public void setRefLugarVot(Lugar_Votacion refLugarVot) {
        this.refLugarVot = refLugarVot;
    }

    public ArrayList<Votante> getLstVotantes() {
        return lstVotantes;
    }

    public void setLstVotantes(ArrayList<Votante> lstVotantes) {
        this.lstVotantes = lstVotantes;
    }        
}
