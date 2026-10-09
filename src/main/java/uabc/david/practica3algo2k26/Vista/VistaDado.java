package uabc.david.practica3algo2k26.Vista;

import javafx.scene.image.ImageView;
import uabc.david.practica3algo2k26.Modelo.Dado;

/**
  * Representa visualmente la cara actual de un dado en la GUI.
 */
public class VistaDado {
    private Dado dado;

    public VistaDado(Dado dado) {
        this.dado = dado;
    }

    /**
     * Genera la vista del dado.
     * @return el ImageView con la cara actual del dado (null si no se encuentra la imagen).
     */
    public ImageView mostrarDado() {
        return CargadorImagenes.crearImageView(construirRuta(), 50);
    }

    /**
     * Construye la ruta basada en el valor del dado.
     */
    public String construirRuta() {
        return String.format("/Dados/Dado%d.png", dado.getValor());
    }
}