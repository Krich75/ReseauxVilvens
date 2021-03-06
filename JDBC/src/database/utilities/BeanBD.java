/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.utilities;
import ProtocoleLUGAPM.Bagage;
import Utilities.ReadProperties;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Vince
 */
public class BeanBD {
    private String typeBD;
    private Connection con;
    private Statement instruc;
    private ResultSet rs;
    
    public BeanBD()
    {
        typeBD = "";
    }
    /**
     * @return the typeBD
     */
    public String getTypeBD() {
        return typeBD.toUpperCase();
    }

    /**
     * @param typeBD the typeBD to set
     */
    public void setTypeBD(String typeBD) {
        if(typeBD.equalsIgnoreCase("oracle") || typeBD.equalsIgnoreCase("mysql"))
            this.typeBD = typeBD;
        else
            javax.swing.JOptionPane.showMessageDialog(null, "Erreur - deux choix possible : \"Oracle\" ou \"MySQL\"");
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * @return the instruc
     */
    public Statement getInstruc() {
        return instruc;
    }

    /**
     * @param instruc the instruc to set
     */
    public void setInstruc(Statement instruc) {
        this.instruc = instruc;
    }
    
    public int connect()
    {
        Class leDriver;
        if(getTypeBD().equals(""))
            return -1;
        else
        {
            System.out.println("Essai de connexion JDBC");
            ReadProperties rP ;
            try {
                rP = new ReadProperties("/database/utilities/Config.properties");
                String s;
                s = new String(getTypeBD()+"_DRIVER");
                leDriver = Class.forName(rP.getProp(s));
                String address,user,pwd;
                
                s = new String(getTypeBD()+"_ADDRESS");
                address = rP.getProp(s);
                
                s = new String(getTypeBD()+"_USER");
                user = rP.getProp(s);
                
                s = new String(getTypeBD()+"_PASSWORD");
                pwd = rP.getProp(s);
                setCon(DriverManager.getConnection(address,user,pwd));
                setInstruc(getCon().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE));
            }
            catch (ClassNotFoundException e)
            { 
                System.out.println("Driver adéquat non trouvable : " + e.getMessage()); 
            } catch (IOException ex) {
                Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }
    }

    /**
     * @return the rs
     */
    public ResultSet getRs() {
        return rs;
    }

    /**
     * @param rs the rs to set
     */
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
    public String findPassword(String user){
        try {
            setRs(getInstruc().executeQuery("Select password from login where user = '" + user + "'"));
            getRs().next();
            String s =getRs().getString("password");
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean findTicket(String idTicket, int nbPassagers)
    {
        int i=0;
        try {
            rs = instruc.executeQuery("SELECT * FROM tickets where idtickets = '"+idTicket +"';");
            
            if(!rs.isBeforeFirst())
                System.err.println("Pas de data pour ticket : "+idTicket);
            else
            {
                rs.next();
                i = rs.getInt("NbPassagers");
            }
            
            if (i==nbPassagers)
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public String findBagages(String Requete)
    {
        
        String str = "";
        String monVec[]={};
        try {
            setRs(getInstruc().executeQuery("select * from bagages where idBagages like '" + Requete +"';"));
            //setRs(getInstruc().executeQuery("select * from bagages where idBagages like '123-%-2017-10-22%';"));
            ResultSetMetaData rsmd = getRs().getMetaData();
            int nbrCol = rsmd.getColumnCount();
            for(int i=0; i<nbrCol;i++)
            {
                if(i+1 == nbrCol)
                    str= str + rsmd.getColumnName(i+1);
                else
                    str= str + rsmd.getColumnName(i+1) + ";";
            }
            monVec = str.split(";");
            str=str + "@";
            
            while(getRs().next())
            {
                
                str=str + getRs().getString(monVec[0]) + ";";
                str=str + getRs().getBoolean(monVec[1]) + ";";
                str=str + getRs().getFloat(monVec[2]) + ";";
                str=str + getRs().getString(monVec[3]) + ";";
                str=str + getRs().getString(monVec[4]) + ";";
                str=str + getRs().getString(monVec[5]) + ";";
                str=str + getRs().getString(monVec[6]);
                str=str+"@";
            }
            return str;
        } catch (SQLException ex) {
            Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public synchronized void updateLug(String str)
    {
        
        try {
            String monVec[]={};
            monVec = str.split(";");
            getInstruc().executeUpdate("UPDATE bagages SET `" + monVec[2] + "`='" + monVec[3] +"' WHERE `" + monVec[0] + "`='" + monVec[1] + "';");
        } catch (SQLException ex) {
            Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Vector<Bagage> getLugage()
    {
        String str = "";
        String monString[]={};
        Vector<Bagage> monVec = new Vector<Bagage>();
        try {
            setRs(getInstruc().executeQuery("SELECT * FROM sys.bagages where `Charge en soute` = 'N';"));
            ResultSetMetaData rsmd = getRs().getMetaData();
            int nbrCol = rsmd.getColumnCount();
            for(int i=0; i<nbrCol;i++)
            {
                if(i+1 == nbrCol)
                    str= str + rsmd.getColumnName(i+1);
                else
                    str= str + rsmd.getColumnName(i+1) + ";";
            }
            
            monString = str.split(";");
            
            
            while(getRs().next())
            {
                Bagage monBagage = new Bagage(getRs().getString(monString[0]),
                        getRs().getInt(monString[1]),
                        getRs().getFloat(monString[2]),
                        getRs().getString(monString[3]),
                        getRs().getString(monString[5])
                );
                System.out.println(monBagage);
                monVec.add(monBagage);
            }
            return monVec;
        } catch (SQLException ex) {
            Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public synchronized void updateAllLug(Vector<Bagage> vec)
    {
        
        try {
            for(int i=0; i<vec.size();i++)
            {
                System.out.println("UPDATE bagages SET `Charge en soute`='"+vec.get(i).isChargeEnSoute()+"' WHERE `idBagages` ='"+vec.get(i).getIdBagage()+"';");
                getInstruc().executeUpdate("UPDATE bagages SET `Charge en soute`='"+vec.get(i).isChargeEnSoute()+"' WHERE `idBagages` ='"+vec.get(i).getIdBagage()+"';");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String findVols()
    {
        
        String str = "";
        String monVec[]={};
        try {
            setRs(getInstruc().executeQuery("select * from vols where HeureDepart = curdate();"));
            
            ResultSetMetaData rsmd = getRs().getMetaData();
            int nbrCol = rsmd.getColumnCount();
            for(int i=0; i<nbrCol;i++)
            {
                if(i+1 == nbrCol)
                    str= str + rsmd.getColumnName(i+1);
                else
                    str= str + rsmd.getColumnName(i+1) + ";";
            }
            monVec = str.split(";");
            str=str + "@";
            
            while(getRs().next())
            {
                
                str=str + getRs().getInt(monVec[0]) + ";";
                str=str + getRs().getString(monVec[1]) + ";";
                str=str + getRs().getDate(monVec[2]) + ";";
                str=str + getRs().getDate(monVec[3]) + ";";
                str=str + getRs().getInt(monVec[4]);
                str=str+"@";
            }

            
            return str;
        } catch (SQLException ex) {
            Logger.getLogger(BeanBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
