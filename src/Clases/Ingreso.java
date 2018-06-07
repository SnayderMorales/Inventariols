/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Valentina Morales
 */
public class Ingreso {

    int codigo;
    Date fecha;
    Connections connect = new Connections();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String crear() throws SQLException, ClassNotFoundException {
        
        String consulta = "INSERT INTO INGRESOS"
                + "(FECHA)"
                + "VALUES(NOW())";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.executeUpdate();
        return "Dato creado";
    }

    public String borrar(int i) throws SQLException, ClassNotFoundException {

        String consulta = "DELETE FROM INGRESOS "
                + "WHERE CODIGO_INGRESO=?";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setInt(1, i);
        pst.executeUpdate();
        
        return "Dato borrado";
    }

    public ArrayList<Ingreso> consulta() throws SQLException, ClassNotFoundException {
        ArrayList<Ingreso> ingresos = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        
        String consulta = "SELECT CODIGO_INGRESO, FECHA FROM  INGRESOS;";
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Ingreso ingreso = new Ingreso();
            ingreso.setCodigo(rst.getInt(1));
            ingreso.setFecha(rst.getDate(2));
            ingresos.add(ingreso);
        }
        
        return ingresos;
    }

    public ArrayList<Ingreso> consultaFecha(Date fecha) throws SQLException, ClassNotFoundException {
        ArrayList<Ingreso> ingresos = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT CODIGO_INGRESO, FECHA FROM  INGRESOS "
                + "WHERE FECHA='" + fecha + "'";
        System.out.println(consulta);
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Ingreso ingreso = new Ingreso();
            ingreso.setCodigo(rst.getInt(1));
            ingreso.setFecha(rst.getDate(2));
            ingresos.add(ingreso);
        }
        connect.Cerrar();
        return ingresos;
    }
}
