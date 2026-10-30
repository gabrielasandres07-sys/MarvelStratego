/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;
/**
 *
 * @author USER
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class DialogoMarvel extends JDialog {

    public static final Color ROJO = new Color(234, 36, 41);
    public static final Color NEGRO = new Color(12, 12, 12);
    public static final Color GRIS_NEGRO = new Color(40, 40, 40);
    public static final Color BLANCO = Color.WHITE;
    public static final Color TEXTO_SUAVE = new Color(255, 210, 210);

    private static final int ANCHO_MINIMO = 480;
    private static final int ALTO_BOTON = 44;   
    private static final String FUENTE_TITULO = "Arial Black";

    private static ImageIcon logoMarvel;
    private static ImageIcon logoStratego;

    private final JPanel contenido;
    private final JPanel pie;
    private Point inicioArrastre;

    private boolean aceptado = false;

    public DialogoMarvel(Window duenio, String titulo) {
        super(duenio, titulo, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);


        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(ROJO);
        raiz.setBorder(BorderFactory.createLineBorder(NEGRO, 10));
        setContentPane(raiz);

        raiz.add(crearEncabezado(titulo), BorderLayout.NORTH);


        contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(ROJO);
        contenido.setBorder(BorderFactory.createEmptyBorder(18, 26, 10, 26));
        raiz.add(contenido, BorderLayout.CENTER);

        pie = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        pie.setBackground(ROJO);
        pie.setBorder(BorderFactory.createEmptyBorder(12, 26, 22, 26));
        raiz.add(pie, BorderLayout.SOUTH);

        getRootPane().registerKeyboardAction(
            e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private JPanel crearEncabezado(String titulo) {

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(ROJO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(6, 12, 0, 8));

        JPanel barraX = new JPanel(new BorderLayout());
        barraX.setOpaque(false);
        barraX.add(crearBotonCerrar(), BorderLayout.EAST);
        encabezado.add(barraX, BorderLayout.NORTH);

        
        JPanel logos = new JPanel();
        logos.setLayout(new BoxLayout(logos, BoxLayout.Y_AXIS));
        logos.setOpaque(false);

        agregarLogo(logos, getLogoStratego());
        logos.add(Box.createVerticalStrut(4));
        agregarLogo(logos, getLogoMarvel());

        encabezado.add(logos, BorderLayout.CENTER);

       
        JLabel lblTitulo = new JLabel(titulo.toUpperCase(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font(FUENTE_TITULO, Font.PLAIN, 24));
        lblTitulo.setForeground(BLANCO);
        lblTitulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 14, 0, 14),
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, BLANCO),
                BorderFactory.createEmptyBorder(0, 0, 8, 0)
            )
        ));
        encabezado.add(lblTitulo, BorderLayout.SOUTH);

        MouseAdapter arrastre = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                inicioArrastre = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point actual = getLocation();
                setLocation(actual.x + e.getX() - inicioArrastre.x, actual.y + e.getY() - inicioArrastre.y);
            }
        };
        encabezado.addMouseListener(arrastre);
        encabezado.addMouseMotionListener(arrastre);

        return encabezado;
    }

  
    private void agregarLogo(JPanel panel, ImageIcon logo) {
        if (logo != null) {
            JLabel etiqueta = new JLabel(logo);
            etiqueta.setAlignmentX(CENTER_ALIGNMENT);
            panel.add(etiqueta);
        }
    }

    private JButton crearBotonCerrar() {

        JButton btnCerrar = new JButton("X");
        btnCerrar.setFont(new Font(FUENTE_TITULO, Font.PLAIN, 16));
        btnCerrar.setForeground(BLANCO);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dispose());


        btnCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCerrar.setForeground(NEGRO);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCerrar.setForeground(BLANCO);
            }
        });

        return btnCerrar;
    }

    private static ImageIcon getLogoStratego() {
        if (logoStratego == null) {
            logoStratego = cargarLogo("/recursos/stratego_logo.png", 56);
        }
        return logoStratego;
    }

    private static ImageIcon getLogoMarvel() {
        if (logoMarvel == null) {
            logoMarvel = cargarLogo("/recursos/marvel_logo.png", 40);
        }
        return logoMarvel;
    }

  
    private static ImageIcon cargarLogo(String ruta, int alto) {
        URL url = DialogoMarvel.class.getResource(ruta);

        if (url == null) {
            return null;
        }

        Image original = new ImageIcon(url).getImage();
        int ancho = original.getWidth(null) * alto / original.getHeight(null);

        return new ImageIcon(original.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
    }

 
    protected void agregar(JComponent componente) {
        componente.setAlignmentX(LEFT_ALIGNMENT);
        contenido.add(componente);
    }

    protected void agregarEspacio(int alto) {
        contenido.add(Box.createVerticalStrut(alto));
    }

    public void agregarTexto(String texto) {
        agregar(crearEtiqueta(
            "<html><body style='width:320px'>"
                + escapar(texto).replace("\n", "<br>") + "</body></html>",
            15, Font.BOLD, BLANCO
        ));
    }

    protected JLabel crearEtiqueta(String texto, int tamanio, int estilo, Color color) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", estilo, tamanio));
        etiqueta.setForeground(color);
        return etiqueta;
    }

    protected JLabel crearTextoGrande(String texto, int tamanio, Color color) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font(FUENTE_TITULO, Font.PLAIN, tamanio));
        etiqueta.setForeground(color);
        return etiqueta;
    }

    protected JLabel crearSubtitulo(String texto) {
        JLabel subtitulo = crearTextoGrande(texto.toUpperCase(), 14, NEGRO);
        subtitulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, NEGRO),
            BorderFactory.createEmptyBorder(0, 8, 0, 0)
        ));
        return subtitulo;
    }

    protected JLabel crearEtiquetaCampo(String texto) {
        return crearEtiqueta(texto.toUpperCase(), 12, Font.BOLD, NEGRO);
    }

    protected JPanel crearTarjeta(String titulo, String valor) {

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(NEGRO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 4, 0, BLANCO),
            BorderFactory.createEmptyBorder(12, 14, 10, 14)
        ));

        tarjeta.add(crearTextoGrande(valor, 28, BLANCO));
        tarjeta.add(Box.createVerticalStrut(2));
        tarjeta.add(crearEtiqueta(titulo.toUpperCase(), 11, Font.BOLD, ROJO));

        return tarjeta;
    }

    protected JPanel crearFila(String texto) {

        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(NEGRO);
        fila.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, BLANCO),
            BorderFactory.createEmptyBorder(9, 12, 9, 12)
        ));

        fila.add(crearEtiqueta( "<html><body style='width:285px'>" + escapar(texto) + "</body></html>", 13, Font.PLAIN, BLANCO ));

        fila.setAlignmentX(LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, fila.getPreferredSize().height));
        return fila;
    }

    protected JScrollPane crearLista(JPanel filas, int altoMaximo) {

        filas.setBackground(ROJO);

        JScrollPane scroll = new JScrollPane(filas);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(ROJO);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        estilizarScroll(scroll);

        int alto = Math.min(filas.getPreferredSize().height, altoMaximo);
        scroll.setPreferredSize(new Dimension(ANCHO_MINIMO - 72, alto));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, alto));

        return scroll;
    }


    protected void estilizarScroll(JScrollPane scroll) {

        JScrollBar barra = scroll.getVerticalScrollBar();
        barra.setUnitIncrement(14);
        barra.setPreferredSize(new Dimension(10, 0));

        barra.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = BLANCO;
                trackColor = new Color(185, 22, 30);
            }

            @Override
            protected JButton createDecreaseButton(int orientacion) {
                return botonInvisible();
            }

            @Override
            protected JButton createIncreaseButton(int orientacion) {
                return botonInvisible();
            }

            private JButton botonInvisible() {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(0, 0));
                return boton;
            }
        });
    }

    protected JPasswordField crearCampoPassword() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setBackground(BLANCO);
        campo.setForeground(NEGRO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEGRO, 3),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        return campo;
    }

    protected JToggleButton crearOpcion(String texto, ButtonGroup grupo, int tamanio) {

        JToggleButton opcion = new JToggleButton(texto);
        opcion.setFont(new Font(FUENTE_TITULO, Font.PLAIN, tamanio));
        opcion.setContentAreaFilled(false);
        opcion.setOpaque(true);
        opcion.setFocusPainted(false);
        opcion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        opcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEGRO, 3),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
        opcion.setAlignmentX(LEFT_ALIGNMENT);
        opcion.setMaximumSize(new Dimension(Integer.MAX_VALUE, opcion.getPreferredSize().height));

        opcion.addItemListener(e -> pintarOpcion(opcion));
        pintarOpcion(opcion);

        grupo.add(opcion);
        return opcion;
    }

    private void pintarOpcion(JToggleButton opcion) {
        if (opcion.isSelected()) {
            opcion.setBackground(BLANCO);
            opcion.setForeground(ROJO);
        } else {
            opcion.setBackground(NEGRO);
            opcion.setForeground(BLANCO);
        }
    }

 
    public JButton agregarBoton(String texto, boolean principal, Runnable accion) {

        Image imagen = cargarImagenBoton(texto);

        JButton boton;
        if (imagen != null) {
            boton = crearBotonImagen(imagen);
        } else {
            boton = crearBotonTexto(texto, principal);
        }

        boton.addActionListener(e -> accion.run());
        pie.add(boton);
        return boton;
    }


    protected static Image cargarImagenBoton(String texto) {
        return cargarImagenBoton(texto, ALTO_BOTON);
    }

 
    protected static Image cargarImagenBoton(String texto, int alto) {

        String limpio = texto.toLowerCase()
            .replace("á", "a").replace("é", "e").replace("í", "i")
            .replace("ó", "o").replace("ú", "u");

        URL url = DialogoMarvel.class.getResource("/recursos/btn_" + limpio + ".png");

        if (url == null) {
            return null;
        }

        Image original = new ImageIcon(url).getImage();
        int ancho = original.getWidth(null) * alto / original.getHeight(null);

        return original.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    private JButton crearBotonImagen(Image imagen) {

        JButton boton = new JButton();
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setBorder(null);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        EfectoHover.aplicar(boton, imagen);
        return boton;
    }

    private JButton crearBotonTexto(String texto, boolean principal) {

        Color fondo = principal ? BLANCO : NEGRO;
        Color fondoHover = principal ? new Color(230, 230, 230) : GRIS_NEGRO;

        JButton boton = new JButton(texto.toUpperCase());
        boton.setFont(new Font(FUENTE_TITULO, Font.PLAIN, 13));
        boton.setForeground(principal ? ROJO : BLANCO);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBackground(fondo);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEGRO, 3),
            BorderFactory.createEmptyBorder(8, 26, 8, 26)
        ));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(fondoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(fondo);
            }
        });

        return boton;
    }

    public void mostrar() {
        pack();

        if (getWidth() < ANCHO_MINIMO) {
            setSize(ANCHO_MINIMO, getHeight());
        }

        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    public static void mensaje(Window duenio, String titulo, String texto) {
        DialogoMarvel dialogo = new DialogoMarvel(duenio, titulo);
        dialogo.agregarTexto(texto);
        dialogo.agregarBoton("Aceptar", true, dialogo::dispose);
        dialogo.mostrar();
    }

   
    public static boolean confirmar(Window duenio, String titulo, String texto) {
        DialogoMarvel dialogo = new DialogoMarvel(duenio, titulo);
        dialogo.agregarTexto(texto);
        dialogo.agregarBoton("Sí", true, dialogo::aceptar);
        dialogo.agregarBoton("No", false, dialogo::dispose);
        dialogo.mostrar();

        return dialogo.isAceptado();
    }

   
    private void aceptar() {
        aceptado = true;
        dispose();
    }

    public boolean isAceptado() {
        return aceptado;
    }

    private static String escapar(String texto) {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}