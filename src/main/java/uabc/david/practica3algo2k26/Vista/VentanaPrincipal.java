package uabc.david.practica3algo2k26.Vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import uabc.david.practica3algo2k26.Controlador.ControladorJuego;

/**
 * Ventana principal del juego.
 */
public class VentanaPrincipal extends Application {
    private ControladorJuego controlador;

    @Override
    public void start(Stage stage) {
        controlador = new ControladorJuego(10, 4, 20);

        Scene scene = new Scene(construirVentana(), 1200, 720);
        stage.setTitle("The Dice Game");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Construye y muestra la ventana.
     * @return el panel raíz de la escena.
     */
    public BorderPane construirVentana() {
        BorderPane raiz = new BorderPane();
        raiz.setStyle("-fx-background-color: white;");

        return raiz;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
