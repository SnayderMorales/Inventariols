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
import javax.swing.JOptionPane;

/**
 *
 * @author Valentina Morales
 */
public class Producto {

    int codigo;
    String descripcion;
    int total;
    Locker locker;
    Connections connect = new Connections();

    public Connections getConnect() {
        return connect;
    }
    ArrayList<Producto> pro;

    public int getCodigo() {
        return codigo;
    }

    public Producto() {
    }

    public Producto(int codigo, String descripcion, int total, Locker locker) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.total = total;
        this.locker = locker;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public void actualizar(Producto p) throws SQLException, ClassNotFoundException {

        //UPDATE `productos` SET `descripcion`="sas",`total` = 10,`locker_codigo` = 7 WHERE codigo = 1
        String consulta = "UPDATE productos SET descripcion= ?, total =? , locker_codigo =?  WHERE codigo = ?";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setString(1, p.getDescripcion());
        pst.setInt(2, p.getTotal());
        pst.setInt(3, p.getLocker().getId());
        pst.setInt(4, p.getCodigo());
        pst.executeUpdate();

        JOptionPane.showMessageDialog(null, "Dato Actualizado Correctamente");
    }

    public void crear(Producto datos) throws SQLException, ClassNotFoundException {
        String consulta = "INSERT INTO PRODUCTOS"
                + "(DESCRIPCION, TOTAL, LOCKER_CODIGO)"
                + "VALUES(?,?,?)";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setString(1, datos.getDescripcion());
        pst.setInt(2, 0);
        pst.setInt(3, datos.getLocker().getId());
        pst.executeUpdate();

        JOptionPane.showMessageDialog(null, "Dato Creado Correctamente");
    }

    public void crearLocker(Locker datos) throws SQLException, ClassNotFoundException {
        String consulta = "INSERT INTO LOCKER"
                + "(NUMERO)"
                + "VALUES(?)";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setInt(1, datos.getNumero());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Dato Creado Correctamente");
    }

    @Override
    public String toString() {
        return "Producto{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", total=" + total + ", locker=" + locker + '}';
    }

    public Producto consultar(String descripcion) throws SQLException, ClassNotFoundException {
        Producto product = new Producto();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT productos.CODIGO, DESCRIPCION, TOTAL,locker_codigo ,locker.numero "
                + "FROM productos INNER JOIN locker WHERE DESCRIPCION=" + descripcion + " AND locker_codigo = locker.codigo";
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        product.setCodigo(rst.getInt(1));
        product.setDescripcion(rst.getString(2));
        product.setTotal(rst.getInt(3));
        product.setLocker(new Locker(rst.getInt(4), rst.getInt(5)));

        return product;
    }

    public ArrayList<Producto> consultar() throws SQLException, ClassNotFoundException {
        Connections connect = new Connections();
        ArrayList<Producto> pro = new ArrayList<Producto>();

        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT productos.codigo, productos.descripcion, productos.total, "
                + "productos.locker_codigo,locker.numero "
                + "FROM productos INNER JOIN locker ON productos.locker_codigo =  locker.codigo";
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {

            pro.add(new Producto(rst.getInt(1), rst.getString(2), rst.getInt(3), new Locker(rst.getInt(4), rst.getInt(5))));
        }

        return pro;
    }

    public ArrayList<Locker> ListLocker() throws SQLException, ClassNotFoundException {
        Connections connect = new Connections();
        ArrayList<Locker> lockers = new ArrayList<Locker>();

        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT * FROM locker";
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            lockers.add(new Locker(rst.getInt(1), rst.getInt(2)));
        }

        return lockers;
    }

    public Producto consultarDesc(int codigo) throws SQLException, ClassNotFoundException {
        Producto product = new Producto();
        Locker lock = new Locker();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT CODIGO, DESCRIPCION, "
                + "TOTAL, LOCKER_CODIGO FROM PRODUCTOS WHERE CODIGO=" + codigo;
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            product.setCodigo(rst.getInt(1));
            product.setDescripcion(rst.getString(2));
            product.setTotal(rst.getInt(3));
            lock.setNumero(rst.getInt(4));
            product.setLocker(lock);
        }

        return product;
    }

    public ArrayList<Producto> listar() throws SQLException, ClassNotFoundException {
        ArrayList<Producto> product = new ArrayList<>();
        Locker lock = new Locker();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT CODIGO, DESCRIPCION, TOTAL, LOCKER_CODIGO"
                + "FROM PRODUCTOS";

        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Producto produc = new Producto();
            produc.setCodigo(rst.getInt(1));
            produc.setDescripcion(rst.getString(2));
            produc.setTotal(rst.getInt(3));
            lock.setNumero(rst.getInt(4));
            produc.setLocker(lock);
            product.add(produc);
        }

        return product;
    }

}
