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
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;


public final class UtilPassword {


    private UtilPassword() {
    }

    public static JCheckBox crearMostrar(JPasswordField campo, Color colorTexto) {

        
        char caracterOculto = campo.getEchoChar();

        JCheckBox casilla = new JCheckBox("Mostrar contraseña");
        casilla.setOpaque(false);
        casilla.setForeground(colorTexto);
        casilla.setFont(new Font("Arial", Font.PLAIN, 12));
        casilla.setFocusPainted(false);
        casilla.setCursor(new Cursor(Cursor.HAND_CURSOR));

        casilla.addActionListener(e -> {
            if (casilla.isSelected()) {
                campo.setEchoChar((char) 0);        
            } else {
                campo.setEchoChar(caracterOculto);  
            }
        });

        return casilla;
    }
}