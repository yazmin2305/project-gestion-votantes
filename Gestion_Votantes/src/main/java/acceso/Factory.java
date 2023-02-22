
package acceso;

/**
 *
 * @author elcamacho, yavigutierrez
 */
public class Factory {
    private static Factory instance;

    private Factory() {
    }

    /**
     * Clase singleton
     *
     * @return
     */
    public static Factory getInstance() {

        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    /**
     * Método que crea una instancia concreta de la jerarquia IVotacionRepository
     *
     * @param type
     * @return una clase hija de la abstracción IVotacionRepository
     */
    public IVotacionRepository getRepository(String type) {  
       IVotacionRepository result = null;
        switch (type) {
            case "default":
                result = new VotacionRepository();
                break;
        }
        return result;
    }
}
