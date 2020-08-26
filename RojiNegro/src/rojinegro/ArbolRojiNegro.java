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
public class ArbolRojiNegro {
    
    private NodoArbol raiz;
    private NodoArbol nil;
    
    
    //constructor
    public ArbolRojiNegro(){
        nil = new NodoArbol();
        nil.color = 0;
        nil.izquierda = null;
        nil.derecha = null;
        raiz = nil;
    }
    
    
    /**
     * Busca un nodo en el arbol
     * @param nodo raiz
     * @param dato dato del nodo a buscar
     * @return el nodo que contiene el dato
     */
    public NodoArbol buscarNodo(NodoArbol nodo, int dato){
        if(nodo == nil || dato == nodo.dato){
            return nodo;
        }
        if(dato < nodo.dato){
            return buscarNodo(nodo.izquierda, dato);
        }
        return buscarNodo(nodo.derecha, dato);
    }
    
    /**
     * Insertar nodo al arbol
     * @param dato numero a insertar
     */
    
    public void insertarNodo(int dato){
        NodoArbol nodo =  new NodoArbol();
        nodo.padre = null;
        nodo.dato = dato;
        nodo.derecha = nil;
        nodo.izquierda = nil;
        nodo.color = 1;
        
        NodoArbol y = null;
        NodoArbol x = this.raiz;
        

        while (x != nil){
            y = x;
            if(nodo.dato < x.dato){                
                x = x.izquierda;               
            } else {
                x = x.derecha;
            }
        }
        
        // y padre de x
        nodo.padre = y;
        if(y == null){
            this.raiz = nodo;
        } else if (nodo.dato < y.dato){
            y.izquierda = nodo;
        } else {
            y.derecha = nodo;
        }
        
        if (nodo.padre == null){
            nodo.color = 0;
            return;
        }
        
        if (nodo.padre.padre == null){
            return;
        }
        
        verificarInsertar(nodo);
        
        
    }
    
     /**
     * Verifica el coloreado del arbol al insertar un nodo
     * @param k nodo insertado
     */
    
    public void verificarInsertar(NodoArbol k){
        NodoArbol t; //tio
        while(k.padre.color == 1){
            if(k.padre == k.padre.padre.derecha){
                t = k.padre.padre.izquierda; // tio
                if (t.color == 1){
                    //caso 1
                    t.color = 0;
                    k.padre.color = 0;
                    k.padre.padre.color = 1;
                    k = k.padre.padre;
                    
                } else {
                    if(k == k.padre.izquierda){
                        // caso 2
                        k = k.padre;
                        rotarDerecha(k);
                    }
                    
                    // caso 3
                    k.padre.color = 0;
                    k.padre.padre.color = 1;
                    rotarIzquierda(k.padre.padre);
                }
            } else {
                
                t = k.padre.padre.derecha; // tio
                
                if (t.color == 1) {
                    // caso 1 al otro lado
                    t.color = 0;
                    k.padre.color = 0;
                    k.padre.padre.color = 1;
                    k = k.padre.padre;
                } else {
                    if(k == k.padre.derecha){
                        // caso 2 al otro lado 
                        k = k.padre;
                        rotarIzquierda(k);
                    }
                    // caso 3 al otro lado 
                    k.padre.color = 0;
                    k.padre.padre.color = 1;
                    rotarDerecha(k.padre.padre);
                            
                }
            }
            if (k == raiz){
                break;
            }
            
        }
        raiz.color = 0;
    }
    
    
    /**
     * Metodo que elimina un nodo del arbol
     * @param nodo raiz
     * @param dato dato del nodo que sera eliminado
    */
    
    public void eliminar(NodoArbol nodo, int dato) {
        NodoArbol z = nil;
        NodoArbol x,y;
        z = nodo;
        while (nodo != nil && nodo != null) {
            if (nodo.dato == dato) {
                z = nodo;
            }

            if (nodo.dato <= dato) {
                nodo = nodo.derecha;
            } else {
                nodo = nodo.izquierda;
            }   
        }
        
        if (z == nil){
            System.out.println("No encontrado");
            return;
        }
        
        y = z;
        int colorOriginalY = y.color;
        if(z.izquierda == nil){
            x = z.derecha;
            intercambio(z, z.derecha);           
        } else if (z.derecha == nil){
            x = z.izquierda;
            intercambio(z, z.izquierda);
        } else {
            y = nodoMinimo(z.derecha);
            colorOriginalY = y.color;
            x = y.derecha;
            if(y.padre == z) {
                x.padre = y;                
            } else {
                intercambio(y, y.derecha);
                y.derecha = z.derecha;
                y.derecha.padre = y;
            }
            
            intercambio(z, y);
            y.izquierda = z.izquierda;
            y.izquierda.padre = y;
            y.color = z.color;
        }
        if (colorOriginalY == 0){
            verificarEliminacion(x);
        }
        
    }
    
   
    private void intercambio(NodoArbol u, NodoArbol v){
        if (u.padre == null) {
          raiz = v;
        } else if (u == u.padre.izquierda){
          u.padre.izquierda = v;
        } else {
          u.padre.derecha = v;
        }
        v.padre = u.padre;
    }
    
    /**
     * Encuentra el nodo minimo de un subarbol
     * @param nodo raiz del subarbol
     * @return nodo minimo del subarbol
     */
    public NodoArbol nodoMinimo(NodoArbol nodo) {
        while (nodo.izquierda != nil) {
          nodo = nodo.izquierda;
        }
        return nodo;
	  }
    
