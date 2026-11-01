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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import modelo.Bando;
import modelo.Personaje;
import modelo.Pieza;
import modelo.PiezaMovil;
import modelo.Tablero;

public class VentanaJuego extends JFrame {

    private final Color azulPanel = new Color(8, 24, 48, 235);

   
    private final Border bordeNormal = BorderFactory.createLineBorder(new Color(235, 40, 55), 1);
    private final Border bordeSeleccion = BorderFactory.createLineBorder(Color.YELLOW, 3);

    
    private static final int ANCHO_IMAGEN = 1920;
    private static final int ALTO_IMAGEN = 1080;
    private static final int TABLERO_X = 467;
    private static final int TABLERO_Y = 80;
    private static final int TABLERO_LADO = 972;

    private double escalaFondo = 1;
    private int xFondo = 0;
    private int yFondo = 0;

    private Image imagenFondo;
    private CatalogoCartas catalogoCartas;

    
    private final Tablero tablero = new Tablero();

   
    private final JButton[][] casillas = new JButton[Tablero.FILAS][Tablero.COLUMNAS];
    private JPanel panelTablero;

    private final Map<String, ImageIcon> iconosEscalados = new HashMap<>();
    private int anchoIconos = -1;
    private int altoIconos = -1;

    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;

    private JPanel panelFondo;

    private JPanel panelHeroes;
    private JPanel panelVillanos;

    private JPanel listaHeroesEliminados;
    private JPanel listaVillanosEliminados;

    private JScrollPane scrollHeroes;
    private JScrollPane scrollVillanos;

    private JLabel tituloHeroes;
    private JLabel tituloVillanos;

    public VentanaJuego() {
        setTitle("Marvel Stratego");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(1200, 750);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        cargarImagenes();
        catalogoCartas = new CatalogoCartas();
        colocarPiezasDePrueba();
        crearInterfaz();
    }


    private void colocarPiezasDePrueba() {
        colocarBandoDePrueba(Bando.VILLANOS, 0);
        colocarBandoDePrueba(Bando.HEROES, 6);
    }

    private void colocarBandoDePrueba(Bando bando, int filaInicial) {
        int fila = filaInicial;
        int columna = 0;

        for (Personaje p : Personaje.values()) {

            if (p.getBando() != bando) {
                continue;
            }

       
            for (int i = 0; i < p.getCantidad(); i++) {
                tablero.colocarPieza(
                    new PiezaMovil(p.getNombre(), p.getRango(), p.getBando()),fila,columna );

                columna++;
                if (columna == Tablero.COLUMNAS) {
                    columna = 0;
                    fila++;
                }
            }
        }
    }

    private Image cargarImagen(String nombre) {
        URL url = getClass().getResource("/recursos/" + nombre);

        if (url == null) {
            return null;
        }

        return new ImageIcon(url).getImage();
    }

    private void cargarImagenes() {
        imagenFondo = cargarImagen("fondo_tablero.png");
    }

