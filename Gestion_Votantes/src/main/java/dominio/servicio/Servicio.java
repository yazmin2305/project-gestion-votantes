
package dominio.servicio;

import acceso.IVotacionRepository;
import dominio.Lugar_Votacion;
import dominio.Mesa_Votacion;
import dominio.Votante;

/**
 *
 * @author elcamacho, yavigutierrez
 */
public class Servicio {
    private IVotacionRepository repository;
    
    /**
     *Constructor con parámetros de la clase Servicio
     * @param repositorio objeto de tipo IVotacionRepository
     */
    public Servicio(IVotacionRepository repositorio) {
        this.repository = repositorio;
    }
    
    public boolean guardarLugarVotacion(Lugar_Votacion objVot){
        if (objVot == null || objVot.getNombre().isBlank() || objVot.getDireccion().isBlank() || objVot.getNum_mesas() <= 0 || objVot.getCodigo() < 0) {
            return false;
        }
        repository.saveLugarVotacion(objVot);
        return true;
    }
    public boolean guardarVotante(Votante objVotante){
        if (objVotante == null || objVotante.getCedula() < 0 || objVotante.getNombres().isBlank() || objVotante.getApellidos().isBlank()
                || objVotante.getDireccion().isBlank() || objVotante.getRefMesaVot().getCodigo_mesa() < 0) {
            return false;
        }
        repository.saveVotante(objVotante);
        return true;
    }
    
    public boolean guardarMesa(Mesa_Votacion objMesa){
        if (objMesa == null || objMesa.getCodigo_mesa() <= 0 || objMesa.getCapacidad_votantes() <= 0 || objMesa.getRefLugarVot().getCodigo() <= 0) {
            return false;
        }
        repository.saveMesaVotacion(objMesa);
        return true;
    }
    public Votante encontrarVotante(long cedula) {
        Votante objVotante = null;
        objVotante = repository.find(cedula);
        return objVotante;
    }
    public Lugar_Votacion encontrarLugarVot(int codigo){
        Lugar_Votacion objLugarVot = null;
        return repository.findLugarVot(codigo);
    }
    public boolean encontrarMesaVotacion(int codigoMesa){
        return repository.findMesaVotacion(codigoMesa);
    }
}
