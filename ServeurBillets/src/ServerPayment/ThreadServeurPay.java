package ServerPayment;

import ServerPayment.*;
import database.utilities.BeanBD;
import java.net.*;
import java.io.*;
/**
 *
 * @author Vince
 */
public class ThreadServeurPay extends Thread {
    private int port;
    private ConsoleServeur guiApplication;
    private ServerSocket SSocket = null;
    private int nbThreads;
    private ThreadClientPay []thr;
    private BeanBD Bc;
    private SourceTaches tachesAExecuter;
    
    public ThreadServeurPay(int p, ConsoleServeur fs, int nb)
    {
        port = p;  guiApplication = fs; nbThreads = nb;
        tachesAExecuter = new ListeTaches();
    }
    public void run()
    {
        try
        {
            SSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            System.err.println("Erreur de port d'écoute ! ? [" + e + "]"); System.exit(1);
        }
        Bc = new BeanBD();
        Bc.setTypeBD("MySql");
        Bc.connect();
         thr = new ThreadClientPay[nbThreads];
        // Démarrage du pool de threads
        for (int i=0; i<nbThreads; i++)
        {
            thr[i] = new ThreadClientPay (tachesAExecuter, "Thread du pool n°" +String.valueOf(i), null, Bc);
            thr[i].start();
        }

        // Mise en attente du serveur
        Socket CSocket = null;
        while (!isInterrupted())
        {
            try
            {
                System.out.println("************ Serveur en attente");
                CSocket = getSSocket().accept();
                guiApplication.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+"#accept");
                //On assigne la socket à un thread Client
                int i = ChercheThreadDispo();
                System.out.println("Thread dispo = Thread n"+i);
                thr[i].setMySock(CSocket);
            }
            catch (IOException e)
            {
                System.out.println("Erreur d'accept ! ? [" + e.getMessage() + "]");
            }
            
        }
    }
    
     public int ChercheThreadDispo()
    {
        int i;
        for(i=0; i<thr.length;i++)
        {
            if(thr[i].getMySock() == null)
            {
                return i;
            }    
        } 
        return -1;
    }

    public ServerSocket getSSocket() {
        return SSocket;
    }
}
