/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rojinegro;

/**
 *
 * @author ARIEL JOSE
 */
public class RojiNegro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolRojiNegro bst = new ArbolRojiNegro();
        bst.insertarNodo(8);
    	bst.insertarNodo(18);
    	bst.insertarNodo(5);
    	bst.insertarNodo(15);
    	bst.insertarNodo(17);
    	bst.insertarNodo(25);
    	bst.insertarNodo(40);
    	bst.insertarNodo(80);
        bst.eliminarNodo(8);
        bst.verArbolRojiNegro();
    }
    
}
