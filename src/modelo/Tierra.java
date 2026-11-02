
package modelo;

public class Tierra extends Pieza {

    public Tierra(String nombre, Bando bando) {
        super(nombre, 0, bando);
    }

    @Override
    public boolean puedeMoverse() {
        return false;
    }

    @Override
    public ResultadoCombate recibirAtaque(Pieza atacante) {
        return ResultadoCombate.GANA;
    }
}