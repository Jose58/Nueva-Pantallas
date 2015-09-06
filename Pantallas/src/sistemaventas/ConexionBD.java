/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaventas;
import java.sql.*;
/**
 *
 * @author jhonny
 */
public class ConexionBD {
    /**Parametros de conexion*/
   static String bd = "examen";
   static String login = "root";
   static String password = "c4ever";
   static String url = "jdbc:mysql://localhost/"+bd;
 
   Connection connection = null;
 
   /** Constructor de DbConnection */
   public ConexionBD() {
      try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         connection = DriverManager.getConnection(url,login,password);
 
         if (connection!=null){
            System.out.println("Conexión a base de datos "+bd+" OK\n");
         }
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }
   }
   /**Permite retornar la conexión*/
   public Connection conectar(){
      return connection;
   }
 
   public void desconectar(){
      connection = null;
   }
}
