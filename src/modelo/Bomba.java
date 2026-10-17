
package modelo;

public class Bomba extends Pieza {

    public Bomba(Bando bando) {
        super("Bomba", 0, bando);
    }

    @Override
    public boolean puedeMoverse() {
        return false;
    }
}
