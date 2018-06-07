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
public class Salida {
    int codigo;
    String instructor, ficha;
    Date fecha;
    Connections connect = new Connections();

    public Salida(int codigo, String instructor, String ficha, Date fecha) {
        this.codigo = codigo;
        this.instructor = instructor;
        this.ficha = ficha;
        this.fecha = fecha;
    }

    public Salida() {
    }

    public Salida(String instructor, String ficha) {
        this.instructor = instructor;
        this.ficha = ficha;
    }

    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Connections getConnect() {
        return connect;
    }

    public void setConnect(Connections connect) {
        this.connect = connect;
    }

    
    public String crear(Salida datos) throws SQLException, ClassNotFoundException{
        String consulta = "INSERT INTO SALIDAS"
                +"(FECHA, INSTRUCTOR, FICHA)"
                + "VALUES(NOW(),?,?)";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setString(1, datos.getInstructor());
        pst.setString(2, datos.getFicha());
        pst.executeUpdate();
       
        return "Dato creado";
    }
    
    public String borrar(int i) throws SQLException, ClassNotFoundException {
        String consulta = "DELETE FROM SALIDAS "
                + "WHERE CODIGO_SALIDA=?";
        Connection cn = connect.Conectar();
        PreparedStatement pst = cn.prepareStatement(consulta);
        pst.setInt(1, i);
        pst.executeUpdate();
        connect.Cerrar();
        return "Dato borrado";
    }
    
    public ArrayList<Salida> consulta() throws SQLException, ClassNotFoundException {
        ArrayList<Salida> salida = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT CODIGO_SALIDA, INSTRUCTOR, FICHA, FECHA FROM SALIDAS;";
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Salida salid = new Salida();
            salid.setCodigo(rst.getInt(1));
            salid.setInstructor(rst.getString(2));
            salid.setFicha(rst.getString(3));
            salid.setFecha(rst.getDate(4));
            salida.add(salid);
        }
        
        return salida;
    }
    
    public ArrayList<Salida> consultaFecha(Date fecha) throws SQLException, ClassNotFoundException {
        ArrayList<Salida> salidas = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT CODIGO_SALIDA, INSTRUCTOR, FICHA, FECHA FROM  SALIDAS  "
                + "WHERE FECHA='"+fecha+"'";
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        while (rst.next()) {
            Salida salid = new Salida();
            salid.setCodigo(rst.getInt(1));
            salid.setInstructor(rst.getString(2));
            salid.setFicha(rst.getString(3));
            salid.setFecha(rst.getDate(4));
            salidas.add(salid);
        }
        return salidas;
    }
    
    public ResultSet consultaInstructor(String instructor) throws SQLException, ClassNotFoundException {
        ArrayList<Salida> salidas = new ArrayList<>();
        ArrayList<Object> sal = new ArrayList<>();
        ResultSet rst = null;
        Statement stmt = null;
        String consulta = "SELECT FECHA, FICHA, DESCRIPCION, CANTIDA FROM  SALIDAS  "
                + "INNER JOIN SALIDAS_PRODUCTOS ON CODIGO_SALIDA=salidas_codigo_salida "
                + "INNER JOIN productos ON productos_codigo=CODIGO "
                + "WHERE instructor like '"+instructor+"'";
        System.out.println(consulta);
        stmt = (Statement) connect.Conectar().createStatement();
        rst = stmt.executeQuery(consulta);
        /*while (rst.next()) {
            Salida salid = new Salida();
            salid.setFecha(rst.getDate(1));
            salid.setFicha(rst.getString(2));           
            salidas.add(salid);
        }*/
        return rst;
    }
}
