
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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelo.GestorJugadores;
import modelo.Jugador;

public class MenuPrincipal extends JFrame {

    private Image imagenFondo;
    private JPanel panelPrincipal;
    private JLabel lblBienvenida;

    public MenuPrincipal() {
        setTitle("Marvel Stratego - Menú Principal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                GestorJugadores.cerrarSesion();
                new PantallaPrincipal().setVisible(true);
            }
        });

        setSize(1200, 750);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        cargarImagenFondo();
        crearInterfaz();
        crearBarraMenu();
    }

    private Jugador jugador() {
        return GestorJugadores.getJugadorActual();
    }


    private void cargarImagenFondo() {
        URL url = getClass().getResource("/recursos/fondo_menu.jpg");

        if (url != null) {
            imagenFondo = new ImageIcon(url).getImage();
        }
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

        lblBienvenida = new JLabel("Bienvenido, " + jugador().getUsername(), SwingConstants.CENTER );
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 28));
        panelPrincipal.add(lblBienvenida);

        panelPrincipal.addComponentListener(
            new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                    lblBienvenida.setBounds(
                        0,
                        (int) (panelPrincipal.getHeight() * 0.80),
                        panelPrincipal.getWidth(),
                        40
                    );
                }
            }
        );
    }

    private void crearBarraMenu() {

        JMenuBar barra = new JMenuBar();

        JMenu menuStratego = new JMenu("STRATEGO – MARVEL HEROES!");
        agregarOpcion(menuStratego, "Partida Nueva", this::partidaNueva);

        JMenu menuPerfil = new JMenu("MI PERFIL");
        agregarOpcion(menuPerfil, "Log de Mis Últimos Juegos", this::verPerfil);
        agregarOpcion(menuPerfil, "Cambiar mi Password", this::cambiarPassword);
        agregarOpcion(menuPerfil, "Eliminar mi Cuenta", this::eliminarCuenta);

        JMenu menuUniverso = new JMenu("UNIVERSO MARVEL");
        agregarOpcion(menuUniverso, "Ranking", this::verRanking);
        agregarOpcion(menuUniverso, "Batallas", this::verBatallas);

        JMenu menuSesion = new JMenu("CERRAR SESIÓN");
        agregarOpcion(menuSesion, "Cerrar Sesión", this::cerrarSesion);

        barra.add(menuStratego);
        barra.add(menuPerfil);
        barra.add(menuUniverso);
        barra.add(menuSesion);

        setJMenuBar(barra);
    }

    private void agregarOpcion(JMenu menu, String texto, Runnable accion) {
        JMenuItem opcion = new JMenuItem(texto);
        opcion.addActionListener(e -> accion.run());
        menu.add(opcion);
    }

    private void partidaNueva() {

  
        ArrayList<Jugador> rivales = new ArrayList<>();

        for (Jugador j : GestorJugadores.getJugadores()) {
            if (j != jugador()) {
                rivales.add(j);
            }
        }

        if (rivales.isEmpty()) {
            DialogoMarvel.mensaje(this,"Partida nueva","Necesitás al menos otro jugador registrado para jugar." );
            return;
        }

        DialogoPartidaNueva dialogo = new DialogoPartidaNueva(this, rivales);
        dialogo.mostrar();

        if (!dialogo.isConfirmado()) {
            return; 
        }
        Jugador rival = GestorJugadores.buscar(dialogo.getRival());


        VentanaJuego juego = new VentanaJuego(jugador(), rival, dialogo.getBando());
        juego.setVisible(true);
    }

 
    private void verPerfil() {
        new DialogoPerfil(this, jugador()).mostrar();
    }

    private void cambiarPassword() {
        new DialogoCuenta(this, jugador(), TipoCuenta.CAMBIAR_PASSWORD).mostrar();
    }

    private void eliminarCuenta() {
        DialogoCuenta dialogo = new DialogoCuenta(this, jugador(), TipoCuenta.ELIMINAR_CUENTA);
        dialogo.mostrar();

        if (dialogo.isCuentaEliminada()) {
            volverAlInicio();
            
        }
    }

  

    private void verRanking() {
        new DialogoUniverso(this, TipoUniverso.RANKING).mostrar();
    }

    private void verBatallas() {
        new DialogoUniverso(this, TipoUniverso.BATALLAS).mostrar();
    }

    private void cerrarSesion() {

        boolean confirmado = DialogoMarvel.confirmar(
            this, "Cerrar sesión", "¿Seguro que querés cerrar sesión?");

        if (confirmado) {
            GestorJugadores.cerrarSesion();
            volverAlInicio();
        }
    }

  
    private void volverAlInicio() {
        new PantallaPrincipal().setVisible(true);
        dispose();
    }
}