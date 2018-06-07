/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventario;

/**
 *
 * @author Valentina Morales
 */
import Clases.Connections;
import Clases.Ingreso;
import Clases.Ingreso_Productos;
import Clases.Locker;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import Clases.Producto;

public class Inventario {

    /**
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        
        Producto product = new Producto();
        Producto produc = new Producto();
        product.setCodigo(1);
        product.setDescripcion("hola");
        product.setTotal(10);
        Locker l = new Locker ();
        l.setId(7);
        l.setNumero(20);
        product.setLocker(l);
        produc.actualizar(product);

    }
}
