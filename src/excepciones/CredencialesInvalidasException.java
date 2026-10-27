/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author USER
 */
public class CredencialesInvalidasException extends Exception {

    public CredencialesInvalidasException() {
        super("El username o el password son incorrectos. "
                + "Verificá tus datos e intentá de nuevo.");
    }
}