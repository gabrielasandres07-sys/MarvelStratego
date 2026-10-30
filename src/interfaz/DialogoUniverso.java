/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author USER
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.GestorJugadores;
import modelo.Jugador;


public class DialogoUniverso extends DialogoMarvel {

    private static final int ANCHO_TABLA = 408;
    private static final int ALTO_FILA = 34;

    public DialogoUniverso(Window duenio, TipoUniverso tipo) {
       
        super(duenio, tipo == TipoUniverso.RANKING ? "Ranking" : "Batallas");

        switch (tipo) {
            case RANKING:
                armarRanking();
                break;
            case BATALLAS:
                armarBatallas();
                break;
        }

        agregarBoton("Cerrar", true, this::dispose);
    }

  

    private void armarRanking() {

        ArrayList<Jugador> ranking = GestorJugadores.obtenerRanking();
        Jugador actual = GestorJugadores.getJugadorActual();

        agregar(crearSubtitulo("Jugadores activos"));
        agregarEspacio(10);

        String[] columnas = { "#", "USUARIO", "PARTIDAS", "PUNTOS" };
        Object[][] datos = new Object[ranking.size()][4];

        for (int i = 0; i < ranking.size(); i++) {
            Jugador j = ranking.get(i);
            datos[i][0] = i + 1;
            datos[i][1] = j.getUsername();
            datos[i][2] = j.getPartidasHeroes() + j.getPartidasVillanos();
            datos[i][3] = j.getPuntos();
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(ALTO_FILA);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setRowSelectionAllowed(false);
        tabla.setFocusable(false);
        tabla.setFillsViewportHeight(true);
        tabla.setBackground(NEGRO);


        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        tabla.getTableHeader().setDefaultRenderer(new RenderEncabezado());

        
        int[] anchos = { 44, 168, 104, 86 };
        for (int i = 0; i < anchos.length; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

       
        tabla.setDefaultRenderer(Object.class, new RenderFila(ranking, actual));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(NEGRO, 3));
        scroll.getViewport().setBackground(NEGRO);
        estilizarScroll(scroll);

        int altoEncabezado = tabla.getTableHeader().getPreferredSize().height;
        int alto = Math.min(ranking.size() * ALTO_FILA + altoEncabezado + 6, 320);
        scroll.setPreferredSize(new Dimension(ANCHO_TABLA, alto));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, alto));

