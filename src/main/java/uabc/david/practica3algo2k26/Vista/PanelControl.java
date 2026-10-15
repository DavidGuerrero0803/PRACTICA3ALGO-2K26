package uabc.david.practica3algo2k26.Vista;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import uabc.david.practica3algo2k26.Controlador.ControladorJuego;

/**
 * Esta clase representa visual y lógicamente el panel de la derecha del juego.
 */
public class PanelControl {
    private ControladorJuego controlador;
    private VistaTablero tablero;
    private Button botonAcciones;
    private VBox panel;
    private Label mensaje;

    /**
     * Arma el panel y lo deja según el estado actual del juego.
     * @param controlador del juego.
     * @param tablero que hay que redibujar después de cada acción.
     */
    public PanelControl(ControladorJuego controlador, VistaTablero tablero) {
        this.controlador = controlador;
        this.tablero = tablero;

        botonAcciones = crearBoton("Start");
        botonAcciones.setOnAction(event -> {
            ejecutar(this::accionPrincipal);
        });

        panel = new VBox(10);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPrefWidth(200);
        panel.setMinWidth(200);
        panel.setStyle("-fx-background-color: #f0f0f0;");
        panel.getChildren().addAll(botonAcciones);
    }

    private Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.setPrefSize(170, 40);
        return boton;
    }

    /**
     * Ejecuta una acción del juego y después actualiza el tablero y los controles.
     */
    private void ejecutar(Runnable accion) {
        accion.run();
        mensaje.setText("");
        tablero.dibujar();
    }

    /**
     * Botón principal de las acciones del juego:
     * Empezar, tirar los dados y mover los clientes.
     */
    private void accionPrincipal() {
        if (!controlador.isIniciado()) {
            controlador.iniciar();
        } else if (!controlador.isDadosLanzados()) {
            controlador.lanzarDados();
        } else {
            controlador.mover();
        }
    }

    /**
     * Regresa el VBox con los elementos que contenga.
     * @return el contenedor con todos los controles, para ponerlo en la ventana.
     */
    public VBox getPanel() {
        return panel;
    }
}
