/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Valentina Morales
 */
public class Ingreso_Productos {

    Ingreso codigo_ingreso;
    Producto codigo_producto;
    double cantidad;
    Connections connect = new Connections();

    public Ingreso_Productos() {
    }

    public Ingreso_Productos(Ingreso codigo_ingreso, Producto codigo_producto, double cantidad) {
        this.codigo_ingreso = codigo_ingreso;
        this.codigo_producto = codigo_producto;
        this.cantidad = cantidad;
    }

    

    public Ingreso getCodigo_ingreso() {
        return codigo_ingreso;
    }

    public void setCodigo_ingreso(Ingreso codigo_ingreso) {
        this.codigo_ingreso = codigo_ingreso;
    }

    public Producto getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(Producto codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String crear(Ingreso_Productos datos) throws SQLException, ClassNotFoundException {
        String consulta = "INSERT INTO INGRESOS_PRODUCTOS"
                + "(ingresos_codigo_ingreso, productos_codigo, cantidad)"
                + "VALUES(?,?,?);";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setInt(1, datos.getCodigo_ingreso().getCodigo());
        pst.setInt(2, datos.getCodigo_producto().getCodigo());
        pst.setDouble(3, datos.getCantidad());
        pst.executeUpdate();
        String consulta2 = "UPDATE PRODUCTOS "
                + "SET TOTAL=TOTAL+? WHERE CODIGO =?;";
        PreparedStatement pst1 = cn.prepareStatement(consulta2);
        pst1.setDouble(1, datos.getCantidad());
        pst1.setInt(2, datos.getCodigo_producto().getCodigo());
        pst1.executeUpdate();

        return "Dato creado";
    }

    public String borrar(int producto_id, int ingreso_id) throws SQLException, ClassNotFoundException {
        String consulta2 = "UPDATE PRODUCTOS "
                + "SET TOTAL=TOTAL-(SELECT CANTIDAD FROM INGRESOS_PRODUCTOS "
                + "WHERE INGRESOS_CODIGO_INGRESO=? "
                + "AND PRODUCTOS_CODIGO=? ) WHERE CODIGO =?;";
        String consulta = "DELETE FROM INGRESOS_PRODUCTOS "
                + "WHERE INGRESOS_CODIGO_INGRESO=? "
                + "AND PRODUCTOS_CODIGO=?";
        Connection cn = connect.Conectar();
        PreparedStatement pst1 = cn.prepareStatement(consulta2);
        pst1.setInt(1, ingreso_id);
        pst1.setInt(2, producto_id);
        pst1.setInt(3, producto_id);
        System.out.println(consulta2);
        pst1.executeUpdate();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setInt(1, ingreso_id);
        pst.setInt(2, producto_id);
        pst.executeUpdate();
        connect.Cerrar();
        return "Dato borrado";
    }

    public ArrayList<Ingreso_Productos> consultaIngreso(int ingreso_id) throws SQLException, ClassNotFoundException {
        ArrayList<Ingreso_Productos> ing_pro = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT ING.CODIGO_INGRESO, ING.FECHA, INGPRO.CANTIDAD,\n"
                + "PRO.CODIGO, PRO.DESCRIPCION, PRO.TOTAL FROM  INGRESOS ING\n"
                + "INNER JOIN INGRESOS_PRODUCTOS INGPRO ON CODIGO_INGRESO=INGRESOS_CODIGO_INGRESO\n"
                + "INNER JOIN PRODUCTOS PRO ON PRODUCTOS_CODIGO=CODIGO\n"
                + "WHERE CODIGO_INGRESO=" + ingreso_id;

        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Ingreso_Productos ingr = new Ingreso_Productos();
            Ingreso ingreso = new Ingreso();
            Producto produc = new Producto();
            ingreso.setCodigo(rst.getInt(1));
            ingreso.setFecha(rst.getDate(2));
            ingr.setCantidad(rst.getDouble(3));
            produc.setCodigo(rst.getInt(4));
            produc.setDescripcion(rst.getString(5));
            ingr.setCodigo_ingreso(ingreso);
            ingr.setCodigo_producto(produc);
            ing_pro.add(ingr);
        }
        
        return ing_pro;
    }
    public ArrayList<Ingreso_Productos> listar() throws SQLException, ClassNotFoundException {
        ArrayList<Ingreso_Productos> ing_pro = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT ING.CODIGO_INGRESO, ING.FECHA, INGPRO.CANTIDAD,PRO.CODIGO, PRO.DESCRIPCION, "
                + "PRO.TOTAL FROM  INGRESOS ING INNER JOIN INGRESOS_PRODUCTOS "
                + "INGPRO ON CODIGO_INGRESO=INGRESOS_CODIGO_INGRESO INNER JOIN PRODUCTOS PRO ON "
                + "PRODUCTOS_CODIGO=CODIGO";

        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Ingreso_Productos ingr = new Ingreso_Productos();
            Ingreso ingreso = new Ingreso();
            Producto produc = new Producto();
            ingreso.setCodigo(rst.getInt(1));
            ingreso.setFecha(rst.getDate(2));
            ingr.setCantidad(rst.getDouble(3));
            produc.setCodigo(rst.getInt(4));
            produc.setDescripcion(rst.getString(5));
            ingr.setCodigo_ingreso(ingreso);
            ingr.setCodigo_producto(produc);
            ing_pro.add(ingr);
        }
       
        return ing_pro;
    }
    //metodo consulta ingresos de un producto
    public ArrayList<Ingreso_Productos> consultaIngresoPro(int producto_id) throws SQLException, ClassNotFoundException {
        ArrayList<Ingreso_Productos> ing_pro = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT ING.CODIGO_INGRESO, ING.FECHA, INGPRO.CANTIDAD,\n"
                + "PRO.CODIGO, PRO.DESCRIPCION, PRO.TOTAL FROM  INGRESOS ING\n"
                + "INNER JOIN INGRESOS_PRODUCTOS INGPRO ON CODIGO_INGRESO=INGRESOS_CODIGO_INGRESO\n"
                + "INNER JOIN PRODUCTOS PRO ON PRODUCTOS_CODIGO=CODIGO\n"
                + "WHERE PRODUCTOS_CODIGO=" + producto_id;

        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Ingreso_Productos ingr = new Ingreso_Productos();
            Ingreso ingreso = new Ingreso();
            Producto produc = new Producto();
            ingreso.setCodigo(rst.getInt(1));
            ingreso.setFecha(rst.getDate(2));
            ingr.setCantidad(rst.getDouble(3));
            produc.setCodigo(rst.getInt(4));
            produc.setDescripcion(rst.getString(5));
            ingr.setCodigo_ingreso(ingreso);
            ingr.setCodigo_producto(produc);
            ing_pro.add(ingr);
        }
        connect.Cerrar();
        return ing_pro;
    }
}
