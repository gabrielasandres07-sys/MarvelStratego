/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;
/**
 *
 * @author USER
 */

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import modelo.Jugador;

public class DialogoPerfil extends DialogoMarvel {

    public DialogoPerfil(Window duenio, Jugador jugador) {
        super(duenio, "Mi Perfil");

       
        agregar(crearEtiqueta("JUGADOR", 12, Font.BOLD, NEGRO));
        agregar(crearTextoGrande(jugador.getUsername(), 32, BLANCO));
        agregarEspacio(14);

        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 10, 0));
        tarjetas.setOpaque(false);
        tarjetas.add(crearTarjeta("Puntos", String.valueOf(jugador.getPuntos())));
        tarjetas.add(crearTarjeta("Con Héroes", String.valueOf(jugador.getPartidasHeroes())));
        tarjetas.add(crearTarjeta("Con Villanos", String.valueOf(jugador.getPartidasVillanos())));
        agregar(tarjetas);
        agregarEspacio(22);

 
        agregar(crearSubtitulo("Mis últimas batallas"));
        agregarEspacio(10);

        if (jugador.getLogs().isEmpty()) {
            agregar(crearEtiqueta(
                "Todavía no has jugado ninguna partida.",
                14, Font.BOLD, TEXTO_SUAVE
            ));
        } else {
            JPanel filas = new JPanel();
            filas.setLayout(new BoxLayout(filas, BoxLayout.Y_AXIS));

            for (String log : jugador.getLogs()) {
                filas.add(crearFila(log));
                filas.add(Box.createVerticalStrut(6));
            }

            agregar(crearLista(filas, 220));
        }

        agregarBoton("Cerrar", true, this::dispose);
    }
}