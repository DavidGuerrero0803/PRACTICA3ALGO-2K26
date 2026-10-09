package uabc.david.practica3algo2k26.Vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uabc.david.practica3algo2k26.Modelo.Cliente;

import java.net.URL;

/**
 * Renderiza visualmente a un cliente individual.
 * Selecciona el cliente según su tipo (inicial es gris, nuevo es azul).
 */
public class VistaCliente {
    private Cliente cliente;
    private ImageView imagenCliente;

    public VistaCliente(Cliente cliente) {
        this.cliente = cliente;
        this.imagenCliente = new ImageView();
    }

    /**
     * Carga y devuelve el ImageView del cliente configurado.
     */
    public ImageView mostrarCliente() {
        String ruta = construirRuta();
        return crearImagen(ruta, 18);
    }

    /**
     * Determina la ruta del recurso según si son clientes iniciales o no.
     */
    public String construirRuta() {
        if (cliente.esInicial()) {
            return "/Clientes/ClienteGris.png";
        } else {
            return "/Clientes/ClienteAzul.png";
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

    public ImageView getImagenCliente() {
        return imagenCliente;
    }
}
