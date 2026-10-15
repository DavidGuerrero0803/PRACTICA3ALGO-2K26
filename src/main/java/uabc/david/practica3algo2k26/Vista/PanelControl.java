package uabc.david.practica3algo2k26.Vista;

import uabc.david.practica3algo2k26.Controlador.ControladorJuego;

/**
 * Esta clase representa visual y lógicamente el panel de la derecha del juego.
 */
public class PanelControl {
    private ControladorJuego controlador;
    private VistaTablero tablero;

    /**
     * Arma el panel y lo deja según el estado actual del juego.
     * @param controlador del juego.
     * @param tablero que hay que redibujar después de cada acción.
     */
    public PanelControl(ControladorJuego controlador, VistaTablero tablero) {
        this.controlador = controlador;
        this.tablero = tablero;
    }
}
