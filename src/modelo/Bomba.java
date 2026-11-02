
package modelo;

public class Bomba extends Pieza {

    public Bomba(String nombre, Bando bando) {
        super(nombre, 0, bando);
    }

    @Override
    public boolean puedeMoverse() {
        return false;
    }

    @Override
    public ResultadoCombate recibirAtaque(Pieza atacante) {
        if (atacante.getRango() == 3) {
            return ResultadoCombate.GANA;
        }

        return ResultadoCombate.PIERDE;
    }
}