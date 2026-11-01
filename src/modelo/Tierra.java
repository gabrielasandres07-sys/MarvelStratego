
package modelo;

public class Tierra extends Pieza {

    public Tierra(String nombre, Bando bando) {
        super(nombre, 0, bando);
    }

    @Override
    public boolean puedeMoverse() {
        return false;
    }
}
