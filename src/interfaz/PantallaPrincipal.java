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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PantallaPrincipal extends JFrame {

    private Image imagenFondo;
    private Image imagenLogin;
    private Image imagenCrearPlayer;

    private JPanel panelPrincipal;
    private JLabel botonLogin;
    private JLabel botonCrearPlayer;

    public PantallaPrincipal() {
        setTitle("Marvel Stratego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1200, 750);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        cargarImagenes();
        crearInterfaz();
    }

    private Image cargarImagen(String nombre) {
        URL url = getClass().getResource("/recursos/" + nombre);

        if (url == null) {
            return null;
        }

        return new ImageIcon(url).getImage();
    }

    private void cargarImagenes() {
        imagenFondo = cargarImagen("fondo_inicio.png");
        imagenLogin = cargarImagen("btn_login.png");
        imagenCrearPlayer = cargarImagen("btn_crepla_1.png");
    }

    private void crearInterfaz() {

        panelPrincipal = new JPanel(null) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
                );

                if (imagenFondo != null) {
                    g2.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2.setColor(new Color(10, 15, 30));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }

                g2.dispose();
            }
        };

        setContentPane(panelPrincipal);

        botonLogin = crearBoton("LOG IN");
        botonCrearPlayer = crearBoton("CREAR PLAYER");

        panelPrincipal.add(botonLogin);
        panelPrincipal.add(botonCrearPlayer);

        botonLogin.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    abrirLogin();
                }
            }
        );

        botonCrearPlayer.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    abrirCrearPlayer();
                }
            }
        );

        panelPrincipal.addComponentListener(
            new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                    ajustarElementos();
                }
            }
        );

        ajustarElementos();
    }

   
    private JLabel crearBoton(String texto) {
        JLabel boton = new JLabel(texto, SwingConstants.CENTER);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void ajustarElementos() {

        if (panelPrincipal == null || botonLogin == null || botonCrearPlayer == null) {
            return;
        }

        int ancho = panelPrincipal.getWidth();
        int alto = panelPrincipal.getHeight();

        if (ancho <= 0 || alto <= 0) {
            return;
        }

        int anchoBoton = 260;
        int altoBoton = 85;
        int separacion = 12;

        int x = (int) (ancho * 0.41);
        int y = (int) (alto * 0.60);

        botonLogin.setBounds(x, y, anchoBoton, altoBoton);

        botonCrearPlayer.setBounds(x, y + altoBoton + separacion,anchoBoton,altoBoton);

        colocarImagen(botonLogin, imagenLogin, anchoBoton, altoBoton);
        colocarImagen(botonCrearPlayer, imagenCrearPlayer, anchoBoton, altoBoton);
    }

    private void colocarImagen(JLabel etiqueta, Image imagen, int anchoMaximo, int altoMaximo) {

        if (imagen == null) {
            return;
        }

        int anchoOriginal = imagen.getWidth(null);
        int altoOriginal = imagen.getHeight(null);

        if (anchoOriginal <= 0 || altoOriginal <= 0) {
            return;
        }

        double escala = Math.min((double) anchoMaximo / anchoOriginal,(double) altoMaximo / altoOriginal);

        int nuevoAncho = Math.max(1, (int) (anchoOriginal * escala));
        int nuevoAlto = Math.max(1, (int) (altoOriginal * escala));

        Image imagenEscalada = imagen.getScaledInstance(nuevoAncho,nuevoAlto,Image.SCALE_SMOOTH);

        etiqueta.setText("");
        etiqueta.setIcon(new ImageIcon(imagenEscalada));
    }

    private void abrirLogin() {
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }

    private void abrirCrearPlayer() {
        CreatePlayer pantalla = new CreatePlayer();
        pantalla.setVisible(true);
        dispose();
    }
}