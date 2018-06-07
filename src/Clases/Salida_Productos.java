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
public class Salida_Productos {
    Salida codigo_salida;
    Producto codigo_producto;
    double cantidad;
    Connections connect = new Connections();

    public Salida_Productos() {
    }
    

    public Salida_Productos(Salida codigo_salida, Producto codigo_producto, double cantidad) {
        this.codigo_salida = codigo_salida;
        this.codigo_producto = codigo_producto;
        this.cantidad = cantidad;
    }
    

    public Salida getCodigo_salida() {
        return codigo_salida;
    }

    public void setCodigo_salida(Salida codigo_ingreso) {
        this.codigo_salida = codigo_ingreso;
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

    public String crear(Salida_Productos datos) throws SQLException, ClassNotFoundException{
        String consulta = "INSERT INTO SALIDAS_PRODUCTOS"
                +"(PRODUCTOS_CODIGO, SALIDAS_CODIGO_SALIDA, CANTIDA)"
                + "VALUES(?,?,?)";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setInt(1, datos.getCodigo_producto().getCodigo());
        pst.setInt(2, datos.getCodigo_salida().getCodigo());
        pst.setDouble(3, datos.getCantidad());
        pst.executeUpdate();
        String consulta2="UPDATE PRODUCTOS "
                + "SET TOTAL=TOTAL-? WHERE CODIGO =?;";
        PreparedStatement pst1 = cn.prepareStatement(consulta2);
        pst1.setDouble(1, datos.getCantidad());
        pst1.setInt(2, datos.getCodigo_producto().getCodigo());
        pst1.executeUpdate();
        
       
        return "Dato creado";
    }
    
    public String borrar(int producto_id, int salida_id) throws SQLException, ClassNotFoundException {
        String consulta2="UPDATE PRODUCTOS "
                + "SET TOTAL=TOTAL+(SELECT CANTIDAD FROM SALIDAS_PRODUCTOS "
                + "WHERE salidas_codigo_salida=? "
                + "AND PRODUCTOS_CODIGO=? ) WHERE CODIGO =?;";
        String consulta = "DELETE FROM salidas_codigo_salida "
                + "WHERE salidas_codigo_salida=? "
                + "AND PRODUCTOS_CODIGO=?";
        Connection cn = connect.Conectar();
        PreparedStatement pst1 = cn.prepareStatement(consulta2);
        pst1.setInt(1, salida_id);
        pst1.setInt(2, producto_id);
        pst1.setInt(3, producto_id);
        System.out.println(consulta2);
        pst1.executeUpdate();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setInt(1, salida_id);
        pst.setInt(2, producto_id);
        pst.executeUpdate();       
        
        return "Dato borrado";
    }
    
    public ArrayList<Salida_Productos> consultaSalida() throws SQLException, ClassNotFoundException {
        ArrayList<Salida_Productos> sal_pro = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT SAL.CODIGO_SALIDA, SAL.INSTRUCTOR, SAL.FICHA, SAL.FECHA, SALPRO.CANTIDA,\n"
                + "PRO.CODIGO, PRO.DESCRIPCION, PRO.TOTAL FROM  SALIDAS SAL\n"
                + "INNER JOIN SALIDAS_PRODUCTOS SALPRO ON CODIGO_SALIDA=SALIDAS_CODIGO_SALIDA\n"
                + "INNER JOIN PRODUCTOS PRO ON PRODUCTOS_CODIGO=CODIGO";

        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Salida_Productos salidas = new Salida_Productos();
            Salida salida = new Salida();
            Producto produc = new Producto();
            salida.setCodigo(rst.getInt(1));          
            salida.setInstructor(rst.getString(2));
            salida.setFicha(rst.getString(3));
            salida.setFecha(rst.getDate(4));
            produc.setCodigo(rst.getInt(6));
            produc.setDescripcion(rst.getString(7));
            produc.setTotal(rst.getInt(8));
            salidas.setCodigo_producto(produc);
            salidas.setCodigo_salida(salida);
            salidas.setCantidad(rst.getDouble(5));
            sal_pro.add(salidas);
        }
        
        return sal_pro;
    }
    //metodo consulta salidas de un producto
    public ArrayList<Salida_Productos> consultaSalidaPro(int producto_id) throws SQLException, ClassNotFoundException {
        ArrayList<Salida_Productos> sal_pro = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT SAL.CODIGO_SALIDA, SAL.INSTRUCTOR, SAL.FICHA, SAL.FECHA, SALPRO.CANTIDA,\n"
                + "PRO.CODIGO, PRO.DESCRIPCION, PRO.TOTAL FROM  SALIDAS SAL\n"
                + "INNER JOIN SALIDAS_PRODUCTOS SALPRO ON CODIGO_SALIDA=SALIDAS_CODIGO_SALIDA\n"
                + "INNER JOIN PRODUCTOS PRO ON PRODUCTOS_CODIGO=CODIGO\n"
                + "WHERE PRODUCTO_CODIGO=" + producto_id;

        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Salida_Productos salidas = new Salida_Productos();
            Salida salida = new Salida();
            Producto produc = new Producto();
            salida.setCodigo(rst.getInt(1));          
            salida.setInstructor(rst.getString(2));
            salida.setFicha(rst.getString(3));
            salida.setFecha(rst.getDate(4));
            produc.setCodigo(rst.getInt(6));
            produc.setDescripcion(rst.getString(7));
            produc.setTotal(rst.getInt(8));
            salidas.setCodigo_producto(produc);
            salidas.setCodigo_salida(salida);
            salidas.setCantidad(rst.getDouble(5));
            sal_pro.add(salidas);
        }
       
        return sal_pro;
    }
}
