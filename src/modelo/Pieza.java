
package modelo;

public abstract class Pieza {

    private String nombre;
    private int rango;
    private Bando bando;
    private boolean revelada;

    public Pieza(String nombre, int rango, Bando bando) {
        this.nombre = nombre;
        this.rango = rango;
        this.bando = bando;
        this.revelada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getRango() {
        return rango;
    }

    public Bando getBando() {
        return bando;
    }

    public boolean isRevelada() {
        return revelada;
    }

    public void revelar() {
        revelada = true;
    }

    public abstract boolean puedeMoverse();
}
