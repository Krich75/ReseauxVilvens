/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import ProtocoleLUGAP.RequeteLUGAP;
import database.utilities.BeanBD;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Vince
 */
public class ThreadClient extends Thread {
    private SourceTaches tachesAExecuter;
    private String nom;
    private Runnable tacheEnCours;
    private Socket mySock;
    private ConsoleServeur guiApplication;
    private BeanBD Bc;
    public ThreadClient(SourceTaches st, String n, Socket s, ConsoleServeur fs )
    {
        tachesAExecuter = st;
        nom = n;
        mySock = s;
        guiApplication = fs;
        Bc = new BeanBD();
        Bc.setTypeBD("MySql");
        Bc.connect();
    }
    
    public void run()
    {
       while (!isInterrupted())
        {
            if(mySock!=null)
           {
                
                ObjectInputStream ois=null;
                RequeteLUGAP req = null;
                try
                {
                    ois = new ObjectInputStream(mySock.getInputStream());
                    req = (RequeteLUGAP)ois.readObject();
                    req.setBc(Bc);
                    System.out.println("Requete lue par le serveur, instance de " +req.getClass().getName());
                }
                catch (ClassNotFoundException e)
                {
                    System.err.println("Erreur de def de classe [" + e.getMessage() + "]");
                }
                catch (IOException e)
                {
                    System.err.println("Erreur ? [" + e.getMessage() + "]");
                }
                
                if(req.getType()==RequeteLUGAP.REQUEST_DECONNECT)
                {
                    System.out.println("Sock to null");
                    
                    this.setMySock(null);
                }
                else
                {
                
                    Runnable travail = req.createRunnable(mySock, guiApplication);
                    if (travail != null)
                    {
                        tachesAExecuter.recordTache(travail);
                        System.out.println("Travail mis dans la file");
                    }
                    else System.out.println("Pas de mise en file");

                    try
                    {
                        System.out.println("Tread client avant get");
                        tacheEnCours = tachesAExecuter.getTache();
                    }
                    catch (InterruptedException e)
                    {
                        System.out.println("Interruption : " + e.getMessage());
                    }
                    System.out.println("run de tachesencours");
                    tacheEnCours.run();
                }
           }
        }
    }

    /**
     * @return the mySock
     */
    public Socket getMySock() {
        return mySock;
    }

    /**
     * @param mySock the mySock to set
     */
    public void setMySock(Socket mySock) {
        this.mySock = mySock;
    }
}
