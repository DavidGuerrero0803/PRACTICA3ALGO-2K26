package uabc.david.practica3algo2k26.Vista;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import uabc.david.practica3algo2k26.Controlador.ControladorJuego;
import uabc.david.practica3algo2k26.Modelo.Persona;

import java.util.ArrayList;

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

        dibujar();
    }

    /**
     * Dibuja el tablero con el estado actual del juego.
     */
    public void dibujar() {
        tablero.getChildren().clear();
        ArrayList<Persona> personas = controlador.getPersonas();

        for (int i = 0; i < personas.size(); i++) {
            int columna, fila, columnaDado, filaDado, columnaCola, filaCola;
            // Dibujado en la fila superior.
            if (i < 4) {
                columna = 2 * i + 1;
                fila = 0;
                columnaDado = columna;
                filaDado = 1;
                columnaCola = columna - 1;
                filaCola = 1;
                // Dibujado en el costado derecho.
            } else if (i < 6) {
                columna = 8;
                fila = 2 * (i - 3);
                columnaDado = 9;
                filaDado = fila;
                columnaCola = 8;
                filaCola = fila - 1;
                // Dibujado en fila inferior.
            } else {
                columna = 2 * (9 - i) + 1;
                fila = 6;
                columnaDado = columna;
                filaDado = 5;
                columnaCola = columna + 1;
                filaCola = 5;
            }

            VistaPersona vista = new VistaPersona(personas.get(i));
            colocarElemento(vista.getImagenPersona(), columna, fila);
            if (i < 6) {
                colocarElemento(vista.getPanelDados(), columnaDado, filaDado);
                colocarElemento(vista.getPanelClientes(), columnaCola, filaCola);
            }
        }
    }

    private void colocarElemento(Node nodo, int columna, int fila) {
        if (nodo != null) {
            nodo.setLayoutX(columna * 100);
            nodo.setLayoutY(fila * 100);
            tablero.getChildren().add(nodo);
        }
    }

    /**
     * Regresa el pane del tablero.
     * @return el Pane de tablero para agregarse a Scene.
     */
    public Pane getTablero() {
        return tablero;
    }
}

