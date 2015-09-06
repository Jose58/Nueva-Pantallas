/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaventas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author rancagua
 */
public class PPrincipal extends javax.swing.JFrame {
// Crear intancia conexion
        ConexionBD conexion = new ConexionBD();
        Connection cn = conexion.conectar();
    /**
     * Creates new form PPrincipal
     */
    public PPrincipal() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        timer.start();
        if(Variable.Tipo.equals("0")){
            this.btnClientes.setVisible(false);
            this.btnDetalles.setVisible(false);
            this.btnEmpleados.setVisible(false);
            this.btnProductos.setVisible(false);
            this.btnProveedor.setVisible(false);
            this.btnventas.setVisible(false);
        }else{
            btnCliProductos.setVisible(false);
        }
    }
        Timer timer = new Timer (1000, new ActionListener ()
{
    public void actionPerformed(ActionEvent e)
    {
        archivotxt();
        timer.stop();
     }
});
    public void archivotxt(){
        //abrir conexion
        conexion.conectar();
        try {
             Calendar calendario = Calendar.getInstance();
                // variables para almacenar hora
                int hora, minutos;
                //asiganar valores a variables.
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);
                // a string
                String Hora = hora + ":" + minutos;

                if(Variable.Tipo.equals("1")){
                //Crear consulta para buscar empleado segun rut
                String Query = "SELECT Nombre, Apellido FROM empleado WHERE Rut = '"+Variable.Rut+"'";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(Query);
                String nombreEmpleado = "";
                String apellidoEmpleado = "";
                while(rs.next())
                {
                    nombreEmpleado = rs.getString("NOMBRE");
                    apellidoEmpleado = rs.getString("APELLIDO");
                }
                
        String ruta = "C:\\Users\\jhonny\\Documents\\NetBeansProjects\\examen\\SistemaVentas\\src\\registro.txt";
        File archivo = new File(ruta);
        BufferedWriter bw;
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo,true));
            bw.newLine();
            bw.write("Ingresó " + nombreEmpleado + " " + apellidoEmpleado + " a las " + Hora);
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Ingresó " + nombreEmpleado + " " + apellidoEmpleado + " a las " + Hora);
        }
        bw.close();
                }else{
                                //Crear consulta para buscar empleado segun rut
                String Query = "SELECT Nombre, Apellido FROM cliente WHERE Rut = '"+Variable.Rut+"'";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(Query);
                String nombreEmpleado = "";
                String apellidoEmpleado = "";
                while(rs.next())
                {
                    nombreEmpleado = rs.getString("NOMBRE");
                    apellidoEmpleado = rs.getString("APELLIDO");
                }
                
        String ruta = "C:\\Users\\jhonny\\Documents\\NetBeansProjects\\examen\\SistemaVentas\\src\\registro.txt";
        File archivo = new File(ruta);
        BufferedWriter bw;
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo,true));
            bw.newLine();
            bw.write("Ingresó " + nombreEmpleado + " " + apellidoEmpleado + " a las " + Hora);
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Ingresó " + nombreEmpleado + " " + apellidoEmpleado + " a las " + Hora);
        }
        bw.close();    
                }
        }catch(Exception e){
        }   
      }
    
    
        //crear instancio detalles
        JIDetalle detalle = new JIDetalle();
        // Crear instancia ventas
        JIVentas ventas = new JIVentas();
        // Crear instancia de productos
        JIProductos productos = new JIProductos();
        // Crear instancio clientes
        JIClientes clientes = new JIClientes();
        // Crear instancia proveedores
        JIProveedores proveedores = new JIProveedores();
        //Crear Instancia Empleados
        JIEmpleados empleados = new JIEmpleados();
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEmpleados = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnDetalles = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnventas = new javax.swing.JButton();
        DP = new javax.swing.JDesktopPane();
        btnQS = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1266, 731));
        getContentPane().setLayout(null);

        btnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/btnEmpleados.jpg"))); // NOI18N
        btnEmpleados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });
        getContentPane().add(btnEmpleados);
        btnEmpleados.setBounds(1010, 70, 160, 50);

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/btnCliente.jpg"))); // NOI18N
        btnClientes.setToolTipText("");
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        getContentPane().add(btnClientes);
        btnClientes.setBounds(790, 70, 160, 50);
        btnClientes.getAccessibleContext().setAccessibleName("btnClientes");

        btnDetalles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/btnDetalle.jpg"))); // NOI18N
        btnDetalles.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });
        getContentPane().add(btnDetalles);
        btnDetalles.setBounds(1010, 10, 160, 50);

        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/btnProducto.jpg"))); // NOI18N
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        getContentPane().add(btnProductos);
        btnProductos.setBounds(790, 10, 160, 50);

        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/btnProveedor.jpg"))); // NOI18N
        btnProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });
        getContentPane().add(btnProveedor);
        btnProveedor.setBounds(570, 70, 160, 50);

        btnventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/btnVentas.jpg"))); // NOI18N
        btnventas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventasActionPerformed(evt);
            }
        });
        getContentPane().add(btnventas);
        btnventas.setBounds(570, 10, 160, 50);

        javax.swing.GroupLayout DPLayout = new javax.swing.GroupLayout(DP);
        DP.setLayout(DPLayout);
        DPLayout.setHorizontalGroup(
            DPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1270, Short.MAX_VALUE)
        );
        DPLayout.setVerticalGroup(
            DPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        getContentPane().add(DP);
        DP.setBounds(0, 130, 1270, 490);

        btnQS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/btnQuienesSomos.jpg"))); // NOI18N
        btnQS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQSActionPerformed(evt);
            }
        });
        getContentPane().add(btnQS);
        btnQS.setBounds(530, 650, 160, 50);

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/layout Principal.jpg"))); // NOI18N
        Fondo.setText("jLabel1");
        getContentPane().add(Fondo);
        Fondo.setBounds(0, 0, 1266, 731);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventasActionPerformed
    
        DP.add(ventas);
        ventas.setVisible(true);
        detalle.dispose();
        productos.dispose();
        clientes.dispose();
        empleados.dispose();
        proveedores.dispose();
    }//GEN-LAST:event_btnventasActionPerformed

    private void btnDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
            DP.add(detalle);       
            detalle.setVisible(true);
            ventas.dispose();
            productos.dispose();
            clientes.dispose();
            empleados.dispose();
            proveedores.dispose();
    }//GEN-LAST:event_btnDetallesActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
            DP.add(productos);       
            detalle.dispose();
            ventas.dispose();
            productos.setVisible(true);
            clientes.dispose();
            empleados.dispose();
            proveedores.dispose();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        DP.add(clientes);
            detalle.dispose();
            ventas.dispose();
            productos.dispose();
            empleados.dispose();
            proveedores.dispose();
            clientes.setVisible(true);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
            DP.add(proveedores);
            detalle.dispose();
            ventas.dispose();
            productos.dispose();
            empleados.dispose();
            clientes.dispose();
            proveedores.setVisible(true);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
       DP.add(empleados);
            detalle.dispose();
            ventas.dispose();
            productos.dispose();
            clientes.dispose();
            proveedores.dispose();
            empleados.setVisible(true);
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnQSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQSActionPerformed
        JDQuienesSomos QS = new JDQuienesSomos(this,true);
        QS.setVisible(true);
    }//GEN-LAST:event_btnQSActionPerformed

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
            java.util.logging.Logger.getLogger(PPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane DP;
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnDetalles;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnQS;
    private javax.swing.JButton btnventas;
    // End of variables declaration//GEN-END:variables
}
