/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Clases.Producto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jorge
 */
public class Modelo extends AbstractTableModel {

    Producto p;
    ArrayList<Producto> pro;
    String[] encabezado = {"Descripción", "Cantidad(Unidades)", "Locker"};

    public Modelo() {
        p = new Producto();
        try {
            pro = p.consultar();
        } catch (SQLException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getRowCount() {
        
        return pro.size();
    }

    @Override
    public int getColumnCount() {
        return encabezado.length;
    }

    @Override
    public String getColumnName(int x) {
        
        return encabezado[x];
    }

    @Override
    public Object getValueAt(int x, int y) {
        String retorno = "";
       
        
        Producto prod = pro.get(x);

        switch (y) {

            case 0:

                retorno = prod.getDescripcion();

                break;
            case 1:
                retorno = "" + prod.getTotal();
                break;
            case 2:
                retorno = "" + prod.getLocker().numero;
                break;
            
        }

        return retorno;
    }

}
