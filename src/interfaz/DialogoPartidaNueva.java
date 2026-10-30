/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;
/**
 *
 * @author USER
 */

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import modelo.Bando;
import modelo.Jugador;

public class DialogoPartidaNueva extends DialogoMarvel {

    private final JComboBox<String> comboRivales;
    private final JToggleButton btnHeroes;
    private final JToggleButton btnVillanos;

    private boolean confirmado = false;
    private String rival;
    private Bando bando;

    public DialogoPartidaNueva(Window duenio, List<Jugador> rivales) {
        super(duenio, "Partida nueva");

   
        agregar(crearSubtitulo("Elegí a tu rival"));
        agregarEspacio(10);

        comboRivales = new JComboBox<>();
        for (Jugador j : rivales) {
            comboRivales.addItem(j.getUsername());
        }
        estilizarCombo(comboRivales);
        agregar(comboRivales);
        agregarEspacio(22);

  
        agregar(crearSubtitulo("Elegí tu bando"));
        agregarEspacio(10);

        ButtonGroup grupoBandos = new ButtonGroup();
        btnHeroes = crearBotonBando("Héroes", grupoBandos);
        btnVillanos = crearBotonBando("Villanos", grupoBandos);
        btnHeroes.setSelected(true);

        JPanel bandos = new JPanel(new GridLayout(1, 2, 12, 0));
        bandos.setOpaque(false);
        bandos.add(btnHeroes);
        bandos.add(btnVillanos);
        agregar(bandos);

        agregarEspacio(10);
        agregar(crearEtiqueta( "Tu rival jugará con el bando contrario.",13, Font.BOLD, TEXTO_SUAVE));

        agregarBoton("Jugar", true, this::confirmar);
        agregarBoton("Cancelar", false, this::dispose);
    }

 
    private JToggleButton crearBotonBando(String texto, ButtonGroup grupo) {

        Image imagen = cargarImagenBoton(texto, 60);

        if (imagen == null) {
            JToggleButton opcion = crearOpcion(texto.toUpperCase(), grupo, 20);
            opcion.setBorder(BorderFactory.createCompoundBorder(
                opcion.getBorder(), BorderFactory.createEmptyBorder(12, 0, 12, 0)));
            return opcion;
        }

        JToggleButton opcion = new JToggleButton();
        opcion.setContentAreaFilled(false);
        opcion.setBorderPainted(false);
        opcion.setFocusPainted(false);
        opcion.setOpaque(false);
        opcion.setBorder(null);
        opcion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        EfectoHover.aplicarOpcion(opcion, imagen);
        grupo.add(opcion);
        return opcion;
    }

 
    private void estilizarCombo(JComboBox<String> combo) {
        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton flecha = new BasicArrowButton(
                    SwingConstants.SOUTH, NEGRO, NEGRO, BLANCO, NEGRO);
                flecha.setBorder(BorderFactory.createEmptyBorder());
                flecha.setPreferredSize(new Dimension(40, 40));
                return flecha;
            }
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle area, boolean foco) {
                g.setColor(BLANCO);
                g.fillRect(area.x, area.y, area.width, area.height);
            }
        });

        combo.setFont(new Font("Arial Black", Font.PLAIN, 15));
        combo.setBackground(BLANCO);
        combo.setForeground(NEGRO);
        combo.setBorder(BorderFactory.createLineBorder(NEGRO, 3));
        combo.setFocusable(false);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        combo.setPreferredSize(new Dimension(300, 46));

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor,
                    int indice, boolean seleccionado, boolean foco) {

                JLabel celda = (JLabel) super.getListCellRendererComponent(
                    lista, valor, indice, seleccionado, foco);

                celda.setFont(new Font("Arial Black", Font.PLAIN, 15));
                celda.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                celda.setBackground(seleccionado ? ROJO : BLANCO);
                celda.setForeground(seleccionado ? BLANCO : NEGRO);
                return celda;
            }
        });
    }

    private void confirmar() {
        rival = (String) comboRivales.getSelectedItem();
        bando = btnHeroes.isSelected() ? Bando.HEROES : Bando.VILLANOS;
        confirmado = true;
        dispose();
    }


    public boolean isConfirmado() {
        return confirmado;
    }

    public String getRival() {
        return rival;
    }

    public Bando getBando() {
        return bando;
    }
}