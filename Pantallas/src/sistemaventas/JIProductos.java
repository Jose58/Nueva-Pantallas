/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaventas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rancagua
 */
public class JIProductos extends javax.swing.JInternalFrame {
ConexionBD conexion = new ConexionBD();
Connection cn = conexion.conectar();

String Numero = "";
String Codigo = "";
Boolean Editar;
    /**
     * Creates new form JIProductos
     */
    public JIProductos() {
        initComponents();
        Cargar();
        tfNuevos.setEnabled(false);
        btnEditar.setEnabled(false);
        ProveedorCombo();
    }

    
    
    public void Cargar(){
        //tomar modelo de la tabla
        DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
        //abrir conexion
        conexion.conectar();
        try {
                //Crear consulta para buscar empleado segun rut
                String Query = "select * from producto";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(Query);
                while(rs.next())
                {
                    String codigo = rs.getString("CODIGO");
                    String nombre = rs.getString("NOMBRE");
                    String descripcion = rs.getString("DESCRIPCION");
                    String stock = rs.getString("STOCK");
                    String costo = rs.getString("COSTO");
                    String utilidad = rs.getString("PORCUTILIDAD");
                    String valor = rs.getString("VALOR");
                    String proveedor = rs.getString("PROVEEDOR");
                    Object fila[] = {codigo,nombre,descripcion,stock,costo, utilidad, valor, proveedor};
                    m.addRow(fila);
                }
                conexion.desconectar();

            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //metodo para buscar producto
    public void tfCodigo(){
        Numero = tfCodigo.getText();
        //abrir conexion
        conexion.conectar();
        try {
                //Crear consulta para buscar producto segun codigo
                String query = "SELECT Nombre , Descripcion , Stock , Costo , PorcUtilidad , Valor , Proveedor FROM producto WHERE Codigo = '" + Numero + "';";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.next()==true)
                {
                    tfNombre.setText(rs.getString("NOMBRE"));
                    tfDescripcion.setText(rs.getString("DESCRIPCION"));
                    tfStock.setText(rs.getString("STOCK"));
                    tfCosto.setText(rs.getString("COSTO"));
                    tfUtilidad.setText(rs.getString("PORCUTILIDAD"));
                    tfValor.setText(rs.getString("VALOR"));
                    tfProveedor.setText(rs.getString("PROVEEDOR"));
                    
                    tfNuevos.setEnabled(true);
                    tfStock.setEnabled(false);
                    Editar = true;
                    btnAgregar.setEnabled(false);
                    btnEditar.setEnabled(true);
                }else{
                tfCodigo.requestFocus();
                JOptionPane.showMessageDialog(this, "El producto no existe");
                }
                conexion.desconectar();

            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void ProveedorCombo(){
        
        //abrir conexion
        conexion.conectar();
        try {
                //Crear consulta para buscar producto segun codigo
                String query = "Select Nombre from proveedor";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    cbxRutProveedor.addItem(rs.getString("NOMBRE"));
                }
                conexion.desconectar();

            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void Agregar(){
    try {
        //abrir nueva conexion
        conexion.conectar();
        //armar query
        String Query = "Insert into producto (Codigo, Nombre, Descripcion, Stock, Costo, PorcUtilidad, Valor, Proveedor) values ('" + tfCodigo.getText() + "','" + tfNombre.getText() + "','" + tfDescripcion.getText() + "','" + tfStock.getText() + "','" + tfCosto.getText() + "','" + tfUtilidad.getText() + "','" + tfValor.getText() + "','" + tfProveedor.getText() + "');";
        //ejecutar query
        Statement st1 = cn.createStatement();
        st1.executeUpdate(Query);
        //cerrar conexion
        conexion.desconectar();
        //limpiar tabla
        Limpiar();
        //actualizar
        Cargar();
    } catch (SQLException ex) {
        Logger.getLogger(JIProductos.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public void Limpiar(){
    jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Descripcion", "Stock", "Costo", "Utilidad", "Valor", "Proveedor"
            }
        ));
    }
    
    public void Update(){
    try {
        int unidad = Integer.parseInt(tfNuevos.getText());
        int totalPro = unidad + Integer.parseInt(tfStock.getText());
        //tomar modelo de la tabla
        DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
        //Seleccionar Fila
        int IdSelect = jTable1.getSelectedRow();
        String codigo = m.getValueAt(IdSelect, 0).toString();
        //abrir nueva conexion
        conexion.conectar();
        String Query = "update producto set Nombre ='" + tfNombre.getText() + "', Descripcion ='" + tfDescripcion.getText() + "', Stock ='" + totalPro + "', Costo ='" + tfCosto.getText() + "', PorcUtilidad ='" + tfUtilidad.getText() + "', Valor = '" + tfValor.getText() + "', Proveedor= '" + tfProveedor.getText() + "'  where Codigo ='" + codigo + "';";
        Statement st1 = cn.createStatement();
        st1.executeUpdate(Query);
        //cerrar conexion
        conexion.desconectar();
        Limpiar();
        Cargar();
        LimpiarCampos();
    } catch (SQLException ex) {
        Logger.getLogger(JIProductos.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public void LimpiarCampos()
        {
            this.tfCodigo.setText("");
            this.tfNombre.setText("");
            this.tfDescripcion.setText("");
            this.tfNuevos.setText("");
            this.tfStock.setText("");
            this.tfCosto.setText("");
            this.tfUtilidad.setText("");
            this.tfValor.setText("");
            this.tfCodigo.requestFocus();
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        miEditar = new javax.swing.JMenuItem();
        codigo = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        descripcion = new javax.swing.JLabel();
        nuevos = new javax.swing.JLabel();
        stock = new javax.swing.JLabel();
        costo = new javax.swing.JLabel();
        utilidad = new javax.swing.JLabel();
        valor = new javax.swing.JLabel();
        proveedor1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        Fecha3 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        tfProveedor = new javax.swing.JTextField();
        tfValor = new javax.swing.JTextField();
        tfUtilidad = new javax.swing.JTextField();
        tfCosto = new javax.swing.JTextField();
        tfNuevos = new javax.swing.JTextField();
        tfStock = new javax.swing.JTextField();
        tfBusqueda = new javax.swing.JTextField();
        tfDescripcion = new javax.swing.JTextField();
        tfCodigo = new javax.swing.JTextField();
        tfNombre = new javax.swing.JTextField();
        Fecha4 = new javax.swing.JLabel();
        btnCodigo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiarCampos = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tfNuevos1 = new javax.swing.JTextField();
        nuevos1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        miEditar.setText("Editar");
        miEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(miEditar);

        setBackground(new java.awt.Color(0, 101, 153));
        setTitle("Productos");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        codigo.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        codigo.setForeground(new java.awt.Color(255, 255, 255));
        codigo.setText("CODIGO");
        getContentPane().add(codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 80, -1));

        nombre.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        nombre.setForeground(new java.awt.Color(255, 255, 255));
        nombre.setText("NOMBRE");
        getContentPane().add(nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, -1));

        descripcion.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        descripcion.setForeground(new java.awt.Color(255, 255, 255));
        descripcion.setText("DESCRIPCION");
        getContentPane().add(descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 130, -1));

        nuevos.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        nuevos.setForeground(new java.awt.Color(255, 255, 255));
        nuevos.setText("PRECIO NETO");
        getContentPane().add(nuevos, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 150, 130, -1));

        stock.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        stock.setForeground(new java.awt.Color(255, 255, 255));
        stock.setText("STOCK");
        getContentPane().add(stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 60, -1));

        costo.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        costo.setForeground(new java.awt.Color(255, 255, 255));
        costo.setText("COSTO");
        getContentPane().add(costo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 60, -1));

        utilidad.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        utilidad.setForeground(new java.awt.Color(255, 255, 255));
        utilidad.setText("UTILIDAD");
        getContentPane().add(utilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, 100, -1));

        valor.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        valor.setForeground(new java.awt.Color(255, 255, 255));
        valor.setText("VALOR");
        getContentPane().add(valor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 70, -1));

