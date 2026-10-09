package uabc.david.practica3algo2k26.Vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uabc.david.practica3algo2k26.Modelo.Dado;

import java.net.URL;

/**
  * Representa visualmente la cara actual de un dado en la interfaz.
 */
public class VistaDado {
    private Dado dado;
    private ImageView imagenDado;

    public VistaDado(Dado dado) {
        this.dado = dado;
        this.imagenDado = new ImageView();
    }

    /**
     * Genera la vista del dado.
     */
    public ImageView mostrarDado() {
        String ruta = construirRuta();
        return crearImagen(ruta, 50);
    }

    /**
     * Construye la ruta basada en el valor del dado.
     */
    public String construirRuta() {
        return String.format("Dados/Dado%d.png", dado.getValor());
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
                System.err.println("Error: No se encontró el dado en " + url);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ImageView getImagenDado() {
        return imagenDado;
    }
}
