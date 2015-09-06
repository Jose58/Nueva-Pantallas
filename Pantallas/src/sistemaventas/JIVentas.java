/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaventas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rancagua
 */
public class JIVentas extends javax.swing.JInternalFrame {
    // Crear intancia conexion
        ConexionBD conexion = new ConexionBD();
        Connection cn = conexion.conectar();
    
    // variables
        int NumVentas;
        
    /**
     * Creates new form JIVentas
     */
    public JIVentas() {
        initComponents();
        this.tfRutCliente.requestFocus();
        //Crear instancia calendario
        Calendar cal = Calendar.getInstance();
        //Asignar fecha a label 
        Fecha1.setText( + cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR));
        
        //Buscar Empleado
        BusEmpleado();
        
        //Ver NUmero de ventas
        VerNumero();
        
        //dar valor minimo a cantidad
        tfCantidad.setText("1");
        
        //Iniciar Timer
        timer.start();
        
        limitar();
    }
    
     public void limitar(){
        tfRutCliente.setDocument(new Limitador(tfRutCliente, 8));
    }
    
    Timer timer = new Timer (1000, new ActionListener ()
{
    public void actionPerformed(ActionEvent e)
    {
        //Ver NUmero de ventas
        VerNumero();
     }
});
    //Metodo para buscar empleado
    public void BusEmpleado()
    {
        //abrir conexion
        conexion.conectar();
        try {
                //Crear consulta para buscar empleado segun rut
                String Query = "SELECT Nombre FROM empleado WHERE Rut = '"+Variable.Rut+"'";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(Query);
                while(rs.next())
                {
                    String nombreEmpleado = rs.getString("NOMBRE");
                    Empleado.setText(nombreEmpleado);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    //Metodo para ver numero de ventas
    public void VerNumero()
    {
        //abrir conexion
        conexion.conectar();
        try {
                //Crear consulta para buscar ultimo numero de ventas
                String Query = "SELECT Numero FROM venta ORDER BY Numero DESC LIMIT 1";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(Query);
                while(rs.next())
                {
                    NumVentas = rs.getInt("NUMERO");
                }
                NumeroVenta.setText(String.valueOf(NumVentas));
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    //Metodo para buscar cliente
    public void BusCliente()
    {
        //abrir conexion
        conexion.conectar();
        try {
                //Crear consulta para buscar cliente segun rut
                String Query = "SELECT Nombre FROM cliente WHERE Rut = '"+tfRutCliente.getText()+"'";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(Query);
                while(rs.next())
                {
                    tfNombreCliente.setText(rs.getString("NOMBRE"));
                }
                conexion.desconectar();
                
                    if(tfNombreCliente.getText().equals("")){
                        JOptionPane.showMessageDialog(this,"El Cliente No existe");
                    }else{
                        //Abrir Conexion
                conexion.conectar();
                //hacer update de rut cliente
                String Query1 = "UPDATE venta SET Cliente = '"+tfRutCliente.getText()+"' WHERE Numero = '"+NumeroVenta.getText()+"'";
                    try {
                        Statement st1 = cn.createStatement();
                        st1.executeUpdate(Query1);
                    } catch (SQLException ex) {
                        Logger.getLogger(JIVentas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }//Fin else
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,"El Cliente No existe" + ex);
            }
        
        
    }

    //Metodo Agregar Productos
    public void AgregarProductos(){
            try {
                String codigo = tfCodigo.getText();
                conexion.conectar();
                String query = "SELECT Valor FROM producto WHERE Codigo = '" + codigo + "'";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.next()==true)
                {
                    int valor       = Integer.parseInt(rs.getString("Valor"));
                    int cantidad    = Integer.parseInt(tfCantidad.getText());
                    int total       = (valor * cantidad); 
                    
                    conexion.desconectar();
                    //abrir nueva conexion
                    conexion.conectar();
                    //armar query
                    String Query = "Insert into detalle_venta (Num_venta, Cod_producto, Cantidad, Total) values ('" + NumeroVenta.getText() + "','" + tfCodigo.getText() + "','" + tfCantidad.getText() + "','" + total + "');";
                    //ejecutar query
                    Statement st1 = cn.createStatement();
                    st1.executeUpdate(Query);
                    //cerrar conexion
                    conexion.desconectar();
                    
                    //agregar datos a jTable
                    //Abrir conexion
                    conexion.conectar();
                    //query
                    String consulta = "SELECT Descripcion ,Valor FROM producto WHERE Codigo = '" + codigo + "';";
                    Statement st2 = cn.createStatement();
                    ResultSet rs2 = st2.executeQuery(consulta);
                    if(rs2.next()==true)
                    {
                        String numv = NumeroVenta.getText();
                        String cod = tfCodigo.getText();
                        String descrip = rs2.getString("DESCRIPCION");
                        String cant = tfCantidad.getText();
                        String val = rs2.getString("VALOR");
                        DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
                        Object fila[] = {numv,cod,descrip,cant,val,total};
                        m.addRow(fila);
                    }
                }
                conexion.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(JIVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    //metodo sumar total 
    public void SumarTotal(){
        double sumatoria1 = 0.0;
        int totalRow= jTable1.getRowCount();
        totalRow-=1;
        for(int i=0;i<=(totalRow);i++)
        {
             double sumatoria= Double.parseDouble(String.valueOf(jTable1.getValueAt(i,5)));
             sumatoria1+=sumatoria;
             Total.setText(String.valueOf(sumatoria1));
        }
    }
    
    //Metodo para descontar productos de stock
    public void DescontarStock(){
            try {
                //variable almacenar cantidad de productos
                int CantPro = 0;
                //abrir conexion
                conexion.conectar();
                //Ver cantidad disponible de productos
                String query = "SELECT Stock FROM producto where('" + tfCodigo.getText() + "');";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.next()==true)
                {
                    CantPro = rs.getInt("STOCK");
                }
                //cerrar conexion.
                conexion.desconectar();
                
                //restar productos
                int TotalStock = (CantPro - Integer.parseInt(tfCantidad.getText()));
                
                         //abrir nueva conexion
                        conexion.conectar();
                        String Query1 = "UPDATE producto SET Stock = " + TotalStock + " where Codigo ='" + tfCodigo.getText() + "'";
                        Statement st1 = cn.createStatement();
                        st1.executeUpdate(Query1);
                        
                        //cerrar conexion
                        conexion.desconectar();
                        //limpiar tfCodigo
                        tfCodigo.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(JIVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Fecha = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();
        Fecha1 = new javax.swing.JLabel();
        Empleado = new javax.swing.JLabel();
        Fecha2 = new javax.swing.JLabel();
        Fecha3 = new javax.swing.JLabel();
        tfRutCliente = new javax.swing.JTextField();
        tfNombreCliente = new javax.swing.JTextField();
        Fecha4 = new javax.swing.JLabel();
        Fecha5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        Fecha6 = new javax.swing.JLabel();
        tfCantidad = new javax.swing.JTextField();
        tfCodigo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnQuitarProducto = new javax.swing.JButton();
        btnFacturar = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        NumeroVenta1 = new javax.swing.JLabel();
        NumeroVenta = new javax.swing.JLabel();
        Fecha7 = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        setTitle("Ventas");
        setPreferredSize(new java.awt.Dimension(1264, 493));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(null);

        jTable1.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° venta", "Producto", "Detalle", "Cantidad", "Precio Unitario", "Total"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(492, 60, 750, 330);

        Fecha.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        Fecha.setForeground(new java.awt.Color(255, 255, 255));
        Fecha.setText("USTED ES ATENDIDO POR :");
        getContentPane().add(Fecha);
        Fecha.setBounds(430, 10, 180, 50);

        jLabel1.setFont(new java.awt.Font("Impact", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TOTAL :$");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(830, 400, 150, 50);

        Total.setFont(new java.awt.Font("Impact", 3, 36)); // NOI18N
        Total.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(Total);
        Total.setBounds(990, 400, 150, 50);

        Fecha1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        Fecha1.setForeground(new java.awt.Color(255, 255, 255));
        Fecha1.setText("FECHA");
        getContentPane().add(Fecha1);
        Fecha1.setBounds(20, 10, 140, 50);

        Empleado.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        Empleado.setForeground(new java.awt.Color(255, 255, 255));
        Empleado.setText("USTED ES ATENDIDO POR :");
        getContentPane().add(Empleado);
        Empleado.setBounds(610, 10, 270, 50);

        Fecha2.setFont(new java.awt.Font("Impact", 0, 48)); // NOI18N
        Fecha2.setForeground(new java.awt.Color(255, 255, 255));
        Fecha2.setText("PRODUCTO");
        getContentPane().add(Fecha2);
        Fecha2.setBounds(150, 250, 220, 50);

        Fecha3.setFont(new java.awt.Font("Impact", 0, 48)); // NOI18N
        Fecha3.setForeground(new java.awt.Color(255, 255, 255));
        Fecha3.setText("CLIENTE");
        getContentPane().add(Fecha3);
        Fecha3.setBounds(170, 70, 160, 50);

        tfRutCliente.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        tfRutCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfRutCliente.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfRutClienteCaretUpdate(evt);
            }
        });
        getContentPane().add(tfRutCliente);
        tfRutCliente.setBounds(160, 130, 230, 37);

        tfNombreCliente.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        tfNombreCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfNombreCliente.setEnabled(false);
        getContentPane().add(tfNombreCliente);
        tfNombreCliente.setBounds(160, 180, 230, 37);

        Fecha4.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        Fecha4.setForeground(new java.awt.Color(255, 255, 255));
        Fecha4.setText("NOMBRE");
        getContentPane().add(Fecha4);
        Fecha4.setBounds(70, 170, 90, 50);

        Fecha5.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        Fecha5.setForeground(new java.awt.Color(255, 255, 255));
        Fecha5.setText("RUT");
        getContentPane().add(Fecha5);
        Fecha5.setBounds(110, 120, 50, 50);
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(10, 380, 470, 10);
        getContentPane().add(jSeparator3);
        jSeparator3.setBounds(10, 60, 470, 10);

        Fecha6.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        Fecha6.setForeground(new java.awt.Color(255, 255, 255));
        Fecha6.setText("CANT");
        getContentPane().add(Fecha6);
        Fecha6.setBounds(230, 310, 80, 50);

        tfCantidad.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        getContentPane().add(tfCantidad);
        tfCantidad.setBounds(290, 320, 50, 37);

        tfCodigo.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        getContentPane().add(tfCodigo);
        tfCodigo.setBounds(90, 320, 120, 37);

        btnBuscar.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(51, 102, 255));
        btnBuscar.setText("BUSQUEDA");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar);
        btnBuscar.setBounds(400, 130, 60, 39);

        btnAgregar.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(51, 102, 255));
        btnAgregar.setText("+");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar);
        btnAgregar.setBounds(360, 320, 50, 39);

        btnQuitarProducto.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        btnQuitarProducto.setForeground(new java.awt.Color(51, 102, 255));
        btnQuitarProducto.setText("x");
        btnQuitarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarProductoActionPerformed(evt);
            }
        });
        getContentPane().add(btnQuitarProducto);
        btnQuitarProducto.setBounds(420, 320, 50, 39);

        btnFacturar.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        btnFacturar.setForeground(new java.awt.Color(51, 102, 255));
        btnFacturar.setText("FACTURAR");
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });
        getContentPane().add(btnFacturar);
        btnFacturar.setBounds(190, 400, 140, 39);
        getContentPane().add(jSeparator4);
        jSeparator4.setBounds(10, 230, 470, 10);

        NumeroVenta1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        NumeroVenta1.setForeground(new java.awt.Color(255, 255, 255));
        NumeroVenta1.setText("NUMERO DE VENTA:");
        getContentPane().add(NumeroVenta1);
        NumeroVenta1.setBounds(1000, 10, 140, 50);

        NumeroVenta.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        NumeroVenta.setForeground(new java.awt.Color(255, 255, 255));
        NumeroVenta.setText("numero");
        getContentPane().add(NumeroVenta);
        NumeroVenta.setBounds(1140, 10, 140, 50);

        Fecha7.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        Fecha7.setForeground(new java.awt.Color(255, 255, 255));
        Fecha7.setText("CODIGO");
        getContentPane().add(Fecha7);
        Fecha7.setBounds(10, 310, 80, 50);

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Layout.jpg"))); // NOI18N
        fondo.setPreferredSize(new java.awt.Dimension(1264, 493));
        fondo.setRequestFocusEnabled(false);
        getContentPane().add(fondo);
        fondo.setBounds(0, 0, 1270, 493);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // BUSCAR CLIENTE
        BusCliente();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tfRutClienteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfRutClienteCaretUpdate
        // limpiar nombre si no hay rut
        if(tfRutCliente.getText().equals("")){
            tfNombreCliente.setText("");
        }
    }//GEN-LAST:event_tfRutClienteCaretUpdate

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // ver si los campos tienen datos
        if (tfCodigo.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar productos");
        }else
        {
            AgregarProductos();
            SumarTotal();
            DescontarStock();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnQuitarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarProductoActionPerformed
            try {
                //tomar modelo de la tabla
                DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
                //Seleccionar Fila
                int IdSelect = jTable1.getSelectedRow();
                //obtener valores de Codigo de producto y cantidad
                String codi = m.getValueAt(IdSelect, 1).toString();
                String canti = m.getValueAt(IdSelect, 3).toString();
                //abrir conexion
                conexion.conectar();
                //query
                String Query = "DELETE FROM detalle_venta WHERE (Cod_producto = '" + codi + "') and (Num_venta = '" + NumeroVenta.getText() + "') and (Cantidad = '" + canti + "')";
                Statement st = cn.createStatement();
                st.execute(Query);
                //cerrar conexion
                conexion.desconectar();
                
                //aumentar productos
                //variable almacenar cantidad de productos
                int CantPro = 0;
                //abrir conexion
                conexion.conectar();
                //Ver cantidad disponible de productos
                String query = "SELECT Stock FROM producto where('" + codi + "')";
                Statement st1 = cn.createStatement();
                ResultSet rs1 = st1.executeQuery(query);
                if(rs1.next()==true)
                {
                    CantPro = rs1.getInt("STOCK");
                }
                //cerrar conexion.
                conexion.desconectar();
                
                //restar productos
                int TotalStock = (CantPro + Integer.parseInt(canti));
                
                         //abrir nueva conexion
                        conexion.conectar();
                        String Query2 = "UPDATE producto SET Stock = " + TotalStock + " where Codigo ='" + codi + "'";
                        Statement st2 = cn.createStatement();
                        st2.executeUpdate(Query2);
                        
                        //cerrar conexion
                        conexion.desconectar();
                        //remover fila de jtable
                        m.removeRow(IdSelect); 
                        //Limpiar total
                        Total.setText("0");
                        //Actualizar total
                        SumarTotal();
            } catch (SQLException ex) {
                Logger.getLogger(JIVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_btnQuitarProductoActionPerformed

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        Variable.Total = Total.getText();
        Variable.NumeroVenta = NumeroVenta.getText();
        Variable.Fecha = Fecha1.getText();
      
        //Crear instancia facturar
        Facturar Fac = new Facturar();
        
        //Limpiar Tabla
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° venta", "Producto", "Detalle", "Cantidad", "Precio Unitario", "Total"
            }
        ));
        
        Total.setText("0");
        
        //Limpiar campos
        tfRutCliente.setText("");
        tfNombreCliente.setText("");
        tfCantidad.setText("1");
        
        //Mostrar frame facturar
        Fac.setVisible(true);
        
    }//GEN-LAST:event_btnFacturarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Empleado;
    private javax.swing.JLabel Fecha;
    private javax.swing.JLabel Fecha1;
    private javax.swing.JLabel Fecha2;
    private javax.swing.JLabel Fecha3;
    private javax.swing.JLabel Fecha4;
    private javax.swing.JLabel Fecha5;
    private javax.swing.JLabel Fecha6;
    private javax.swing.JLabel Fecha7;
    private javax.swing.JLabel NumeroVenta;
    private javax.swing.JLabel NumeroVenta1;
    private javax.swing.JLabel Total;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnQuitarProducto;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField tfCantidad;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfNombreCliente;
    private javax.swing.JTextField tfRutCliente;
    // End of variables declaration//GEN-END:variables
}
