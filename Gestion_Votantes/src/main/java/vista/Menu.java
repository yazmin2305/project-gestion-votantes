package vista;

import acceso.Factory;
import acceso.IVotacionRepository;
import dominio.Lugar_Votacion;
import dominio.Mesa_Votacion;
import dominio.Votante;
import dominio.servicio.Servicio;
import java.util.Random;
import java.util.Scanner;


/**
 *
 * @author elcamacho, yavigutierrez
 */
public class Menu {
    Scanner objScanner = new Scanner(System.in);
    IVotacionRepository repo = Factory.getInstance().getRepository("default");
    Servicio objServicio = new Servicio(repo);
    public Menu(){ }
    
    public void ejecutarmenu(){
        int opcion =0;
        
        do{
            System.out.println("----Menu----");
            System.out.println("1. Agregar lugar de votación");
            System.out.println("2. Agregar votante");
            System.out.println("3. Buscar lugar de votación por cedula");
            System.out.println("4. Salir");

            System.out.println("Ingrese una opcion");
            opcion = objScanner.nextInt();
            
            switch (opcion) {
               case 1:
                   PedirDatosLugarVotacion();
                   break;
               case 2:
                   PedirDatosVotante();
                   break;
               case 3:
                   BuscarVotanteXCedula();
                   break;
               case 4:
                   System.out.println("Saliendo...");
            }
        }while(opcion!=4);            
    
    }
    public void PedirDatosLugarVotacion(){
        Lugar_Votacion objLugarVot = new Lugar_Votacion();
        System.out.println("Ingrese el codigo del lugar de votación: ");
        int codigo = objScanner.nextInt();
        objScanner.nextLine();
        objLugarVot.setCodigo(codigo);
        System.out.println("Ingrese el nombre del lugar de votación: ");
        String nombre = objScanner.nextLine();
        //objScanner.next();
        objLugarVot.setNombre(nombre);
        System.out.println("Ingrese la dirección del lugar de votación: ");
        String direccion = objScanner.nextLine();
        objLugarVot.setDireccion(direccion);
        System.out.println("Ingrese el numero de mesas del lugar de votación: ");
        int num_mesas = objScanner.nextInt();
        while (num_mesas < 1 || num_mesas > 15) {
            System.out.println("El numero de mesas debe estar comprendido entre 1 y 15, ingrese el valor nuevamente: ");
            num_mesas = objScanner.nextInt();
        }        
        objLugarVot.setNum_mesas(num_mesas);
        
        guardarMesa(objLugarVot);
        objServicio.guardarLugarVotacion(objLugarVot);
    }
    public void guardarMesa(Lugar_Votacion objLugarVot){
        for (int i = 0; i < objLugarVot.getNum_mesas(); i++) {
            System.out.println("Ingrese la capacidad de votantes para la mesa " + (i + 1));
            int capacidad = objScanner.nextInt();

            System.out.println("Ingrese el codigo para la mesa " + (i + 1));
            int codMesa = objScanner.nextInt();
            while (objServicio.encontrarMesaVotacion(codMesa)) {
                System.out.println("El codigo ya existe. Digite uno nuevo: ");
                codMesa = objScanner.nextInt();
            }
            Mesa_Votacion objMesa = objLugarVot.crearMesas(capacidad, codMesa);

            objServicio.guardarMesa(objMesa);
        }
    }
    public void PedirDatosVotante(){
        Votante objVotante = new Votante();
        System.out.println("Ingrese la cedula del votante: ");
        long cedula = objScanner.nextLong();
        objScanner.nextLine();
        objVotante.setCedula(cedula);
        System.out.println("Ingrese el nombre del votante: ");
        String nombresVot = objScanner.nextLine();
        objVotante.setNombres(nombresVot);
        System.out.println("Ingrese los apellidos del votante: ");
        String apellidosVot = objScanner.nextLine();
        objVotante.setApellidos(apellidosVot);
        System.out.println("Ingrese la direccion del votante: ");
        String direccionVot = objScanner.nextLine();
        objVotante.setDireccion(direccionVot);
        System.out.println("Ingrese el codigo del lugar de votación asociado al votante: ");
        int codLugarVot = objScanner.nextInt();
        Lugar_Votacion objLVot = objServicio.encontrarLugarVot(codLugarVot);
        //generar num aleatorio
        Random rand = new Random();
        int random_num = rand.nextInt(objLVot.getNum_mesas());

        Mesa_Votacion objM = objLVot.getLstMesas().get(random_num);
        objM.getLstVotantes().add(objVotante);
        objVotante.setRefMesaVot(objM);
        objServicio.guardarVotante(objVotante);
    }
    
    public void BuscarVotanteXCedula(){
        System.out.println("Ingrese la cedula del votante: ");
        long cedulaBuscar = objScanner.nextLong();

        Votante objVot = objServicio.encontrarVotante(cedulaBuscar);
        if (objVot == null) {
            System.out.println("No se encontró el votante");
        } else {
            System.out.println("Información votante:");
            System.out.println("Cedula: " + objVot.getCedula());
            System.out.println("Nombres: " + objVot.getNombres());
            System.out.println("Apellidos: " + objVot.getApellidos());
            System.out.println("Direccion: " + objVot.getDireccion()+"\n");
            System.out.println("Informacion del lugar de votación: ");
            System.out.println("Nombre: " + objVot.getRefMesaVot().getRefLugarVot().getNombre());
            System.out.println("Dirección: " + objVot.getRefMesaVot().getRefLugarVot().getDireccion());
            System.out.println("Codigo de la mesa: " + objVot.getRefMesaVot().getCodigo_mesa());
        }
    }
    
}
