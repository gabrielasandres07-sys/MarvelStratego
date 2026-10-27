

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;
/**
 *
 * @author USER
 */
public class PasswordInvalidoException extends Exception {

    public PasswordInvalidoException() {
        super("El password debe tener exactamente 5 caracteres.");
    }
}