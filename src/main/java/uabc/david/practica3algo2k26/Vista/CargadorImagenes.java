package uabc.david.practica3algo2k26.Vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Se encarga de cargar las imágenes del juego desde los recursos.
 */
public class CargadorImagenes {
    private static HashMap<String, Image> imagenes;
    private static HashSet<String> faltantes;

    public CargadorImagenes() {
        imagenes = new HashMap<>();
        faltantes = new HashSet<>();
    }

    /**
     * Crea un ImageView con la imagen indicada.
     * @param ruta del recurso.
     * @param tamano el ancho y alto deseado.
     * @return el ImageView configurado (null si la imagen no se encuentra).
     */
    public static ImageView crearImageView(String ruta, int tamano) {
        Image imagen = imagenes.get(ruta);
        if (imagen == null) {
            URL recurso = CargadorImagenes.class.getResource(ruta);
            if (recurso == null) {
                if (faltantes.add(ruta)) {
                    System.err.println("Error: No se encontró la imagen en " + ruta);
                }
                return null;
            }
            imagen = new Image(recurso.toExternalForm());
            imagenes.put(ruta, imagen);
        }
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(tamano);
        imageView.setFitHeight(tamano);
        imageView.setPreserveRatio(true);

        return imageView;
    }
}
