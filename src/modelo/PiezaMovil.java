
package modelo;

public class PiezaMovil extends Pieza {

    public PiezaMovil(String nombre, int rango, Bando bando) {
        super(nombre, rango, bando);
    }

    @Override
    public boolean puedeMoverse() {
        return true;
    }

    @Override
    public ResultadoCombate recibirAtaque(Pieza atacante) {
        if (atacante.getRango() == 1 && getRango() == 10) {
            return ResultadoCombate.GANA;
        }

        if (atacante.getRango() > getRango()) {
            return ResultadoCombate.GANA;
        }

        if (atacante.getRango() < getRango()) {
            return ResultadoCombate.PIERDE;
        }

        return ResultadoCombate.EMPATE;
    }
}