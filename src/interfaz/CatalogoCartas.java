/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author USER
 */


import java.util.HashMap;
import java.util.Map;
import modelo.Bando;
import modelo.Personaje;


public class CatalogoCartas {

    private static final String CARPETA = "/recursos/CartasTa/";

    private final Map<String, CartaTablero> cartas = new HashMap<>();

    public CatalogoCartas() {
        
        for (Personaje p : Personaje.values()) {
            cartas.put(clave(p.getNombre(), p.getBando()),
                new CartaTablero(p.getNombre(), CARPETA + p.getImagen()));
        }
    }

    
    public CartaTablero obtener(String nombre, Bando bando) {
        return cartas.get(clave(nombre, bando));
    }

    private static String clave(String nombre, Bando bando) {
        return bando + ":" + nombre;
    }

    public int cantidad() {
        return cartas.size();
    }
}