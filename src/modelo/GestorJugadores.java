/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
import excepciones.CredencialesInvalidasException;
import excepciones.PasswordInvalidoException;
import excepciones.UsuarioDuplicadoException;
import java.util.ArrayList;


public class GestorJugadores {

    public static final int LARGO_PASSWORD = 5;

   
    private static final ArrayList<Jugador> jugadores = new ArrayList<>();
    private static int usuariosHistoricos = 0;
    private static Jugador jugadorActual;

   
    private static int partidasJugadas = 0;
    private static int victoriasHeroes = 0;
    private static int victoriasVillanos = 0;

    
    private GestorJugadores() {
    }


    public static Jugador registrar(String username, String password)
            throws UsuarioDuplicadoException, PasswordInvalidoException {

        if (buscar(username) != null) {
            throw new UsuarioDuplicadoException(username);
        }

        validarPassword(password);

        Jugador nuevo = new Jugador(username, password);
        jugadores.add(nuevo);
        usuariosHistoricos++;

        jugadorActual = nuevo;  
        return nuevo;
    }

    public static Jugador iniciarSesion(String username, String password)
            throws CredencialesInvalidasException {

        Jugador jugador = buscar(username);

        if (jugador == null || !jugador.passwordCorrecto(password)) {
            throw new CredencialesInvalidasException();
        }

        jugadorActual = jugador;
        return jugador;
    }

    public static void cerrarSesion() {
        jugadorActual = null;
    }

    public static void validarPassword(String password)
            throws PasswordInvalidoException {
        if (password == null || password.length() != LARGO_PASSWORD) {
            throw new PasswordInvalidoException();
        }
    }


    public static void eliminar(Jugador jugador) {
        jugadores.remove(jugador);

        if (jugadorActual == jugador) {
            jugadorActual = null;
        }
    }

   
    public static Jugador buscar(String username) {
        for (Jugador j : jugadores) {
            if (j.getUsername().equals(username)) {
                return j;
            }
        }
        return null;
    }

    public static Jugador getJugadorActual() {
        return jugadorActual;
    }

    public static ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public static int getUsuariosHistoricos() {
        return usuariosHistoricos;
    }

    public static void registrarPartida(Bando ganador) {
        partidasJugadas++;

        if (ganador == Bando.HEROES) {
            victoriasHeroes++;
        } else {
            victoriasVillanos++;
        }
    }

    public static int getPartidasJugadas() {
        return partidasJugadas;
    }

    public static int getVictoriasHeroes() {
        return victoriasHeroes;
    }

    public static int getVictoriasVillanos() {
        return victoriasVillanos;
    }

    public static ArrayList<Jugador> obtenerRanking() {
        ArrayList<Jugador> ranking = new ArrayList<>();

        for (Jugador j : jugadores) {
            insertarOrdenado(ranking, j, 0);
        }

        return ranking;
    }

 
    private static void insertarOrdenado(ArrayList<Jugador> ranking,Jugador jugador, int posicion) {

        if (posicion == ranking.size()|| jugador.getPuntos() > ranking.get(posicion).getPuntos()) {
            ranking.add(posicion, jugador);
            return;
        }
        insertarOrdenado(ranking, jugador, posicion + 1);
    }
}