/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IACOP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tibha
 */
public class Employe extends Personne{

    public Employe(String identifiant) {
        super(identifiant);
    }
    
    public void answer_Question(String Question)
    {
        //check digest
        String str[];
        str=Question.split(";");
         try {
             
            String chaine = "2;" + str[1];
            DatagramPacket dtg = new DatagramPacket(chaine.getBytes(), chaine.length(),adresseGroupe, 5001);
            socketGroupe.send(dtg);
        } catch (IOException ex) {
            Logger.getLogger(Personne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
