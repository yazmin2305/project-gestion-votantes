/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceso;

import dominio.Lugar_Votacion;
import dominio.Mesa_Votacion;
import dominio.Votante;

/**
 *
 * @author elcamacho, yavigutierrez
 */
public interface IVotacionRepository {
    
    boolean saveLugarVotacion(Lugar_Votacion objVot);
    boolean saveVotante(Votante objVotante);
    boolean saveMesaVotacion(Mesa_Votacion objMesa);
    Votante find(long cedula);
    Lugar_Votacion findLugarVot(int codigo);
    boolean findMesaVotacion(int codigoMesa);
}
