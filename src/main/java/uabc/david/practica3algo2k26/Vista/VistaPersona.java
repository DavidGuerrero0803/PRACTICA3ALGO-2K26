package uabc.david.practica3algo2k26.Vista;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import uabc.david.practica3algo2k26.Modelo.Cliente;
import uabc.david.practica3algo2k26.Modelo.ColaCircular;
import uabc.david.practica3algo2k26.Modelo.Dado;
import uabc.david.practica3algo2k26.Modelo.Persona;

import java.net.URL;

/**
 * Representa visualmente la estación de 1 persona, por lo que
 * contendrá la silueta de la persona, su dado o dados y los clientes.
 */
public class VistaPersona {
    private Persona persona;
    private VBox estacionPane;
    private ImageView imagenPersona;

    public VistaPersona(Persona persona) {
        this.persona = persona;
        this.estacionPane = new VBox(5);
        this.estacionPane.setAlignment(Pos.CENTER);
        this.imagenPersona = new ImageView();
        construirEstacion();
    }

    /**
     * Construye la estructura visual de la estación de trabajo.
     */
    private void construirEstacion() {
        String rutaPersona = String.format("/Personas/persona%d.png", persona.getIdEstacion() + 1);
        imagenPersona = crearImagen(rutaPersona, 100);

        HBox contenedorDados = new HBox(3);
        contenedorDados.setAlignment(Pos.CENTER);
        for (Dado dado : persona.getDados()) {
            VistaDado vistaDado = new VistaDado(dado);
            ImageView imgDado = vistaDado.mostrarDado();
            if (imgDado != null) {
                contenedorDados.getChildren().add(imgDado);
            }
        }

        Label lblEstacion = new Label("P" + (persona.getIdEstacion() + 1));
        lblEstacion.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        FlowPane contenedorClientes = new FlowPane();
        contenedorClientes.setHgap(2);
        contenedorClientes.setVgap(2);
        contenedorClientes.setMaxWidth(120);
        contenedorClientes.setAlignment(Pos.CENTER);

        // Renderiza cada cliente presente en la cola circular.
        ColaCircular<Cliente> cola = persona.getCola();
        int totalClientes = cola.getCantidadElementos();

        // Oculta la cola visual para la estación 0 (es la reserva infinita).
        if (!persona.esPrimera()) {
            for (int i = 0; i < totalClientes; i++) {
                // Recupera el arreglo interno para dibujar los objetos en orden de llegada.
                Cliente cliente = cola.getColaCircular()[(cola.getCantidadElementos() == 0) ? 0 : i];
                if (cliente != null) {
                    VistaCliente vistaCliente = new VistaCliente(cliente);
                    ImageView imgCliente = vistaCliente.mostrarCliente();
                    if (imgCliente != null) {
                        contenedorClientes.getChildren().add(imgCliente);
                    }
                }
            }
        }

        // Une todos los elementos para formar así la estación.
        if (imagenPersona != null) {
            estacionPane.getChildren().addAll(lblEstacion, contenedorDados, imagenPersona, contenedorClientes);
        }
    }

    private ImageView crearImagen(String url, int size) {
        try {
            URL resource = getClass().getResource(url);
            if (resource != null) {
                Image image = new Image(resource.toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(size);
                imageView.setFitHeight(size);
                imageView.setPreserveRatio(true);
                return imageView;
            } else {
                System.err.println("Error: No se encontró la imagen en " + url);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public VBox getPane() {
        return estacionPane;
    }

    public Persona getPersona() {
        return persona;
    }
}
