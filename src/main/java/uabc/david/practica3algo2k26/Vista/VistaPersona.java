package uabc.david.practica3algo2k26.Vista;

import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import uabc.david.practica3algo2k26.Modelo.Dado;
import uabc.david.practica3algo2k26.Modelo.Persona;

/**
 * Representa visualmente a 1 persona de la línea la imagen
 * de la persona, sus dados y la cola de clientes.
 */
public class VistaPersona {
    private Persona persona;
    private ImageView imagenPersona;
    private FlowPane panelDados;
    private FlowPane panelClientes;

    /**
     * Crea la vista de una persona con su estado actual.
     * @param persona la persona a representar.
     */
    public VistaPersona(Persona persona) {
        this.persona = persona;
        construirImagen();
        construirDados();
        construirClientes();
    }

    /**
     * Carga la imagen de la persona según su posición en la línea.
     */
    private void construirImagen() {
        String ruta = String.format("/Personas/persona%d.png", persona.getIdEstacion() + 1);
        imagenPersona = CargadorImagenes.crearImageView(ruta, 100);
    }

    /**
     * Construye el panel con los dados de la persona.
     */
    private void construirDados() {
        panelDados = new FlowPane();
        panelDados.setPrefWrapLength(100);
        for (Dado dado : persona.getDados()) {
            ImageView imgDado = new VistaDado(dado).mostrarDado();
            if (imgDado != null) {
                panelDados.getChildren().add(imgDado);
            }
        }
    }

    /**
     * Construye el panel con los clientes de la cola de la persona.
     */
    private void construirClientes() {
        panelClientes = new FlowPane();
        panelClientes.setHgap(2);
        panelClientes.setVgap(2);
        panelClientes.setPrefWrapLength(100);

        if (!persona.esPrimera()) {
            String contenido = persona.getCola().mostrarDatos();
            for (int i = 0; i < contenido.length(); i++) {
                ImageView imgCliente = new VistaCliente(contenido.charAt(i) == 'I').mostrarCliente();
                if (imgCliente != null) {
                    panelClientes.getChildren().add(imgCliente);
                }
            }
        }
    }

    public ImageView getImagenPersona() {
        return imagenPersona;
    }

    public FlowPane getPanelDados() {
        return panelDados;
    }

    public FlowPane getPanelClientes() {
        return panelClientes;
    }

    public Persona getPersona() {
        return persona;
    }
}