        agregar(scroll);
        agregarEspacio(8);
        agregar(crearEtiqueta("Tu posición aparece resaltada.", 13, Font.BOLD, TEXTO_SUAVE));
    }

    private static class RenderEncabezado extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable tabla, Object valor,
                boolean seleccionado, boolean foco, int fila, int columna) {

            JLabel celda = (JLabel) super.getTableCellRendererComponent(
                tabla, valor, false, false, fila, columna);

            celda.setFont(new Font("Arial Black", Font.PLAIN, 12));
            celda.setForeground(BLANCO);
            celda.setBackground(NEGRO);
            celda.setHorizontalAlignment(columna == 1 ? SwingConstants.LEFT : SwingConstants.CENTER);
            celda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, BLANCO),
                BorderFactory.createEmptyBorder(8, 6, 8, 6)
            ));
            return celda;
        }
    }

    private static class RenderFila extends DefaultTableCellRenderer {

        private final ArrayList<Jugador> ranking;
        private final Jugador actual;

        RenderFila(ArrayList<Jugador> ranking, Jugador actual) {
            this.ranking = ranking;
            this.actual = actual;
        }

        @Override
        public Component getTableCellRendererComponent(JTable tabla, Object valor,
                boolean seleccionado, boolean foco, int fila, int columna) {

            JLabel celda = (JLabel) super.getTableCellRendererComponent(
                tabla, valor, false, false, fila, columna);

            boolean esActual = ranking.get(fila) == actual;

            if (esActual) {
                celda.setBackground(BLANCO);
                celda.setForeground(ROJO);
            } else {
                celda.setBackground(fila % 2 == 0 ? NEGRO : GRIS_NEGRO);
                celda.setForeground(BLANCO);
            }

            boolean podio = columna == 0 && fila < 3;
            celda.setFont(new Font(podio ? "Arial Black" : "Arial", Font.BOLD, podio ? 16 : 14));

            celda.setHorizontalAlignment(columna == 1 ? SwingConstants.LEFT : SwingConstants.CENTER);
            celda.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            return celda;
        }
    }


    private void armarBatallas() {

        int heroes = GestorJugadores.getVictoriasHeroes();
        int villanos = GestorJugadores.getVictoriasVillanos();


        agregar(crearSubtitulo("Jugadores"));
        agregarEspacio(10);

        JPanel usuarios = new JPanel(new GridLayout(1, 2, 10, 0));
        usuarios.setOpaque(false);
        usuarios.add(crearTarjeta("Activos", String.valueOf(GestorJugadores.getJugadores().size())));
        usuarios.add(crearTarjeta("Históricos", String.valueOf(GestorJugadores.getUsuariosHistoricos())));
        agregar(usuarios);
        agregarEspacio(20);

    
        agregar(crearSubtitulo("Partidas"));
        agregarEspacio(10);

        JPanel partidas = new JPanel(new GridLayout(1, 3, 10, 0));
        partidas.setOpaque(false);
        partidas.add(crearTarjeta("Jugadas", String.valueOf(GestorJugadores.getPartidasJugadas())));
        partidas.add(crearTarjeta("Ganó Héroes", String.valueOf(heroes)));
        partidas.add(crearTarjeta("Ganó Villanos", String.valueOf(villanos)));
        agregar(partidas);
        agregarEspacio(20);

        // Barra Héroes vs Villanos
        agregar(crearSubtitulo("Héroes vs Villanos"));
        agregarEspacio(10);
        agregar(new BarraVictorias(heroes, villanos));
    }

    private static class BarraVictorias extends JComponent {

        private final int heroes;
        private final int villanos;

        BarraVictorias(int heroes, int villanos) {
            this.heroes = heroes;
            this.villanos = villanos;
            setPreferredSize(new Dimension(ANCHO_TABLA, 46));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );

            int ancho = getWidth();
            int alto = getHeight();
            int total = heroes + villanos;

            g2.setFont(new Font("Arial Black", Font.PLAIN, 13));

            if (total == 0) {
                g2.setColor(NEGRO);
                g2.fillRect(0, 0, ancho, alto);
                dibujarCentrado(g2, "Todavía no hay partidas", 0, ancho, alto, TEXTO_SUAVE);
            } else {
                int anchoHeroes = ancho * heroes / total;
                int porcentaje = Math.round(heroes * 100f / total);

                g2.setColor(BLANCO);
                g2.fillRect(0, 0, anchoHeroes, alto);
                g2.setColor(NEGRO);
                g2.fillRect(anchoHeroes, 0, ancho - anchoHeroes, alto);

                if (heroes > 0) {
                    dibujarCentrado(g2, "HÉROES " + porcentaje + "%", 0, anchoHeroes, alto, ROJO);
                }
                if (villanos > 0) {
                    dibujarCentrado(g2, "VILLANOS " + (100 - porcentaje) + "%",
                        anchoHeroes, ancho - anchoHeroes, alto, BLANCO);
                }
            }

            g2.setColor(NEGRO);
            g2.drawRect(0, 0, ancho - 1, alto - 1);
            g2.drawRect(1, 1, ancho - 3, alto - 3);

            g2.dispose();
        }

        private void dibujarCentrado(Graphics2D g2, String texto, int x, int ancho, int alto, Color color) {
            int anchoTexto = g2.getFontMetrics().stringWidth(texto);

            if (anchoTexto > ancho - 8) {
                return; 
            }

            int y = (alto + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;
            g2.setColor(color);
            g2.drawString(texto, x + (ancho - anchoTexto) / 2, y);
        }
    }
}