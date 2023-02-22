package acceso;

import dominio.Lugar_Votacion;
import dominio.Mesa_Votacion;
import dominio.Votante;
import dominio.servicio.Servicio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elcamacho, yavigutierrez
 */
public class VotacionRepository implements IVotacionRepository{
    private Connection conn;

    public VotacionRepository() {
        initDatabase();
    }
    @Override
    public Votante find(long cedula) {
        Votante objVotante = null;
        try {

            String sql = "SELECT v.cedula as cedulav, v.nombres as nombresv, v.apellidos as apellidosv, "
                    + "v.direccion as direccionv, m.cod_mesa as codMesa, lu.direccion as direccionL, "
                    + "lu.lugar_vot as nombreLu FROM Votante as v INNER JOIN MesaVotacion as m ON "
                    + "m.cod_mesa = v.cod_mesa INNER JOIN LugarVotacion as lu ON lu.cod_lugar_vot = m.cod_lugar_vot "
                    + "Where v.cedula=" + cedula;
            
            //this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                objVotante = new Votante();
                objVotante.setCedula(rs.getLong("cedulav"));
                objVotante.setNombres(rs.getString("nombresv"));
                objVotante.setApellidos(rs.getString("apellidosv"));
                objVotante.setDireccion(rs.getString("direccionv")); 
                Lugar_Votacion objL = new Lugar_Votacion();
                Mesa_Votacion objM = new Mesa_Votacion();
                objM.setRefLugarVot(objL);
                objVotante.setRefMesaVot(objM);
                objVotante.getRefMesaVot().setCodigo_mesa(rs.getInt("codMesa"));
                objVotante.getRefMesaVot().getRefLugarVot().setDireccion(rs.getString("direccionL"));
                objVotante.getRefMesaVot().getRefLugarVot().setNombre(rs.getString("nombreLu"));
            }
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, "Error al buscar el votante en la base de datos", ex);
        }
        return objVotante;
    }
    
    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS LugarVotacion (\n"
                + "cod_lugar_vot INTEGER PRIMARY KEY, \n"
                + "lugar_vot TEXT NOT NULL, \n"
                + "direccion TEXT NOT NULL, \n"
                + "num_mesas INTEGER NOT NULL \n"
                + ");";
        String sql2 = "CREATE TABLE IF NOT EXISTS MesaVotacion(\n"
                + "cod_mesa INTEGER PRIMARY KEY, \n"
                + "capacidad_votantes INTEGER NOT NULL, \n"
                + "cod_lugar_vot INTEGER NOT NULL, \n"
                + "FOREIGN KEY (cod_lugar_vot) REFERENCES LugarVotacion(cod_lugar_vot)  \n"
                + ");";
        String sql3 = "CREATE TABLE IF NOT EXISTS Votante (\n"
                + "cedula INTEGER PRIMARY KEY, \n"
                + "nombres TEXT NOT NULL, \n"
                + "apellidos TEXT NOT NULL, \n"
                + "direccion TEXT NOT NULL, \n"
                + "cod_mesa INTEGER NOT NULL, \n"
                + "FOREIGN KEY (cod_mesa) REFERENCES MesaVotacion(cod_mesa)  \n"
                + ");";
        
        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.execute(sql3);

        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        //        String url = "jdbc:sqlite::memory:";
        String url = "jdbc:sqlite:./mydatabase.db";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public boolean saveLugarVotacion(Lugar_Votacion objVot) {
        try{
            if(objVot == null || objVot.getNombre().isBlank() || objVot.getDireccion().isBlank() || objVot.getNum_mesas()<=0 || objVot.getCodigo() <0){
                return false;
            }
            String sql = "INSERT INTO LugarVotacion(cod_lugar_vot, lugar_vot, direccion, num_mesas) VALUES (?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, objVot.getCodigo());
            pstmt.setString(2, objVot.getNombre());
            pstmt.setString(3, objVot.getDireccion());
            pstmt.setInt(4, objVot.getNum_mesas());
            pstmt.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);            
        }
        return false;
    }

    @Override
    public boolean saveVotante(Votante objVotante) {
        try{
            if(objVotante == null || objVotante.getCedula()<0 || objVotante.getNombres().isBlank() || objVotante.getApellidos().isBlank() 
                    || objVotante.getDireccion().isBlank() || objVotante.getRefMesaVot().getCodigo_mesa()<0){
                return false;
            }
            String sql = "INSERT INTO Votante(cedula, nombres, apellidos, direccion, cod_mesa) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, objVotante.getCedula());
            pstmt.setString(2, objVotante.getNombres());
            pstmt.setString(3, objVotante.getApellidos());
            pstmt.setString(4, objVotante.getDireccion());
            pstmt.setInt(5, objVotante.getRefMesaVot().getCodigo_mesa());
            pstmt.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);            
        }
        return false;
    }
    
    @Override
    public boolean saveMesaVotacion(Mesa_Votacion objMesa) {
        try{
            if(objMesa == null || objMesa.getCodigo_mesa() <= 0 || objMesa.getCapacidad_votantes() <=0 || objMesa.getRefLugarVot().getCodigo() <= 0){
                return false;
            }
            String sql = "INSERT INTO MesaVotacion(cod_mesa, capacidad_votantes, cod_lugar_vot) VALUES (?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, objMesa.getCodigo_mesa());
            pstmt.setInt(2, objMesa.getCapacidad_votantes());
            pstmt.setInt(3, objMesa.getRefLugarVot().getCodigo());
            pstmt.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);            
        }
        return false;
    }
    
    @Override
    public Lugar_Votacion findLugarVot(int codigo) {
        Lugar_Votacion objLugarVot = null;        
        
        try {
            String sql = "SELECT cod_lugar_vot, lugar_vot, direccion, num_mesas FROM LugarVotacion "
                    + "where cod_lugar_vot=" + codigo;
            
            
            //this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                objLugarVot = new Lugar_Votacion();
                objLugarVot.setCodigo(rs.getInt("cod_lugar_vot"));
                objLugarVot.setNombre(rs.getString("lugar_vot"));
                objLugarVot.setDireccion(rs.getString("direccion"));
                objLugarVot.setNum_mesas(rs.getInt("num_mesas"));        
            }

            buscarMesaXCodigo(codigo, objLugarVot);            

            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, "Error al buscar el lugar de votación en la base de datos", ex);
        }
        return objLugarVot;
    }
    public void buscarMesaXCodigo(int codigo, Lugar_Votacion objLugarVot){
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT cod_mesa, capacidad_votantes, cod_lugar_vot FROM MesaVotacion "
                    + "where cod_lugar_vot=" + codigo;
            ResultSet rs = stmt.executeQuery(sql);          
            
            while(rs.next()){
                Mesa_Votacion objMesa = new Mesa_Votacion();
                objMesa.setCodigo_mesa(rs.getInt("cod_mesa"));
                objMesa.setCapacidad_votantes(rs.getInt("capacidad_votantes"));
                objMesa.setRefLugarVot(objLugarVot);
                objLugarVot.getLstMesas().add(objMesa);
            }
        }catch(SQLException ex){
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, "Error al buscar la mesa de votacion en la base de datos", ex);
        }
    }

    @Override
    public boolean findMesaVotacion(int codigoMesa) {       
        try {

            String sql = "SELECT cod_mesa FROM MesaVotacion " 
                    + "Where cod_mesa=" + codigoMesa;
            //this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return true;
            }
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, "Error al buscar la mesa de votación en la base de datos", ex);
        }
        return false;
    }
}
