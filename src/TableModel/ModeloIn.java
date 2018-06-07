/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Clases.*;
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
public class ModeloIn extends AbstractTableModel {

    
    ArrayList<Ingreso_Productos> pro;
    String[] encabezado = {"Cantidad(Unidades)","Fecha"};

    public ModeloIn(ArrayList<Ingreso_Productos> pro) {
        this.pro = pro;
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
       
        
        Ingreso_Productos prod = pro.get(x);

        switch (y) {
            
            case 0:

                retorno = ""+(int)prod.getCantidad();

                break;
            case 1:
                retorno = "" + prod.getCodigo_ingreso().getFecha();
                break;
            
            
        }

        return retorno;
    }

}
