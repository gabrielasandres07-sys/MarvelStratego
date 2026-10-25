/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */


import java.util.ArrayList;

public class Jugador {

    private final String username;
    private String password;
    private int puntos;
    private int partidasHeroes;
    private int partidasVillanos;
    private final ArrayList<String> logs;

    public Jugador(String username, String password) {
        this.username = username;
        this.password = password;
        this.puntos = 0;
        this.partidasHeroes = 0;
        this.partidasVillanos = 0;
        this.logs = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean passwordCorrecto(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }

    public int getPartidasHeroes() {
        return partidasHeroes;
    }

    public int getPartidasVillanos() {
        return partidasVillanos;
    }

    public void sumarPartida(Bando bando) {
        if (bando == Bando.HEROES) {
            partidasHeroes++;
        } else {
            partidasVillanos++;
        }
    }

    public ArrayList<String> getLogs() {
        return logs;
    }

    public void agregarLog(String log) {
        logs.add(0, log);
    }
}
