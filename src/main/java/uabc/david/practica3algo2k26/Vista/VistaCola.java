package uabc.david.practica3algo2k26.Vista;

import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import uabc.david.practica3algo2k26.Modelo.Cliente;
import uabc.david.practica3algo2k26.Modelo.ColaCircular;

/**
 * Representa una cola de clientes (un círculo por cliente, siendo 5 por fila).
 * Sirve para la cola de cada estación de persona.
 */
public class VistaCola {
    private FlowPane panel;

    /**
     * Crea la vista de una cola con su contenido actual.
     * @param cola de clientes a dibujar.
     */
    public VistaCola(ColaCircular<Cliente> cola) {
        panel = new FlowPane();
        panel.setHgap(2);
        panel.setVgap(2);
        panel.setPrefWrapLength(120);

        String contenido = cola.mostrarDatos();
        for (int i = 0; i < contenido.length(); i++) {
            ImageView imagenCliente = new VistaCliente(contenido.charAt(i) == 'I').mostrarCliente();
            if (imagenCliente != null) {
                panel.getChildren().add(imagenCliente);
            }
        }
    }

    public FlowPane getPanel() {
        return panel;
    }
}
