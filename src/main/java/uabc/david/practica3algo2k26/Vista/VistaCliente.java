package uabc.david.practica3algo2k26.Vista;

import javafx.scene.image.ImageView;

import uabc.david.practica3algo2k26.Modelo.Cliente;

/**
 * Renderiza visualmente a un cliente individual.
 * Selecciona el cliente según su tipo (inicial es gris, nuevo es azul).
 */
public class VistaCliente {
    private boolean esInicial;

    public VistaCliente(Cliente cliente) {
        this(cliente.esInicial());
    }

    /**
     * Crea la vista de un cliente a partir de su tipo.
     * @param esInicial true si es un cliente inicial (gris), false si es nuevo (azul).
     */
    public VistaCliente(boolean esInicial) {
        this.esInicial = esInicial;
    }

    /**
     * Carga y devuelve el ImageView del cliente configurado.
     */
    public ImageView mostrarCliente() {
        String ruta = construirRuta();
        return CargadorImagenes.crearImageView(ruta, 18);
    }

    /**
     * Determina la ruta del recurso según si son clientes iniciales o no.
     */
    public String construirRuta() {
        if (esInicial) {
            return "/Clientes/ClienteGris.png";
        } else {
            return "/Clientes/ClienteAzul.png";
        }
    }
}