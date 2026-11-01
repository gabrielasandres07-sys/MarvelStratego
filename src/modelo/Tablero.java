package modelo;

import java.util.ArrayList;
import java.util.Random;

public class Tablero {

    public static final int FILAS = 10;
    public static final int COLUMNAS = 10;

    private final Pieza[][] casillas;
    private final Random random = new Random();

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
            throw new IllegalArgumentException("La pieza no puede ser nula.");
        }

        if (esZonaProhibida(fila, columna)) {
            throw new IllegalArgumentException("No se puede colocar una pieza en esa zona.");
        }

        if (casillas[fila][columna] != null) {
            throw new IllegalArgumentException( "La casilla ya está ocupada.");
        }

        casillas[fila][columna] = pieza;
    }

    public void generarFormacion(Bando bando) {
        int filaFondo;
        int filaSegunda;
        int filaMedia;
        int filaFrente;

        if (bando == Bando.VILLANOS) {
            filaFondo = 0;
            filaSegunda = 1;
            filaMedia = 2;
            filaFrente = 3;
        } else {
            filaFondo = 9;
            filaSegunda = 8;
            filaMedia = 7;
            filaFrente = 6;
        }

        Pieza tierra = null;
        ArrayList<Pieza> bombas = new ArrayList<>();
        ArrayList<Pieza> rango2 = new ArrayList<>();
        ArrayList<Pieza> resto = new ArrayList<>();

        for (Personaje p : Personaje.values()) {

            if (p.getBando() != bando) {
                continue;
            }

            for (int i = 0; i < p.getCantidad(); i++) {
                if (p.esTierra()) {
                    tierra = new Tierra(p.getNombre(), bando);
                } else if (p.esBomba()) {
                    bombas.add(new Bomba(p.getNombre(), bando));
                } else if (p.getRango() == 2) {
                    rango2.add(new PiezaMovil(p.getNombre(), p.getRango(), bando));
                } else {
                    resto.add(new PiezaMovil(p.getNombre(), p.getRango(), bando));
                }
            }
        }

        int columnaTierra = 1 + random.nextInt(COLUMNAS - 2);

        colocarPieza(tierra, filaFondo, columnaTierra);
        colocarPieza(bombas.remove(0), filaFondo, columnaTierra - 1);
        colocarPieza(bombas.remove(0), filaFondo, columnaTierra + 1);
        colocarPieza(bombas.remove(0), filaSegunda, columnaTierra);

        colocarAleatorio(bombas, casillasLibres(filaFondo, filaSegunda));
        colocarAleatorio(rango2, casillasLibres(filaMedia, filaFrente));
        colocarAleatorio(resto, casillasLibres(filaFrente, filaFrente));
        colocarAleatorio(resto, casillasLibres(filaFondo, filaFrente));
    }

    private ArrayList<int[]> casillasLibres(int filaA, int filaB) {
        ArrayList<int[]> libres = new ArrayList<>();

        int desde = Math.min(filaA, filaB);
        int hasta = Math.max(filaA, filaB);

        for (int f = desde; f <= hasta; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (estaVacia(f, c)) {
                    libres.add(new int[] {f, c});
                }
            }
        }

        return libres;
    }

    private void colocarAleatorio(ArrayList<Pieza> piezas, ArrayList<int[]> libres) {

        if (piezas.isEmpty() || libres.isEmpty()) {
            return;
        }

        Pieza pieza = piezas.remove(random.nextInt(piezas.size()));
        int[] casilla = libres.remove(random.nextInt(libres.size()));

        colocarPieza(pieza, casilla[0], casilla[1]);

        colocarAleatorio(piezas, libres);
    }

    public boolean esZonaProhibida(int fila, int columna) {
        validarPosicion(fila, columna);

        return (fila == 4 || fila == 5)
                && (columna == 2 || columna == 3
                || columna == 6 || columna == 7);
    }

    public boolean estaVacia(int fila, int columna) {
        validarPosicion(fila, columna);

        return casillas[fila][columna] == null && !esZonaProhibida(fila, columna);
    }

    public boolean esMovimientoValido(int filaOrigen, int columnaOrigen,
                                      int filaDestino, int columnaDestino) {

        Pieza pieza = obtenerPieza(filaOrigen, columnaOrigen);

        if (pieza == null || !pieza.puedeMoverse()) {
            return false;
        }

        int distancia = Math.abs(filaOrigen - filaDestino)
                + Math.abs(columnaOrigen - columnaDestino);

        return distancia == 1 && estaVacia(filaDestino, columnaDestino);
    }

    public void moverPieza(int filaOrigen, int columnaOrigen,
                           int filaDestino, int columnaDestino) {

        validarPosicion(filaOrigen, columnaOrigen);
        validarPosicion(filaDestino, columnaDestino);

        Pieza pieza = casillas[filaOrigen][columnaOrigen];

        if (pieza == null) {
            throw new IllegalArgumentException("No hay ninguna pieza en el origen.");
        }

        if (!pieza.puedeMoverse()) {
            throw new IllegalArgumentException( "Esta pieza no puede moverse.");
        }

        if (!estaVacia(filaDestino, columnaDestino)) {
            throw new IllegalArgumentException("El destino no está disponible.");
        }

        casillas[filaDestino][columnaDestino] = pieza;
        casillas[filaOrigen][columnaOrigen] = null;
    }

    private void validarPosicion(int fila, int columna) {
        if (fila < 0 || fila >= FILAS
                || columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException( "La posición está fuera del tablero.");
        }
    }
}