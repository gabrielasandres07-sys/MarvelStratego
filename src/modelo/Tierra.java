
package modelo;

public class Tierra extends Pieza {

    public Tierra(Bando bando) {
        super("Tierra", 0, bando);
    }

    @Override
    public boolean puedeMoverse() {
        return false;
    }
}
