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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

public final class EfectoHover {

    private static final Color BLANCO = Color.WHITE;
    private static final Color ROJO = new Color(234, 36, 41);
    private static final Color GRIS = new Color(205, 205, 205);
    private static final Color ROJO_OSCURO = new Color(170, 20, 28);

  
    private EfectoHover() {
    }

    public static void aplicar(JButton boton, Image imagen) {
        boton.setIcon(new ImageIcon(imagen));
        boton.setRolloverIcon(recolorear(imagen, BLANCO, ROJO));
        boton.setPressedIcon(recolorear(imagen, GRIS, ROJO_OSCURO));
        boton.setRolloverEnabled(true);
    }

 
    public static void aplicarOpcion(JToggleButton opcion, Image imagen) {
        ImageIcon marcado = recolorear(imagen, BLANCO, ROJO);

        opcion.setIcon(new ImageIcon(imagen));
        opcion.setRolloverIcon(recolorear(imagen, GRIS, ROJO_OSCURO));
        opcion.setSelectedIcon(marcado);
        opcion.setRolloverSelectedIcon(marcado);
        opcion.setRolloverEnabled(true);
    }

    public static void aplicar(JLabel etiqueta, Image imagen) {

        etiqueta.putClientProperty("iconoNormal", new ImageIcon(imagen));
        etiqueta.putClientProperty("iconoEncima", recolorear(imagen, BLANCO, ROJO));
        etiqueta.putClientProperty("iconoPresionado", recolorear(imagen, GRIS, ROJO_OSCURO));
        usarIcono(etiqueta, "iconoNormal");

        if (etiqueta.getClientProperty("tieneEfecto") == null) {
            etiqueta.putClientProperty("tieneEfecto", true);

            etiqueta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    usarIcono(etiqueta, "iconoEncima");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    usarIcono(etiqueta, "iconoNormal");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    usarIcono(etiqueta, "iconoPresionado");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    usarIcono(etiqueta, "iconoEncima");
                }
            });
        }
    }

    private static void usarIcono(JLabel etiqueta, String clave) {
        etiqueta.setIcon((ImageIcon) etiqueta.getClientProperty(clave));
    }

    private static ImageIcon recolorear(Image imagen, Color fondo, Color texto) {
        Image cargada = new ImageIcon(imagen).getImage();
        int ancho = cargada.getWidth(null);
        int alto = cargada.getHeight(null);

        if (ancho <= 0 || alto <= 0) {
            return new ImageIcon(imagen);
        }
        BufferedImage copia = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = copia.createGraphics();
        g2.drawImage(cargada, 0, 0, null);
        g2.dispose();

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {

                Color pixel = new Color(copia.getRGB(x, y), true);

            
                float claridad = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / (3f * 255);

                int r = mezclar(fondo.getRed(), texto.getRed(), claridad);
                int g = mezclar(fondo.getGreen(), texto.getGreen(), claridad);
                int b = mezclar(fondo.getBlue(), texto.getBlue(), claridad);

                copia.setRGB(x, y, new Color(r, g, b, pixel.getAlpha()).getRGB());
            }
        }

        return new ImageIcon(copia);
    }

    private static int mezclar(int desde, int hasta, float proporcion) {
        return Math.round(desde + (hasta - desde) * proporcion);
    }
}