/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientServeurSocket;

import TICKMAP.ReponseTICKMAP;
import TICKMAP.RequeteTICKMAP;
import Utilities.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
/**
 *
 * @author Vince
 */
public class InterfaceConnexion extends javax.swing.JDialog {

    private Socket cliSock;
    private boolean logged;
    private ObjectInputStream ois;
    private InterfaceClient guiParent;
    public InterfaceConnexion(java.awt.Frame parent, boolean modal,Socket cliS) {
        super(parent, modal);
        guiParent=((InterfaceClient)parent);
        cliSock = cliS;
        logged = false;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Nom d'utilisateur:");

        jLabel2.setText("Mot de passe:");

        jButton1.setText("Se connecter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jButton1)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        login();
    }//GEN-LAST:event_jButton1ActionPerformed

    void login()
    {
        Identify log = new Identify();
        if(jTextField1.getText() != null && !"".equals(jTextField1.getText()) && jTextField1.getText() != null && !"".equals(jTextField1.getText()))
        {  log.setLogin(jTextField1.getText());
            log.setPassword(jPasswordField1.getText());
        log.setMd();
        
        RequeteTICKMAP req;
        req=log.sendLogin();
        ObjectOutputStream oos =null;
        try
        {
            oos= new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(req); oos.flush();
        }
        catch (IOException e)
        {
            System.err.println("Erreur réseau ? [" + e.getMessage() + "]"); 
        }
        }
        
        ReponseTICKMAP rep = null;
        ois = null;
        try
        {
            ois = new ObjectInputStream(cliSock.getInputStream());
            rep = (ReponseTICKMAP)ois.readObject();
        }
        catch (ClassNotFoundException e)
        { 
            System.out.println("--- erreur sur la classe = " + e.getMessage()); 
        }
        catch (IOException e)
        { 
            System.out.println("--- erreur IO = " + e.getMessage()); }
        if(rep.getChargeUtile().equals("LOGIN OK"))
        {
            try {
                setLogged(true);
                this.setVisible(false);
                System.out.println("*** Cle publique recuperee = "+((PublicKey)Encryption.convertFromBytes(rep.getByteArray())).toString());
                PublicKey pK=(PublicKey)Encryption.convertFromBytes(rep.getByteArray());
                guiParent.setCléPublique(pK);
                //negocier cles sym
                handshake(log.getLogin(),pK);
            } catch (IOException ex) {
                Logger.getLogger(InterfaceConnexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterfaceConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }          
        else 
            setLogged(false);
        
    }
    public void handshake(String log,PublicKey pK)
    {
        try {
            InputStream input = this.getClass().getResourceAsStream("/Cles/ClesLabo.jceks");
            Security.addProvider(new BouncyCastleProvider());
            KeyStore keystore = KeyStore.getInstance("JCEKS");
            keystore.load(input, "123".toCharArray());
            
            guiParent.setKeyCipher((SecretKey)keystore.getKey(log, "123".toCharArray()));
            guiParent.setKeyHmac((SecretKey)keystore.getKey(log + "auth", "123".toCharArray()));
            SecretKey key = (SecretKey)keystore.getKey(log, "123".toCharArray());
            System.out.println(key.toString());
            //envoi des cles sym
            ObjectOutputStream oos =null;
            oos= new ObjectOutputStream(cliSock.getOutputStream());

            byte[]reqCrypt = Encryption.encryptRSA(pK, guiParent.getKeyCipher());
            Encryption crypt = new Encryption();
            crypt.setMessage(reqCrypt);
            oos.writeObject(crypt); oos.flush();

            reqCrypt = Encryption.encryptRSA(pK, guiParent.getKeyHmac());
            oos.writeObject(reqCrypt); oos.flush();

            
            
        } catch (IOException ex) {
            Logger.getLogger(InterfaceConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(InterfaceConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(InterfaceConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(InterfaceConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(InterfaceConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InterfaceConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfaceConnexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceConnexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceConnexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceConnexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*InterfaceConnexion dialog = new InterfaceConnexion(new javax.swing.JFrame(), true,);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);*/
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the logged
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * @param logged the logged to set
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
