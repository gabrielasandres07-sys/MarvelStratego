/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author USER
 */



import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class CartaTablero {

    private final String nombre;
    private final Image imagen;

    public CartaTablero(String nombre, String rutaImagen) {
        this.nombre = nombre;

        URL url = getClass().getResource(rutaImagen);

        if (url != null) {
            this.imagen = new ImageIcon(url).getImage();
        } else {
            this.imagen = null;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Image getImagen() {
        return imagen;
    }

    public void dibujar(Graphics2D g2,int x,int y,int ancho,int alto) {

        if (imagen != null) {
            g2.drawImage(imagen,x,y, ancho,alto, null);
        }
    }
}
