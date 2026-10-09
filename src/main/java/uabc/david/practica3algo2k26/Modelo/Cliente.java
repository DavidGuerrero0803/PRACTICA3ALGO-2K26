package uabc.david.practica3algo2k26.Modelo;

/**
 * Representa a un cliente/unidad de producción dentro del sistema.
 */
public class Cliente {
    private int turnoDeEntrada;
    private boolean esInicial;

    /**
     * Constructor por defecto.
     * Crea un cliente perteneciente al inventario inicial.
     */
    public Cliente() {
        this.turnoDeEntrada = 0;
        this.esInicial = true;
    }

    /**
     * Constructor parametrizado para clientes generados durante la simulación.
     * @param turnoDeEntrada en el que el cliente ingresa a la línea.
     * @param esInicial true para clientes iniciales (gris), false para clientes posteriores (azul).
     */
    public Cliente(int turnoDeEntrada, boolean esInicial) {
        this.turnoDeEntrada = turnoDeEntrada;
        this.esInicial = esInicial;
    }

    /**
     * Determina si el estado del sistema es el inicial.
     */
    public boolean esInicial() {
        return esInicial;
    }

    /**
     * Devuelve el número de turno en el que el cliente ingresa a la línea.
     */
    public int getTurnoDeEntrada() {
        return turnoDeEntrada;
    }

    /**
     * Devuelve un solo carácter para que la vista lo lea desde ColaCircular.
     * @return "I" si es un cliente inicial (gris), "N" si es nuevo (azul).
     */
    @Override
    public String toString() {
        return esInicial ? "I" : "N";
    }
}