/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_test_jdbc;

import database.utilities.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vince
 */
public class InterfaceOracle extends javax.swing.JDialog {

    DefaultComboBoxModel CbModel = new DefaultComboBoxModel();
    BeanBD BeanConnect;
    /**
     * Creates new form InterfaceOracle
     */
    public InterfaceOracle(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        CbModel.addElement("Select");CbModel.addElement("Update");
        BeanConnect = new BeanBD();
        BeanConnect.setTypeBD("Oracle");
        
        BeanConnect.connect();
        
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
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Requête:");

        jButton1.setText("Envoyer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        BeanRequete BeanRequete = new BeanRequete();
        String Table;
        //selection de la BeanRequete.
        Table=jTextField1.getText();
       // BeanRequete.traiteRequete(Table);
        DefaultTableModel jTableModel;    
        try {
                if(BeanRequete.getRequete().equalsIgnoreCase("update"))
                {
                    BeanConnect.getInstruc().executeUpdate(jTextField1.getText()) ;
                    javax.swing.JOptionPane.showMessageDialog(null,"Update réussie"); 
                }
                else
                {
                    BeanConnect.setRs(BeanConnect.getInstruc().executeQuery(jTextField1.getText())) ;                  
                    BeanRequete.setTable(BeanRequete.getTable().toLowerCase(Locale.FRANCE));
            
                switch(BeanRequete.getTable())
                {   
                    case "activites":
                        IniTable("activites",BeanRequete.isCount());
                        jTableModel = (DefaultTableModel) jTable1.getModel();

                        if(BeanRequete.isCount())
                        {
                            int countA;
                            BeanConnect.getRs().next();
                            countA=BeanConnect.getRs().getInt("count(*)");
                            Vector Temp = new Vector();
                            Temp.addElement(countA);
                            jTableModel.addRow(Temp);
                        }
                        else
                        {
                            String IDActivites; 
                            String LibelleActivite;
                            while (BeanConnect.getRs().next())
                            {
                                IDActivites=BeanConnect.getRs().getString("IDACTIVITES");
                                LibelleActivite=BeanConnect.getRs().getString("LIBELLEACTIVITE");
                                Vector Temp = new Vector();
                                Temp.addElement(IDActivites);
                                Temp.addElement(LibelleActivite);
                                jTableModel.addRow(Temp);
                            }
                        }

                    break;
                    case "intervenants":
                        IniTable("intervenants",BeanRequete.isCount());
                        jTableModel = (DefaultTableModel) jTable1.getModel();

                        if(BeanRequete.isCount())
                        {
                            int countA;
                            BeanConnect.getRs().next();
                            countA=BeanConnect.getRs().getInt("count(*)");
                            Vector Temp = new Vector();
                            Temp.addElement(countA);
                            jTableModel.addRow(Temp);
                        }
                        else
                        {
                            String IDIntervenant, LibelleIntervenant;
                            while (BeanConnect.getRs().next())
                            {
                                IDIntervenant = BeanConnect.getRs().getString("IDINTERVENANT");
                                LibelleIntervenant = BeanConnect.getRs().getString("LIBELLEINTERVENANT");
                                Vector Temp = new Vector();
                                Temp.addElement(IDIntervenant);
                                Temp.addElement(LibelleIntervenant);
                                jTableModel.addRow(Temp);
                            }
                        }

                    break;
                }
                }
        } catch (SQLException ex) {
                Logger.getLogger(InterfaceOracle.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void IniTable(String Table,boolean count)
    {
        switch(Table)
        {
            case "activites":
                if(count)
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "Nombre de tuples d'activites"
                    }
                ));
                else  
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "IDActivites", "LibelleActivite"
                    }
                ));
                break;
            case "intervenants":
                if(count)
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "Nombre de tuples d'Intervenants"
                    }
                ));
                else 
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "IDIntervenant", "LibelleIntervenant"
                    }
                ));
                break;
        }
    }
    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(InterfaceOracle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceOracle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceOracle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceOracle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InterfaceOracle dialog = new InterfaceOracle(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
