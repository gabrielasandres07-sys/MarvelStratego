/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author USER
 */

import excepciones.PasswordInvalidoException;
import excepciones.UsuarioDuplicadoException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import modelo.GestorJugadores;

public class CreatePlayer extends JFrame {

    private Image imagenFondo;
    private Image imagenBtnCrear;
    private Image imagenBtnBack;

    private JPanel panelPrincipal;
    private JPanel panelFormulario;

    private JLabel lblUsername;
    private JLabel lblPassword;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkMostrar;
    private JButton btnCrear;
    private JButton btnVolver;

    public CreatePlayer() {
        setTitle("Marvel Stratego - Crear Player");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                new PantallaPrincipal().setVisible(true);
            }
        });

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
        imagenFondo = cargarImagen("fondo_crepla.jpg");
        imagenBtnCrear = cargarImagen("btn_crepla_1.png");
        imagenBtnBack = cargarImagen("btn_back.png");
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

        crearFormulario();

        panelPrincipal.addComponentListener(
            new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                    ajustarElementos();
                }
            }
        );

        ajustarElementos();

        SwingUtilities.invokeLater(() ->
            txtUsername.requestFocusInWindow()
        );
    }

    private void crearFormulario() {

        panelFormulario = new JPanel(null);
        panelFormulario.setOpaque(false);
        panelPrincipal.add(panelFormulario);

        lblUsername = crearEtiqueta("USUARIO");
        panelFormulario.add(lblUsername);

        txtUsername = new JTextField();
        configurarCampo(txtUsername);
        panelFormulario.add(txtUsername);

        lblPassword = crearEtiqueta("CONTRASEÑA (5 CARACTERES)");
        panelFormulario.add(lblPassword);

        txtPassword = new JPasswordField();
        configurarCampo(txtPassword);
        panelFormulario.add(txtPassword);

        chkMostrar = UtilPassword.crearMostrar(txtPassword, Color.WHITE);
        panelFormulario.add(chkMostrar);

        btnCrear = new JButton();
        configurarBotonImagen(btnCrear, "CREAR");
        panelFormulario.add(btnCrear);

        btnVolver = new JButton();
        configurarBotonImagen(btnVolver, "VOLVER");
        panelFormulario.add(btnVolver);

        btnCrear.addActionListener(e -> crearJugador());
        txtPassword.addActionListener(e -> crearJugador());
        txtUsername.addActionListener(e -> txtPassword.requestFocusInWindow());
        btnVolver.addActionListener(e -> volverInicio());
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));
        return etiqueta;
    }

    private void configurarCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setBackground(new Color(235, 240, 248));
        campo.setForeground(new Color(20, 25, 35));
        campo.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
    }

    private void configurarBotonImagen(JButton boton, String texto) {
        boton.setText(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setBorder(null);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void ajustarElementos() {

        if (panelPrincipal == null || panelFormulario == null) {
            return;
        }

        int ancho = panelPrincipal.getWidth();
        int alto = panelPrincipal.getHeight();

        if (ancho <= 0 || alto <= 0) {
            return;
        }

        int anchoPanel = 390;
        int altoPanel = 320;

        int x = (ancho - anchoPanel) / 2;
        int y = (alto - altoPanel) / 2 + 40;

        panelFormulario.setBounds(x, y, anchoPanel, altoPanel);

        int xInterior = 33;
        int anchoInterior = 320;

        lblUsername.setBounds(xInterior, 20, anchoInterior, 25);
        txtUsername.setBounds(xInterior, 48, anchoInterior, 42);
        lblPassword.setBounds(xInterior, 105, anchoInterior, 25);
        txtPassword.setBounds(xInterior, 133, anchoInterior, 42);

        chkMostrar.setBounds(xInterior, 180, anchoInterior, 22);

        btnCrear.setBounds(xInterior + 55, 210, 210, 45);
        btnVolver.setBounds(xInterior + 55, 265, 210, 45);

        actualizarImagenBoton(btnCrear, imagenBtnCrear);
        actualizarImagenBoton(btnVolver, imagenBtnBack);
    }

    private void actualizarImagenBoton(JButton boton, Image imagen) {

        if (imagen == null) {
            return;
        }

        int anchoOriginal = imagen.getWidth(null);
        int altoOriginal = imagen.getHeight(null);

        if (anchoOriginal <= 0 || altoOriginal <= 0) {
            return;
        }

        double escala = Math.min((double) boton.getWidth() / anchoOriginal,(double) boton.getHeight() / altoOriginal);

        int nuevoAncho = Math.max(1, (int) Math.round(anchoOriginal * escala));
        int nuevoAlto = Math.max(1, (int) Math.round(altoOriginal * escala));

        Image imagenEscalada = imagen.getScaledInstance( nuevoAncho,nuevoAlto, Image.SCALE_SMOOTH);

        boton.setText("");
        EfectoHover.aplicar(boton, imagenEscalada);
    }

    private void crearJugador() {

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresá un usuario.",  "Campos incompletos",JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            GestorJugadores.registrar(username, password);

            JOptionPane.showMessageDialog( this, "Player \"" + username + "\" creado correctamente.","Player creado", JOptionPane.INFORMATION_MESSAGE);

            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
            dispose();

        } catch (UsuarioDuplicadoException ex) {

            JOptionPane.showMessageDialog(this,ex.getMessage(),"Usuario no disponible",JOptionPane.ERROR_MESSAGE );

            txtUsername.selectAll();
            txtUsername.requestFocusInWindow();

        } catch (PasswordInvalidoException ex) {

            JOptionPane.showMessageDialog( this, ex.getMessage(),"Contraseña inválida",JOptionPane.ERROR_MESSAGE);

            txtPassword.setText("");
            txtPassword.requestFocusInWindow();
        }
    }

    private void volverInicio() {
        PantallaPrincipal pantalla = new PantallaPrincipal();
        pantalla.setVisible(true);
        dispose();
    }
}