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
import java.awt.Window;
import javax.swing.JPasswordField;
import modelo.GestorJugadores;
import modelo.Jugador;
public class DialogoCuenta extends DialogoMarvel {

    private final Jugador jugador;
    private final JPasswordField txtActual;
    private JPasswordField txtNueva;
    private boolean cuentaEliminada = false;

    public DialogoCuenta(Window duenio, Jugador jugador, TipoCuenta tipo) {
        super(duenio, tipo == TipoCuenta.CAMBIAR_PASSWORD ? "Cambiar contraseña" : "Eliminar cuenta");
        this.jugador = jugador;
        this.txtActual = crearCampoPassword();

        switch (tipo) {
            case CAMBIAR_PASSWORD:
                armarCambiarPassword();
                break;
            case ELIMINAR_CUENTA:
                armarEliminarCuenta();
                break;
        }
    }

   
    public boolean isCuentaEliminada() {
        return cuentaEliminada;
    }


    private void armarCambiarPassword() {

        txtNueva = crearCampoPassword();

        agregarCampo("Contraseña actual", txtActual);
        agregarEspacio(14);
        agregarCampo("Contraseña nueva (5 caracteres)", txtNueva);

        txtActual.addActionListener(e -> txtNueva.requestFocusInWindow());
        txtNueva.addActionListener(e -> guardarPassword());

        agregarBoton("Guardar", true, this::guardarPassword);
        agregarBoton("Cancelar", false, this::dispose);
    }

    private void guardarPassword() {

        if (!jugador.passwordCorrecto(texto(txtActual))) {
            mensaje(this, "Error", "La contraseña actual es incorrecta.");
            txtActual.setText("");
            txtActual.requestFocusInWindow();
            return;
        }

        try {
            GestorJugadores.validarPassword(texto(txtNueva));
            jugador.setPassword(texto(txtNueva));

            mensaje(this, "Listo", "Tu contraseña se cambió correctamente.");
            dispose();

        } catch (PasswordInvalidoException ex) {
            mensaje(this, "Error", ex.getMessage());
            txtNueva.setText("");
            txtNueva.requestFocusInWindow();
        }
    }


    private void armarEliminarCuenta() {

        agregarTexto("Esta acción no se puede deshacer. Se borrarán todos tus datos.");
        agregarEspacio(16);
        agregarCampo("Ingresá tu contraseña para confirmar", txtActual);

        txtActual.addActionListener(e -> eliminar());

        agregarBoton("Eliminar", true, this::eliminar);
        agregarBoton("Cancelar", false, this::dispose);
    }

    private void eliminar() {

        if (!jugador.passwordCorrecto(texto(txtActual))) {
            mensaje(this, "Error", "Contraseña incorrecta. La cuenta no se eliminó.");
            txtActual.setText("");
            txtActual.requestFocusInWindow();
            return;
        }

        GestorJugadores.eliminar(jugador);
        cuentaEliminada = true;

        mensaje(this, "Cuenta eliminada", "Tu cuenta fue eliminada.");
        dispose();
    }

    private void agregarCampo(String etiqueta, JPasswordField campo) {
        agregar(crearEtiquetaCampo(etiqueta));
        agregarEspacio(6);
        agregar(campo);
        agregarEspacio(4);
        agregar(UtilPassword.crearMostrar(campo, BLANCO));
    }

    private String texto(JPasswordField campo) {
        return new String(campo.getPassword());
    }
}
