package uabc.david.practica3algo2k26.Vista;

import javafx.scene.layout.Pane;

import uabc.david.practica3algo2k26.Controlador.ControladorJuego;

/**
 * Esta clase dibuja el tablero/ventana del juego.
 */
public class VistaTablero {
    private ControladorJuego controlador;
    private Pane tablero;

    /**
     * Crea un tablero con el estado actual del juego.
     * @param controlador del juego.
     */
    public VistaTablero(ControladorJuego controlador) {
        this.controlador = controlador;

        tablero = new Pane();
        tablero.setPrefSize(1000, 700);
        tablero.setMinSize(1000, 700);
        tablero.setMaxSize(1000, 700);

    }

    /**
     * Regresa el pane del tablero.
     * @return el Pane de tablero para agregarse a Scene.
     */
    public Pane getTablero() {
        return tablero;
    }
}