    /**
     * Verifica el coloreado del arbol despues de eliminar un nodo
     * @param x nodo 
     */
    public void verificarEliminacion (NodoArbol x){
        NodoArbol s;
        while(x != raiz && x.color == 0){
            if (x == x.padre.izquierda){
                s = x.padre.derecha;
                if (s.color == 1){
                    // caso 1
                    s.color = 0;
                    x.padre.color = 1;
                    rotarIzquierda(x.padre);
                    s = x.padre.derecha;
                    
                }
                
                if(s.izquierda.color == 0 && s.derecha.color == 0){
                    //caso 2
                    s.color = 1;
                    x = x.padre;
                } else {
                    if(s.derecha.color == 0){
                        // caso 3
                        s.izquierda.color = 0;
                        s.color = 1;
                        rotarDerecha(s);
                        s = x.padre.derecha;
                        
                    }
                    
                    // caso 4
                    s.color = x.padre.color;
                    x.padre.color = 0;
                    s.derecha.color = 0;
                    rotarIzquierda(x.padre);
                    x = raiz;
                }
            } else {
                s = x.padre.izquierda;
                if (s.color == 1){
                    //caso 5
                    s.color = 0;
                    x.padre.color = 1;
                    rotarDerecha(x.padre);
                    s = x.padre.izquierda;
                }
                
                if (s.derecha.color == 0 && s.derecha.color == 0){
                    // caso 6
                    s.color = 1;
                    x = x.padre;
                } else {
                    if(s.izquierda.color == 0){
                        //caso 7
                        s.derecha.color = 0;
                        s.color = 1;
                        rotarIzquierda(s);
                        s = x.padre.izquierda;
                    }
                    //caso 8
                    s.color = x.padre.color;
                    x.padre.color = 0;
                    s.izquierda.color = 0;
                    rotarDerecha(x.padre);
                    x = raiz;
                }               
            }
        }
        x.color = 0;
    }
    
    
    
    
    
    /**
     * Rota a la derecha un subarbol
     * @param x raiz
     */
    public void rotarDerecha(NodoArbol x){
        NodoArbol y = x.izquierda;
        x.izquierda = y.derecha;
        if (y.derecha != nil){
            y.derecha.padre = x;
        }
        y.padre = x.padre;
        if (x.padre == null){
            this.raiz = y;
        } else if (x == x.padre.derecha){
            x.padre.derecha = y;            
        } else {
            x.padre.izquierda = y;
        }
        
        y.derecha = x;
        x.padre = y;
    }
    
    /**
     * Rota a la izquierda un subarbol
     * @param x raiz
     */
    public void rotarIzquierda(NodoArbol x){
        NodoArbol y = x.derecha;
        x.derecha = y.izquierda;
        if (y.izquierda != nil){
            y.izquierda.padre = x;            
        }
        y.padre = x.padre;
        if(x.padre == null){
            this.raiz = y;
        } else if (x == x.padre.izquierda){
            x.padre.izquierda = y;
        } else {
            x.padre.derecha = y;
        }
        
        y.izquierda = x;
        x.padre = y;
       
    }
    
    /**
     * Recorrido preorden del arbol
     * @param raiz raiz del arbol
     */
    private void preorden(NodoArbol raiz){
        if(raiz != nil){
            System.out.print(raiz.dato + " ");
            preorden(raiz.izquierda);
            preorden(raiz.derecha);
        }
    }
    
    /**
     * Recorrido inorden del arbol
     * @param raiz raiz del arbol
     */
    private void inorden(NodoArbol raiz){
        if(raiz != nil){
            inorden(raiz.izquierda);
            System.out.print(raiz.dato + " ");
            inorden(raiz.derecha);
        }
    }
    
    /**
     * Recorrido posorden del arbol
     * @param raiz raiz del arbol
     */
    private void posorden(NodoArbol raiz){
        if(raiz != nil){
            posorden(raiz.izquierda);
            posorden(raiz.derecha);
            System.out.print(raiz.dato + " ");
            
        }
    }
    
    /**
     * Invoca al recorrido preorden
     */
    public void preOrden(){
        preorden(this.raiz);
    }
    
    /**
     * Invoca al recorrido inorden
     */
    public void inOrden(){
        inorden(this.raiz);        
    }
    
    /**
     * Invoca al recorrido posorden
     */
    public void posOrden(){
        posorden(this.raiz);
    }
    
    /**
     * Retorna la raiz del arbol
     * @return raiz del arbol
     */
    public NodoArbol getRaiz(){
        return this.raiz;
    }
    
    /**
     * Metodo que invoca la eliminacion
     * @param dato dato a eliminar
    */
    public void eliminarNodo(int dato){
        eliminar(this.raiz, dato);
    }
   
    /**
     * Imprime el arbol con guias visuales del color y ubicacion de los nodos
     * @param raiz raiz del subarbol
     * @param espacio cadena que almacena caracteres
     * @param lado dependiendo de su valor se almacenan caracteres en la cadena espacio
     */
    private void verArbolRojiNegro(NodoArbol raiz, String espacio, boolean lado) {
	   	if (raiz != nil) {
		   System.out.print(espacio);
		   if (lado) {
		      System.out.print("D----");
		      espacio += "     ";
		   } else {
		      System.out.print("I----");
		      espacio += "|    ";
		   }
            
           String colorNodo = raiz.color == 1?"ROJO":"NEGRO";
		   System.out.println(raiz.dato + "(" + colorNodo + ")");
		   verArbolRojiNegro(raiz.izquierda, espacio, false);
		   verArbolRojiNegro(raiz.derecha, espacio, true);
		}
	}
    
    /**
     * Metodo que permite visuzalizar el arbol roji negro
     */
    public void verArbolRojiNegro(){
        verArbolRojiNegro(this.raiz, "", true);
        
    }
    
}

