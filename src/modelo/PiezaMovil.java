
package modelo;

public class PiezaMovil extends Pieza {

    public PiezaMovil(String nombre, int rango, Bando bando) {
        super(nombre, rango, bando);
    }

    @Override
    public boolean puedeMoverse() {
        return true;
    }
}
