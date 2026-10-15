package uabc.david.practica3algo2k26.Vista;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import uabc.david.practica3algo2k26.Controlador.ControladorJuego;

/**
 * Esta clase representa visual y lógicamente el panel de la derecha del juego.
 */
public class PanelControl {
    private ControladorJuego controlador;
    private VistaTablero tablero;
    private Button botonAcciones;
    private Button botonReset;
    private Button botonDados;
    private VBox panel;
    private Label mensaje;
    private Label turno;
    private ComboBox<Integer> comboOrigen;
    private ComboBox<Integer> comboDestino;

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

        botonReset = crearBoton("Reset");
        botonReset.setOnAction(event -> {
            ejecutar(controlador::reiniciar);
        });

        Button botonActivity = crearBoton("Activity");
        botonActivity.setOnAction(event -> {
            new GraficaActivity(controlador.getHistorial()).mostrar();
        });
        Button botonThroughput = crearBoton("Throughput");
        botonThroughput.setOnAction(event -> {
            new GraficaThroughput(controlador.getHistorial()).mostrar();
        });
        Button botonNumberInSystem = crearBoton("Number in system");
        Button botonTimeInSystem = crearBoton("Time in system");

        Region espacio = new Region();
        VBox.setVgrow(espacio, Priority.ALWAYS);

        turno = new Label();
        turno.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        panel = new VBox(10);
        panel.setPadding(new Insets(15));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPrefWidth(200);
        panel.setMinWidth(200);
        panel.setStyle("-fx-background-color: #f0f0f0;");
        panel.getChildren().addAll(botonReset, new Region(), botonActivity, botonThroughput,
                botonNumberInSystem, botonTimeInSystem, construirPanelDados(), espacio, turno, botonAcciones);

        actualizarControles();
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
        actualizarControles();
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
     * Actualiza los botones, selector de dados y el contador según el estado del juego.
     */
    private void actualizarControles() {
        boolean terminado = controlador.haTerminado();
        if (terminado) {
            botonAcciones.setText("End");
        } else if (!controlador.isIniciado()) {
            botonAcciones.setText("Start");
        } else if (!controlador.isDadosLanzados()) {
            botonAcciones.setText("Roll");
        } else {
            botonAcciones.setText("Move");
        }
        botonAcciones.setDisable(terminado);
        botonReset.setDisable(!terminado);
        turno.setText("Turns " + controlador.getTurnoActual());

        boolean puedeMoverDados = controlador.puedeMoverDados();
        comboOrigen.setDisable(!puedeMoverDados);
        comboDestino.setDisable(!puedeMoverDados);
        botonDados.setDisable(!puedeMoverDados);
    }

    /**
     * Construye la sección para mover un dado de una persona a otra.
     */
    private VBox construirPanelDados() {
        comboOrigen = new ComboBox<>();
        comboDestino = new ComboBox<>();

        for (int i = 1; i <= 10; i++) {
            comboOrigen.getItems().add(i);
            comboDestino.getItems().add(i);
        }
        comboOrigen.setPrefWidth(75);
        comboDestino.setPrefWidth(75);

        HBox origen = new HBox(8, new Label("From"), comboOrigen);
        HBox destino = new HBox(8, new Label("To"), comboDestino);
        origen.setAlignment(Pos.CENTER_RIGHT);
        destino.setAlignment(Pos.CENTER_RIGHT);

        botonDados = crearBoton("Move Dice");
        botonDados.setOnAction(event -> {
            moverDados();
        });

        mensaje = new Label();
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(170);
        mensaje.setStyle("-fx-text-fill: #b00020;");

        VBox seccion = new VBox(6, new Label("Change Dice Position"), origen, destino, botonDados, mensaje);

        seccion.setAlignment(Pos.TOP_CENTER);
        seccion.setPadding(new Insets(15, 0, 0, 0));
        return seccion;
    }

    /**
     * Mueve un dado de la persona de origen a la de destino (según lo elegido).
     */
    private void moverDados() {
        Integer origen = comboOrigen.getValue();
        Integer destino = comboDestino.getValue();
        if (origen == null || destino == null) {
            mensaje.setText("Select the origin person and the destination person.");
        } else if (origen.equals(destino)) {
            mensaje.setText("Origin and destination are the same person.");
        } else if (!controlador.transferirDado(origen - 1, destino - 1)) {
            mensaje.setText("Person " + origen + " has no dice.");
        } else {
            ejecutar(() -> {});
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
