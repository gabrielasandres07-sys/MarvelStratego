
package modelo;

public class Bomba extends Pieza {

    public Bomba(String nombre, Bando bando) {
        super(nombre, 0, bando);
    }

    @Override
    public boolean puedeMoverse() {
        return false;
    }
}