    private void crearInterfaz() {
        panelFondo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(new Color(12, 12, 12));
                g.fillRect(0, 0, getWidth(), getHeight());

                
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, xFondo, yFondo,
                        (int) (ANCHO_IMAGEN * escalaFondo),
                        (int) (ALTO_IMAGEN * escalaFondo),
                        this);
                }
            }
        };

        setContentPane(panelFondo);

       
        panelTablero = crearTablero();
        panelFondo.add(panelTablero);

       
        panelHeroes = crearPanelLateral();
        tituloHeroes = crearTitulo("HÉROES ELIMINADOS", new Color(35, 125, 220));
        listaHeroesEliminados = crearLista();
        scrollHeroes = crearScroll(listaHeroesEliminados);
        panelHeroes.add(tituloHeroes);
        panelHeroes.add(scrollHeroes);

     
        panelVillanos = crearPanelLateral();
        tituloVillanos = crearTitulo("VILLANOS ELIMINADOS", new Color(220, 45, 55));
        listaVillanosEliminados = crearLista();
        scrollVillanos = crearScroll(listaVillanosEliminados);
        panelVillanos.add(tituloVillanos);
        panelVillanos.add(scrollVillanos);

        panelFondo.add(panelHeroes);
        panelFondo.add(panelVillanos);

        panelFondo.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarElementos();
            }
        });

        SwingUtilities.invokeLater(this::ajustarElementos);
    }

 

    private JPanel crearTablero() {

        JPanel panel = new JPanel(new GridLayout(Tablero.FILAS, Tablero.COLUMNAS));
        panel.setOpaque(false);

        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {

                JButton boton = new JButton();
                boton.setFocusPainted(false);
                boton.setContentAreaFilled(false);
                boton.setOpaque(false);
                boton.setBorder(bordeNormal);

                if (tablero.esZonaProhibida(f, c)) {
                  
                    boton.setEnabled(false);
                } else {
                    boton.setCursor(new Cursor(Cursor.HAND_CURSOR));


                    final int fila = f;
                    final int columna = c;
                    boton.addActionListener(e -> clicCasilla(fila, columna));
                }

                casillas[f][c] = boton;
                panel.add(boton);
            }
        }

        return panel;
    }

   
    private void refrescarTablero() {

        JButton ejemplo = casillas[0][0];
        int ancho = (int) (ejemplo.getWidth() * 0.85);
        int alto = (int) (ejemplo.getHeight() * 0.85);

        if (ancho <= 0 || alto <= 0) {
            return;
        }

     
        if (ancho != anchoIconos || alto != altoIconos) {
            iconosEscalados.clear();
            anchoIconos = ancho;
            altoIconos = alto;
        }

        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {

                Pieza pieza = tablero.obtenerPieza(f, c);
                casillas[f][c].setIcon(pieza == null ? null : obtenerIcono(pieza));

              
                if (!tablero.esZonaProhibida(f, c)) {
                    boolean seleccionada = f == filaSeleccionada && c == columnaSeleccionada;
                    casillas[f][c].setBorder(seleccionada ? bordeSeleccion : bordeNormal);
                }
            }
        }
    }

 
    private ImageIcon obtenerIcono(Pieza pieza) {

        
        String nombre = pieza.getBando() + ":" + pieza.getNombre();

        if (!iconosEscalados.containsKey(nombre)) {
            CartaTablero carta = catalogoCartas.obtener(pieza.getNombre(), pieza.getBando());

            if (carta == null || carta.getImagen() == null) {
                return null;
            }

            Image escalada = carta.getImagen().getScaledInstance(
                anchoIconos, altoIconos, Image.SCALE_SMOOTH);
            iconosEscalados.put(nombre, new ImageIcon(escalada));
        }

        return iconosEscalados.get(nombre);
    }

  
    private void clicCasilla(int fila, int columna) {


        if (fila == filaSeleccionada && columna == columnaSeleccionada) {
            filaSeleccionada = -1;
            columnaSeleccionada = -1;
        } else {
            filaSeleccionada = fila;
            columnaSeleccionada = columna;
        }

        refrescarTablero();
    }

    private JPanel crearPanelLateral() {
        JPanel panel = new JPanel(null);
        panel.setBackground(azulPanel);
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createLineBorder(new Color(70, 120, 175), 1));
        return panel;
    }

    private JLabel crearTitulo(String texto, Color borde) {
        JLabel etiqueta = new JLabel("<html><center>" + texto + "</center></html>", SwingConstants.CENTER);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setBackground(new Color(5, 17, 35));
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 12));
        etiqueta.setBorder(BorderFactory.createLineBorder(borde, 2));
        return etiqueta;
    }

    private JPanel crearLista() {
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setOpaque(false);
        lista.setBorder(BorderFactory.createEmptyBorder(4, 2, 4, 2));
        return lista;
    }

    private JScrollPane crearScroll(JPanel lista) {
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    public void agregarCartaEliminada(JPanel lista, Image imagen) {
        if (lista == null || imagen == null) {
            return;
        }

        JLabel carta = new JLabel();
        carta.putClientProperty("imagenOriginal", imagen);
        carta.setAlignmentX(CENTER_ALIGNMENT);
        carta.setHorizontalAlignment(SwingConstants.CENTER);
        carta.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 0));

        actualizarCarta(carta, lista);

        lista.add(carta);
        lista.revalidate();
        lista.repaint();
    }

    private void actualizarCarta(JLabel carta, JPanel lista) {
        Object objeto = carta.getClientProperty("imagenOriginal");

        if (!(objeto instanceof Image)) {
            return;
        }

        Image original = (Image) objeto;

        int ow = original.getWidth(null);
        int oh = original.getHeight(null);

        if (ow <= 0 || oh <= 0) {
            return;
        }

        int anchoDisponible = Math.max(35, lista.getWidth() - 8);
        int anchoCarta = Math.min(anchoDisponible, 72);
        int altoCarta = Math.max(25, (int) (oh * anchoCarta / (double) ow));

        Image escalada = original.getScaledInstance(anchoCarta, altoCarta, Image.SCALE_SMOOTH);

        carta.setIcon(new ImageIcon(escalada));
        carta.setPreferredSize(new Dimension(anchoCarta, altoCarta + 6));
        carta.setMaximumSize(new Dimension(Integer.MAX_VALUE, altoCarta + 6));
    }

    private void actualizarCartas(JPanel lista) {
        if (lista == null) {
            return;
        }

        for (int i = 0; i < lista.getComponentCount(); i++) {
            if (lista.getComponent(i) instanceof JLabel) {
                JLabel carta = (JLabel) lista.getComponent(i);

                if (carta.getClientProperty("imagenOriginal") != null) {
                    actualizarCarta(carta, lista);
                }
            }
        }

        lista.revalidate();
        lista.repaint();
    }

    private void ajustarElementos() {
        if (panelFondo == null || panelTablero == null
                || panelHeroes == null || panelVillanos == null) {
            return;
        }

        int ancho = panelFondo.getWidth();
        int alto = panelFondo.getHeight();

        if (ancho <= 0 || alto <= 0) {
            return;
        }

      
        escalaFondo = Math.max((double) ancho / ANCHO_IMAGEN, (double) alto / 1028);

    
        escalaFondo = Math.min(escalaFondo, (double) alto / TABLERO_LADO);

  
        xFondo = (int) ((ancho - ANCHO_IMAGEN * escalaFondo) / 2);
        yFondo = (int) ((alto - TABLERO_LADO * escalaFondo) / 2 - TABLERO_Y * escalaFondo);

     
        int xTablero = xFondo + (int) (TABLERO_X * escalaFondo);
        int yTablero = yFondo + (int) (TABLERO_Y * escalaFondo);
        int anchoTablero = (int) (TABLERO_LADO * escalaFondo);
        int altoTablero = anchoTablero;

        panelTablero.setBounds(xTablero, yTablero, anchoTablero, altoTablero);

     
        panelTablero.doLayout();
        refrescarTablero();

        int margenExterior = 5;
        int separacionTablero = 24;

        int anchoPanel = Math.max(75, (int) (ancho * 0.105));
        int altoPanel = (int) (alto * 0.78);
        int yPanel = (alto - altoPanel) / 2;

        int xHeroes = xTablero - anchoPanel - separacionTablero - 15;
        int xVillanos = xTablero + anchoTablero + separacionTablero + 10;

        panelHeroes.setBounds(Math.max(margenExterior, xHeroes), yPanel, anchoPanel, altoPanel);
        panelVillanos.setBounds(
            Math.min(ancho - anchoPanel - margenExterior, xVillanos),
            yPanel, anchoPanel, altoPanel
        );

      
        int interno = 4;
        int anchoInterior = anchoPanel - interno * 2;

        int altoTitulo = 44;
        int yTitulo = 8;
        int yCartas = yTitulo + altoTitulo + 6;

        tituloHeroes.setBounds(interno, yTitulo, anchoInterior, altoTitulo);
        scrollHeroes.setBounds(interno, yCartas, anchoInterior, altoPanel - yCartas - 8);

        tituloVillanos.setBounds(interno, yTitulo, anchoInterior, altoTitulo);
        scrollVillanos.setBounds(interno, yCartas, anchoInterior, altoPanel - yCartas - 8);

        actualizarCartas(listaHeroesEliminados);
        actualizarCartas(listaVillanosEliminados);

        panelFondo.repaint();
    }
}