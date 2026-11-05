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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import modelo.GestorJugadores;
import modelo.Pieza;
import modelo.ResultadoCombate;
import modelo.Tablero;
import modelo.Tierra;
import modelo.Jugador;

public class VentanaJuego extends JFrame {

    private final Color azulPanel = new Color(8, 24, 48, 235);
    private final Color azulHeroes = new Color(35, 125, 220);
    private final Color rojoVillanos = new Color(220, 45, 55);

   
    private final Border bordeNormal = BorderFactory.createLineBorder(new Color(235, 40, 55), 1);
    private final Border bordeSeleccion = BorderFactory.createLineBorder(Color.YELLOW, 3);
    private final Border bordeMovimiento = BorderFactory.createLineBorder(new Color(60, 220, 90), 3);
    private final Border bordeAtaque = BorderFactory.createLineBorder(new Color(255, 140, 0), 3);

    private final Color capaBloqueada = new Color(20, 20, 20, 150);
    private final Color capaMovimiento = new Color(60, 220, 90, 70);
    private final Color capaAtaque = new Color(255, 140, 0, 90);

    
    private static final int ANCHO_IMAGEN = 1920;
    private static final int ALTO_IMAGEN = 1080;
    private static final int TABLERO_X = 467;
    private static final int TABLERO_Y = 80;
    private static final int TABLERO_LADO = 972;

    private double escalaFondo = 1;
    private int xFondo = 0;
    private int yFondo = 0;

    private Image imagenFondo;
    private Image imagenOculta;
    private ImageIcon iconoOculto;
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

    private JLabel infoHeroes;
    private JLabel infoVillanos;

    private JButton btnRetiroHeroes;
    private JButton btnRetiroVillanos;

    

    private final Jugador jugador1;
    private final Jugador jugador2;
    private final Bando bandoJugador1;
    private final Bando bandoJugador2;

    private Bando turno = Bando.HEROES;

    public VentanaJuego(Jugador jugador1, Jugador jugador2, Bando bandoJugador1) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.bandoJugador1 = bandoJugador1;

        if (bandoJugador1 == Bando.HEROES) {
            this.bandoJugador2 = Bando.VILLANOS;
        } else {
            this.bandoJugador2 = Bando.HEROES;
        }

