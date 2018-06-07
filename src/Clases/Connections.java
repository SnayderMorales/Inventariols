/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Valentina Morales
 */
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
public class Connections {
   private static Connection cnx = null;
   
   public Connection Conectar() throws SQLException, ClassNotFoundException {
      if (cnx == null) {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/inventario", "root", "");
            System.out.println("Conecxion correcta");
         } catch (SQLException ex) {
            throw new SQLException(ex);
         } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
         }
      }
      return cnx;
   }
   
   public void Cerrar() throws SQLException {
      if (cnx != null) {
         cnx.close();
      }
   } 
}
