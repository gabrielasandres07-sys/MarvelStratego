package modelo;

public abstract class Pieza implements Movible, Atacable {

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

    @Override
    public abstract boolean puedeMoverse();

    @Override
    public abstract ResultadoCombate recibirAtaque(Pieza atacante);
}