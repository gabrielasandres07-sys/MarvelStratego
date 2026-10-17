
package modelo;

public class Tablero {

    public static final int FILAS = 10;
    public static final int COLUMNAS = 10;

    private final Pieza[][] casillas;

    public Tablero() {
        casillas = new Pieza[FILAS][COLUMNAS];
    }

    public Pieza obtenerPieza(int fila, int columna) {
        validarPosicion(fila, columna);
        return casillas[fila][columna];
    }

    public void colocarPieza(Pieza pieza, int fila, int columna) {
        validarPosicion(fila, columna);

        if (pieza == null) {
            throw new IllegalArgumentException(
                    "La pieza no puede ser nula.");
        }

        if (esZonaProhibida(fila, columna)) {
            throw new IllegalArgumentException(
                    "No se puede colocar una pieza en esa zona.");
        }

        if (casillas[fila][columna] != null) {
            throw new IllegalArgumentException(
                    "La casilla ya está ocupada.");
        }

        casillas[fila][columna] = pieza;
    }

    public boolean esZonaProhibida(int fila, int columna) {
        validarPosicion(fila, columna);

        return (fila == 4 || fila == 5)
                && (columna == 2 || columna == 3
                || columna == 6 || columna == 7);
    }

    public boolean estaVacia(int fila, int columna) {
        validarPosicion(fila, columna);

        return casillas[fila][columna] == null
                && !esZonaProhibida(fila, columna);
    }

    public void moverPieza(int filaOrigen, int columnaOrigen,
                           int filaDestino, int columnaDestino) {

        validarPosicion(filaOrigen, columnaOrigen);
        validarPosicion(filaDestino, columnaDestino);

        Pieza pieza = casillas[filaOrigen][columnaOrigen];

        if (pieza == null) {
            throw new IllegalArgumentException(
                    "No hay ninguna pieza en el origen.");
        }

        if (!pieza.puedeMoverse()) {
            throw new IllegalArgumentException(
                    "Esta pieza no puede moverse.");
        }

        if (!estaVacia(filaDestino, columnaDestino)) {
            throw new IllegalArgumentException(
                    "El destino no está disponible.");
        }

        casillas[filaDestino][columnaDestino] = pieza;
        casillas[filaOrigen][columnaOrigen] = null;
    }

    private void validarPosicion(int fila, int columna) {
        if (fila < 0 || fila >= FILAS
                || columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException(
                    "La posición está fuera del tablero.");
        }
    }
}