        setTitle("Marvel Stratego");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(1200, 750);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        cargarImagenes();
        catalogoCartas = new CatalogoCartas();
        colocarPiezas();
        crearInterfaz();
    }


    private void colocarPiezas() {
        tablero.generarFormacion(Bando.VILLANOS);
        tablero.generarFormacion(Bando.HEROES);
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
        imagenOculta = cargarImagen("CartasTa/CartaOcultaTA.png");
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
        tituloHeroes = crearTitulo("HÉROES ELIMINADOS", azulHeroes);
        listaHeroesEliminados = crearLista();
        scrollHeroes = crearScroll(listaHeroesEliminados);
        panelHeroes.add(tituloHeroes);
        panelHeroes.add(scrollHeroes);

     
        panelVillanos = crearPanelLateral();
        tituloVillanos = crearTitulo("VILLANOS ELIMINADOS", rojoVillanos);
        listaVillanosEliminados = crearLista();
        scrollVillanos = crearScroll(listaVillanosEliminados);
        panelVillanos.add(tituloVillanos);
        panelVillanos.add(scrollVillanos);

        panelFondo.add(panelHeroes);
        panelFondo.add(panelVillanos);

        infoHeroes = crearInfoJugador();
        infoVillanos = crearInfoJugador();
        btnRetiroHeroes = crearBotonRetiro(Bando.HEROES);
        btnRetiroVillanos = crearBotonRetiro(Bando.VILLANOS);

        panelFondo.add(infoHeroes);
        panelFondo.add(infoVillanos);
        panelFondo.add(btnRetiroHeroes);
        panelFondo.add(btnRetiroVillanos);

        actualizarTurno();

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

                JButton boton = new JButton() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        Object capa = getClientProperty("capa");

                        if (capa instanceof Color) {
                            g.setColor((Color) capa);
                            g.fillRect(0, 0, getWidth(), getHeight());
                        }
                    }
                };
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
            iconoOculto = null;
            anchoIconos = ancho;
            altoIconos = alto;
        }

        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {

                Pieza pieza = tablero.obtenerPieza(f, c);
                if (pieza == null) {
                    casillas[f][c].setIcon(null);
                } else if (debeOcultarse(pieza)) {
                    casillas[f][c].setIcon(obtenerIconoOculto());
                } else {
                    casillas[f][c].setIcon(obtenerIcono(pieza));
                }

              
                marcarCasilla(f, c);
            }
        }
    }

    private void marcarCasilla(int fila, int columna) {
        Border borde = bordeNormal;
        Color capa = null;

        if (filaSeleccionada != -1) {
            if (fila == filaSeleccionada && columna == columnaSeleccionada) {
                borde = bordeSeleccion;
            } else if (tablero.esMovimientoValido(filaSeleccionada, columnaSeleccionada, fila, columna)) {
                borde = bordeMovimiento;
                capa = capaMovimiento;
            } else if (tablero.esAtaqueValido(filaSeleccionada, columnaSeleccionada, fila, columna)) {
                borde = bordeAtaque;
                capa = capaAtaque;
            } else {
                capa = capaBloqueada;
            }
        }

        casillas[fila][columna].setBorder(borde);
        casillas[fila][columna].putClientProperty("capa", capa);
        casillas[fila][columna].repaint();
    }

 
    private boolean debeOcultarse(Pieza pieza) {
        return pieza.getBando() != turno && !pieza.isRevelada();
    }

    private ImageIcon obtenerIconoOculto() {
        if (imagenOculta == null) {
            return null;
        }

        if (iconoOculto == null) {
            Image escalada = imagenOculta.getScaledInstance(
                anchoIconos, altoIconos, Image.SCALE_SMOOTH);
            iconoOculto = new ImageIcon(escalada);
        }

        return iconoOculto;
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

        Pieza pieza = tablero.obtenerPieza(fila, columna);

        if (pieza != null && pieza.getBando() == turno) {
            if (fila == filaSeleccionada && columna == columnaSeleccionada) {
                filaSeleccionada = -1;
                columnaSeleccionada = -1;
            } else if (pieza.puedeMoverse()) {
                filaSeleccionada = fila;
                columnaSeleccionada = columna;
            }

            refrescarTablero();
            return;
        }

        if (filaSeleccionada == -1) {
            return;
        }

        if (tablero.esAtaqueValido(filaSeleccionada, columnaSeleccionada, fila, columna)) {
            atacar(fila, columna);
            return;
        }

        if (tablero.esMovimientoValido(filaSeleccionada, columnaSeleccionada, fila, columna)) {
            tablero.moverPieza(filaSeleccionada, columnaSeleccionada, fila, columna);
            cambiarTurno();
        }
    }

    private void atacar(int fila, int columna) {
        Pieza atacante = tablero.obtenerPieza(filaSeleccionada, columnaSeleccionada);
        Pieza defensor = tablero.obtenerPieza(fila, columna);

        ResultadoCombate resultado = tablero.atacar(filaSeleccionada, columnaSeleccionada, fila, columna);

        String texto = atacante.getNombre() + " (" + atacante.getRango() + ") atacó a "
            + defensor.getNombre() + " (" + defensor.getRango() + "). ";

        switch (resultado) {
            case GANA:
                eliminarPieza(defensor);
                texto += atacante.getNombre() + " gana.";
                break;
            case PIERDE:
                eliminarPieza(atacante);
                texto += defensor.getNombre() + " gana.";
                break;
            case EMPATE:
                eliminarPieza(atacante);
                eliminarPieza(defensor);
                texto += "Empate: las dos piezas son eliminadas.";
                break;
        }

        filaSeleccionada = -1;
        columnaSeleccionada = -1;
        refrescarTablero();

        if (defensor instanceof Tierra && resultado == ResultadoCombate.GANA) {
            capturarTierra(atacante.getBando());
            return;
        }

        DialogoMarvel.mensaje(this, "Combate", texto);
        cambiarTurno();
    }

    private void eliminarPieza(Pieza pieza) {
        CartaTablero carta = catalogoCartas.obtener(pieza.getNombre(), pieza.getBando());

        if (carta == null) {
            return;
        }

        if (pieza.getBando() == Bando.HEROES) {
            agregarCartaEliminada(listaHeroesEliminados, carta.getImagen());
        } else {
            agregarCartaEliminada(listaVillanosEliminados, carta.getImagen());
        }
    }

    private void capturarTierra(Bando bandoGanador) {
        Jugador ganador = jugadorDe(bandoGanador);
        Jugador perdedor = jugadorDe(contrario(bandoGanador));

        String accion;
        if (bandoGanador == Bando.HEROES) {
            accion = "SALVADO";
        } else {
            accion = "CAPTURADO";
        }

        String log = ganador.getUsername() + " usando " + nombreBando(bandoGanador)
            + " ha " + accion + " la TIERRA! Venciendo a " + perdedor.getUsername()
            + " — " + fechaDeHoy();

        terminarPartida(bandoGanador, log);
    }

    private Jugador jugadorDe(Bando bando) {
        if (bando == bandoJugador1) {
            return jugador1;
        }
        return jugador2;
    }

    private Bando contrario(Bando bando) {
        if (bando == Bando.HEROES) {
            return Bando.VILLANOS;
        }
        return Bando.HEROES;
    }

    private String nombreBando(Bando bando) {
        if (bando == Bando.HEROES) {
            return "HÉROES";
        }
        return "VILLANOS";
    }

    private JLabel crearInfoJugador() {
        JLabel etiqueta = new JLabel("", SwingConstants.CENTER);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setBackground(new Color(5, 17, 35));
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));
        return etiqueta;
    }

    private JButton crearBotonRetiro(Bando bando) {
        JButton boton = new JButton();
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addActionListener(e -> retirarse(bando));

        Image imagen = DialogoMarvel.cargarImagenBoton("Retiro", 42);

        if (imagen != null) {
            boton.setContentAreaFilled(false);
            boton.setBorderPainted(false);
            boton.setOpaque(false);
            boton.setBorder(null);
            EfectoHover.aplicar(boton, imagen);
        } else {
            boton.setText("RETIRO");
            boton.setFont(new Font("Arial Black", Font.PLAIN, 15));
            boton.setForeground(DialogoMarvel.BLANCO);
            boton.setBackground(DialogoMarvel.ROJO);
            boton.setContentAreaFilled(false);
            boton.setOpaque(true);
            boton.setBorder(BorderFactory.createLineBorder(DialogoMarvel.NEGRO, 2));
        }

        return boton;
    }

    private void actualizarTurno() {
        actualizarInfo(infoHeroes, Bando.HEROES, azulHeroes);
        actualizarInfo(infoVillanos, Bando.VILLANOS, rojoVillanos);

        btnRetiroHeroes.setVisible(turno == Bando.HEROES);
        btnRetiroVillanos.setVisible(turno == Bando.VILLANOS);
    }

    private void actualizarInfo(JLabel etiqueta, Bando bando, Color colorBando) {
        boolean activo = turno == bando;

        String estado;
        if (activo) {
            estado = "<font color='#FFE600'>TU TURNO</font>";
        } else {
            estado = "<font color='#9AA5B5'>ESPERANDO</font>";
        }

        etiqueta.setText("<html><center>" + nombreBando(bando)
            + "<br><font size='5'>" + jugadorDe(bando).getUsername() + "</font>"
            + "<br>" + estado + "</center></html>");

        if (activo) {
            etiqueta.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else {
            etiqueta.setBorder(BorderFactory.createLineBorder(colorBando, 2));
        }
    }

    private void cambiarTurno() {
        turno = contrario(turno);
        filaSeleccionada = -1;
        columnaSeleccionada = -1;
        actualizarTurno();
        refrescarTablero();

        if (!tablero.tieneMovimientos(turno)) {
            sinMovimientos(turno);
        }
    }

    private void retirarse(Bando bando) {
        Jugador perdedor = jugadorDe(bando);
        Bando bandoGanador = contrario(bando);
        Jugador ganador = jugadorDe(bandoGanador);

        boolean confirmado = DialogoMarvel.confirmar(this, "Retiro",
            "¿Seguro que querés retirarte? " + ganador.getUsername() + " ganará la partida.");

        if (!confirmado) {
            return;
        }

        String log = ganador.getUsername() + " usando " + nombreBando(bandoGanador)
            + " ha ganado ya que " + perdedor.getUsername() + " usando " + nombreBando(bando)
            + " se ha retirado del juego — " + fechaDeHoy();

        terminarPartida(bandoGanador, log);
    }

    private void sinMovimientos(Bando bando) {
        Jugador perdedor = jugadorDe(bando);
        Bando bandoGanador = contrario(bando);
        Jugador ganador = jugadorDe(bandoGanador);

        String log = perdedor.getUsername() + " usando " + nombreBando(bando)
            + " ha perdido por no tener movimientos válidos disponibles ante "
            + ganador.getUsername() + " — " + fechaDeHoy();

        terminarPartida(bandoGanador, log);
    }

    private void terminarPartida(Bando bandoGanador, String log) {
        Jugador ganador = jugadorDe(bandoGanador);

        ganador.sumarPuntos(3);

        jugador1.sumarPartida(bandoJugador1);
        jugador2.sumarPartida(bandoJugador2);

        jugador1.agregarLog(log);
        jugador2.agregarLog(log);

        GestorJugadores.registrarPartida(bandoGanador);

        DialogoMarvel.mensaje(this, "Fin de la partida", log);

        dispose();
    }

    private String fechaDeHoy() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
        int espacio = 8;

        int anchoPanel = Math.max(75, (int) (ancho * 0.105));

        int xHeroes = Math.max(margenExterior, xTablero - anchoPanel - separacionTablero - 15);
        int xVillanos = Math.min(ancho - anchoPanel - margenExterior,
            xTablero + anchoTablero + separacionTablero + 10);

        int altoInfo = 84;
        int altoRetiro = 42;

        int yInfo = yTablero + 5;
        int yRetiro = yTablero + altoTablero - 5 - altoRetiro;
        int yPanel = yInfo + altoInfo + espacio;
        int altoPanel = yRetiro - espacio - yPanel;

        infoHeroes.setBounds(xHeroes, yInfo, anchoPanel, altoInfo);
        infoVillanos.setBounds(xVillanos, yInfo, anchoPanel, altoInfo);

        panelHeroes.setBounds(xHeroes, yPanel, anchoPanel, altoPanel);
        panelVillanos.setBounds(xVillanos, yPanel, anchoPanel, altoPanel);

        btnRetiroHeroes.setBounds(xHeroes, yRetiro, anchoPanel, altoRetiro);
        btnRetiroVillanos.setBounds(xVillanos, yRetiro, anchoPanel, altoRetiro);

      
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