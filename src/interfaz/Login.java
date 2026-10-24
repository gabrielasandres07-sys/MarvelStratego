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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Login extends JFrame {

    private Image imagenFondo;
    private Image imagenBtnLogin;
    private Image imagenBtnBack;

    private JPanel panelPrincipal;
    private JPanel panelLogin;

    private JLabel lblUsername;
    private JLabel lblPassword;

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnIngresar;
    private JButton btnVolver;

    public Login() {
        setTitle("Marvel Stratego - Login");
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
        imagenFondo = cargarImagen("fondo_login.jpg");
        imagenBtnLogin = cargarImagen("btn_login.png");
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
                    g2.setColor(new Color(12, 12, 12));
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

        panelLogin = new JPanel(null);
        panelLogin.setOpaque(false);
        panelLogin.setBorder(null);

        panelPrincipal.add(panelLogin);

        lblUsername = new JLabel("USUARIO");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 13));
        panelLogin.add(lblUsername);

        txtUsername = new JTextField();
        configurarCampo(txtUsername);
        panelLogin.add(txtUsername);

        lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
        panelLogin.add(lblPassword);

        txtPassword = new JPasswordField();
        configurarCampo(txtPassword);
        panelLogin.add(txtPassword);

        btnIngresar = new JButton();
        configurarBotonImagen(btnIngresar, "LOG IN");
        panelLogin.add(btnIngresar);

        btnVolver = new JButton();
        configurarBotonImagen(btnVolver, "VOLVER");
        panelLogin.add(btnVolver);

        btnIngresar.addActionListener(e -> validarCredenciales());
        txtPassword.addActionListener(e -> validarCredenciales());
        txtUsername.addActionListener(e -> txtPassword.requestFocusInWindow());
        btnVolver.addActionListener(e -> volverInicio());
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

        if (panelPrincipal == null || panelLogin == null) {
            return;
        }

        int ancho = panelPrincipal.getWidth();
        int alto = panelPrincipal.getHeight();

        if (ancho <= 0 || alto <= 0) {
            return;
        }

        int anchoPanel = 390;
        int altoPanel = 300;

        int x = (ancho - anchoPanel) / 2;
        int y = (alto - altoPanel) / 2 + 40;

        panelLogin.setBounds(x, y, anchoPanel, altoPanel);

        int xInterior = 33;
        int anchoInterior = 320;

        lblUsername.setBounds(xInterior, 20, anchoInterior, 25);
        txtUsername.setBounds(xInterior, 48, anchoInterior, 42);
        lblPassword.setBounds(xInterior, 105, anchoInterior, 25);
        txtPassword.setBounds(xInterior, 133, anchoInterior, 42);

        btnIngresar.setBounds(xInterior + 55, 195, 210, 45);
        btnVolver.setBounds(xInterior + 55, 250, 210, 45);

        actualizarImagenBoton(btnIngresar, imagenBtnLogin);
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

        double escala = Math.min(
            (double) boton.getWidth() / anchoOriginal,
            (double) boton.getHeight() / altoOriginal
        );

        int nuevoAncho = Math.max(1, (int) Math.round(anchoOriginal * escala));
        int nuevoAlto = Math.max(1, (int) Math.round(altoOriginal * escala));

        Image imagenEscalada = imagen.getScaledInstance(
            nuevoAncho,
            nuevoAlto,
            Image.SCALE_SMOOTH
        );

        boton.setText("");
        EfectoHover.aplicar(boton, imagenEscalada);
    }

    private void validarCredenciales() {

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Ingresá tu usuario y tu contraseña.",
                "Campos incompletos",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // PENDIENTE, q se valide el usuario  y abrir el Menú Principal
        JOptionPane.showMessageDialog(
            this,
            "El inicio de sesión todavía no está disponible.",
            "Login",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void volverInicio() {
        PantallaPrincipal pantalla = new PantallaPrincipal();
        pantalla.setVisible(true);
        dispose();
    }
}