/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_test_jdbc;
import java.sql.*;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vince
 */
public class InterfaceMySQL extends javax.swing.JDialog {
    
    //DefaultComboBoxModel CbModel = new DefaultComboBoxModel();
    Statement instruc;
    ResultSet rs;
    /**
     * Creates new form InterfaceMySQL
     */
    public InterfaceMySQL(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        /*CbModel.addElement("Avion");CbModel.addElement("Agents");CbModel.addElement("Billets");CbModel.addElement("Bagages");CbModel.addElement("Vols");
        CB_TypeRequete.setModel(CbModel);*/
        
        System.out.println("Essai de connexion JDBC");
        try
        {
          Class leDriver = Class.forName("org.gjt.mm.mysql.Driver");
          //Class leDriver = Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException e)
        { 
            System.out.println("Driver adéquat non trouvable : " + e.getMessage()); 
        }
        
        try
        {  
            //Connection con = DriverManager.getConnection("jdbc:mysql://192.168.253.138:3306/sys","thib","1234");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.81.132:3306/sys","thib","1234");
            //Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.81.132:1521:xe","thib","123");
            System.out.println("Connexion à la BDD inpres-metal réalisée");
            instruc = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            System.out.println("Création d'une instance d'instruction pour cette connexion");
            System.out.println("Instruction SELECT sur stocks envoyée à la BDD sys");
 
            
        }
        catch (SQLException e) { System.out.println("Erreur SQL : " + e.getMessage()); }
        
        
}
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        CB_TypeRequete = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jLabel1.setText("Table :");

        jButton1.setText("Envoyer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        CB_TypeRequete.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Avion", "Billets", "Vols", "Agents", "Bagages" }));
        CB_TypeRequete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CB_TypeRequeteActionPerformed(evt);
            }
        });
        CB_TypeRequete.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                CB_TypeRequetePropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_TypeRequete, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(CB_TypeRequete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        // TODO add your handling code here:
    DefaultComboBoxModel CbModel = (DefaultComboBoxModel) CB_TypeRequete.getModel();
    //CB_TypeRequete.getModel();
    String Table;
    Table = (String) CbModel.getSelectedItem();
    DefaultTableModel jTableModel;
    jTableModel = (DefaultTableModel) jTable1.getModel();
    try{
        ResultSet rs = instruc.executeQuery("select * from Avion");
    switch(Table)
    {    
        case "Avion":
            int cpt = 0;
            int idAvion,NbPlaces,PoidsMax;
            String TypeAvion;
            boolean Check_OK;
            while (rs.next())
            {
                idAvion=rs.getInt("idAvion");
                Check_OK=rs.getBoolean("Check_OK");
                TypeAvion=rs.getString("TypeAvion");
                NbPlaces=rs.getInt("NbPlaces");
                PoidsMax=rs.getInt("PoidsMax");
                System.out.println(idAvion + " " +Check_OK + " " + TypeAvion + " " + NbPlaces + " " + PoidsMax);
                Vector Temp = null;
                Temp.add(idAvion);
                Temp.add(Check_OK);
                Temp.add(TypeAvion);
                Temp.add(NbPlaces);
                Temp.add(PoidsMax);
                jTableModel.addRow(Temp);
                
            }
                break;
            case "Agents":
                break;
            case "Bagages":
                break;
            case "Billets":

                break;
            case "Vols":

                break;
    }
    }
    catch (SQLException e) { System.out.println("Erreur SQL : " + e.getMessage()); }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CB_TypeRequetePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CB_TypeRequetePropertyChange

        jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "idAvion", "Check_OK", "TypeAvion", "NbPlaces","Poids Max"
                    }
                ));
    }//GEN-LAST:event_CB_TypeRequetePropertyChange

    private void CB_TypeRequeteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CB_TypeRequeteActionPerformed
               // TODO add your handling code here:
        DefaultComboBoxModel CbModel = (DefaultComboBoxModel) CB_TypeRequete.getModel();
        //CB_TypeRequete.getModel();
        String Table;
        Table = (String) CbModel.getSelectedItem();
        switch(Table)
        {
            case "Avion":
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "idAvion", "Check_OK", "TypeAvion", "NbPlaces","Poids Max"
                    }
                ));
                break;
            case "Agents":
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "idAgents", "Role"
                    }
                ));
                break;
            case "Bagages":
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "idBagages", "Valise", "Poids"
                    }
                ));
                break;
            case "Billets":
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "idBillets", "Nom", "Prenom","Num_id"
                    }
                ));
                break;
            case "Vols":
                jTable1.setModel(new javax.swing.table.DefaultTableModel
                (
                    new Object [][] {
                    },
                    new String [] {
                        "idVols", "Destination", "HeureArrivee","HeureDepart","AvionUtilise"
                    }
                ));
                break;
        }
    }//GEN-LAST:event_CB_TypeRequeteActionPerformed

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
            java.util.logging.Logger.getLogger(InterfaceMySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceMySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceMySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceMySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InterfaceMySQL dialog = new InterfaceMySQL(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> CB_TypeRequete;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