        proveedor1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        proveedor1.setForeground(new java.awt.Color(255, 255, 255));
        proveedor1.setText("PROVEEDOR");
        getContentPane().add(proveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 120, -1));

        jTable1.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Descripcion", "Stock", "Costo", "Utilidad", "IVA", "Precio Neto", "Valor", "Proveedor"
            }
        ));
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 660, 300));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 650, 10));

        Fecha3.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        Fecha3.setForeground(new java.awt.Color(255, 255, 255));
        Fecha3.setText("BUSQUEDA");
        getContentPane().add(Fecha3, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 310, 210, 40));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 1110, 10));

        tfProveedor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(tfProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 130, -1));

        tfValor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(tfValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 90, -1));

        tfUtilidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfUtilidad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfUtilidadCaretUpdate(evt);
            }
        });
        getContentPane().add(tfUtilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 150, 80, -1));

        tfCosto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(tfCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, 80, -1));

        tfNuevos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfNuevos.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfNuevosCaretUpdate(evt);
            }
        });
        getContentPane().add(tfNuevos, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 150, 80, -1));

        tfStock.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(tfStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 90, -1));

        tfBusqueda.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        tfBusqueda.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfBusquedaCaretUpdate(evt);
            }
        });
        getContentPane().add(tfBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 370, 240, -1));

        tfDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(tfDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 370, -1));

        tfCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(tfCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 90, -1));

        tfNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(tfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 130, -1));

        Fecha4.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        Fecha4.setForeground(new java.awt.Color(255, 255, 255));
        Fecha4.setText("INGRESAR PRODUCTO");
        getContentPane().add(Fecha4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 580, 50));

        btnCodigo.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnCodigo.setForeground(new java.awt.Color(51, 102, 255));
        btnCodigo.setIcon(new javax.swing.ImageIcon("C:\\Users\\toot58\\Desktop\\examen-master\\SistemaVentas\\src\\Recursos\\btnBuscar.png")); // NOI18N
        btnCodigo.setContentAreaFilled(false);
        btnCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoActionPerformed(evt);
            }
        });
        getContentPane().add(btnCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 310, 60, -1));

        btnEditar.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(51, 102, 255));
        btnEditar.setIcon(new javax.swing.ImageIcon("C:\\Users\\toot58\\Desktop\\examen-master\\SistemaVentas\\src\\Recursos\\btnEditar.png")); // NOI18N
        btnEditar.setToolTipText("Editar");
        btnEditar.setContentAreaFilled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 250, 90, 70));

        btnEliminar.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(51, 102, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\toot58\\Desktop\\examen-master\\SistemaVentas\\src\\Recursos\\btnEliminar.png")); // NOI18N
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 390, 90, 70));

        btnLimpiarCampos.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnLimpiarCampos.setForeground(new java.awt.Color(51, 102, 255));
        btnLimpiarCampos.setIcon(new javax.swing.ImageIcon("C:\\Users\\toot58\\Desktop\\examen-master\\SistemaVentas\\src\\Recursos\\btnLimpiar.png")); // NOI18N
        btnLimpiarCampos.setContentAreaFilled(false);
        btnLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarCamposActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpiarCampos, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 320, 90, 70));

        btnAgregar.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(51, 102, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon("C:\\Users\\toot58\\Desktop\\examen-master\\SistemaVentas\\src\\Recursos\\btnAgregar.png")); // NOI18N
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 460, 90, 70));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("AGREGAR");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 480, -1, -1));

        tfNuevos1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfNuevos1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfNuevos1CaretUpdate(evt);
            }
        });
        getContentPane().add(tfNuevos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 150, 80, -1));

        nuevos1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        nuevos1.setForeground(new java.awt.Color(255, 255, 255));
        nuevos1.setText("IVA");
        getContentPane().add(nuevos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 50, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("EDITAR");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 270, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("LIMPIAR");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 340, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ELIMINAR");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 410, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        tfStock.setEnabled(true);
        tfNuevos.setEnabled(false);     
        this.Agregar();
        this.LimpiarCampos();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoActionPerformed
        tfCodigo();
    }//GEN-LAST:event_btnCodigoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        btnAgregar.setEnabled(true);
        btnEditar.setEnabled(false);
        Update();
        timer.start();
    }//GEN-LAST:event_btnEditarActionPerformed
            Timer timer = new Timer (1000, new ActionListener ()
{
    public void actionPerformed(ActionEvent e)
    {
        LimpiarCampos();
        timer.stop();
     }
});
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        //tomar modelo de la tabla
        DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
        //Seleccionar Fila
        int IdSelect = jTable1.getSelectedRow();
        Numero = m.getValueAt(IdSelect, 0).toString();
        
        int confirmado = JOptionPane.showConfirmDialog(this,"¿Desear realmente eliminar este registro?");

         if (JOptionPane.OK_OPTION == confirmado){
            try {
                //abrir conexion
                conexion.conectar();
                //query
                String Query = "delete from producto where Codigo = '" + Numero + "';";
                Statement st = cn.createStatement();
                st.execute(Query);
                //cerrar conexion
                conexion.desconectar();
                Limpiar();
                Cargar();
            } catch (SQLException ex) {
                Logger.getLogger(JIProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
         }else{
            
         }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tfUtilidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfUtilidadCaretUpdate
       if (tfUtilidad.getText().equals(""))
            {
                tfValor.setText("");
            }
            else
            {
                //calcular porcentaje
                int Costo = Integer.parseInt(this.tfCosto.getText());
                int Porc = Integer.parseInt(this.tfUtilidad.getText());
                int Valor = ((Costo * Porc) / 100);
                int Total = (Valor + Costo);
                //mostrar el valor del producto
                tfValor.setText(Integer.toString(Total));
            }
    }//GEN-LAST:event_tfUtilidadCaretUpdate

    private void tfNuevosCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfNuevosCaretUpdate
        if (tfNuevos.getText().equals(""))
            {
                tfNuevos.setText("0");
            }
            else
            {
                //no pasa nada
            }
    }//GEN-LAST:event_tfNuevosCaretUpdate

    private void miEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditarActionPerformed
        tfNuevos.setEnabled(true);
        tfStock.setEnabled(false);
        btnEditar.setEnabled(true);
        btnAgregar.setEnabled(false);
        Editar = true;
        
        //tomar modelo de la tabla
        DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
        //Seleccionar Fila
        int IdSelect = jTable1.getSelectedRow();
        
            this.tfNombre.setText(m.getValueAt(IdSelect, 1).toString());
            this.tfDescripcion.setText(m.getValueAt(IdSelect, 2).toString());
            this.tfStock.setText(m.getValueAt(IdSelect, 3).toString());
            this.tfCosto.setText(m.getValueAt(IdSelect, 4).toString());
            this.tfUtilidad.setText(m.getValueAt(IdSelect, 5).toString());
            this.tfValor.setText(m.getValueAt(IdSelect, 6).toString());
            this.tfProveedor.setText(m.getValueAt(IdSelect, 7).toString());
    }//GEN-LAST:event_miEditarActionPerformed

    private void btnLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarCamposActionPerformed
        LimpiarCampos();
    }//GEN-LAST:event_btnLimpiarCamposActionPerformed

    private void tfBusquedaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfBusquedaCaretUpdate
        Limpiar();
        DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
        try{
            // Conectar a la BD
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.conectar();
            //Consulta a ejecutar
            String     sql = ("SELECT * FROM producto WHERE (Codigo like '%"+tfBusqueda.getText()+"%') or (Nombre like '%"+tfBusqueda.getText()+"%') or (Descripcion like '%"+tfBusqueda.getText()+"%')");
            //Preparar la consulta
            Statement st = con.createStatement();
            //Ejecutar consulta
            ResultSet rs = st.executeQuery(sql);
            //Mostrar los datos
            while(rs.next()){
                String cod = rs.getString("CODIGO");
                String nom = rs.getString("NOMBRE");
                String des = rs.getString("DESCRIPCION");
                String sto = rs.getString("STOCK");
                String cos = rs.getString("COSTO");
                String uti = rs.getString("PORCUTILIDAD");
                String val = rs.getString("VALOR");
                String pro = rs.getString("PROVEEDOR");
                Object fila []  = {cod, nom, des, sto, cos, uti, val ,pro};
                m.addRow(fila);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tfBusquedaCaretUpdate

    private void tfNuevos1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfNuevos1CaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNuevos1CaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha3;
    private javax.swing.JLabel Fecha4;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCodigo;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiarCampos;
    private javax.swing.JLabel codigo;
    private javax.swing.JLabel costo;
    private javax.swing.JLabel descripcion;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem miEditar;
    private javax.swing.JLabel nombre;
    private javax.swing.JLabel nuevos;
    private javax.swing.JLabel nuevos1;
    private javax.swing.JLabel proveedor1;
    private javax.swing.JLabel stock;
    private javax.swing.JTextField tfBusqueda;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfCosto;
    private javax.swing.JTextField tfDescripcion;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfNuevos;
    private javax.swing.JTextField tfNuevos1;
    private javax.swing.JTextField tfProveedor;
    private javax.swing.JTextField tfStock;
    private javax.swing.JTextField tfUtilidad;
    private javax.swing.JTextField tfValor;
    private javax.swing.JLabel utilidad;
    private javax.swing.JLabel valor;
    // End of variables declaration//GEN-END:variables
}
