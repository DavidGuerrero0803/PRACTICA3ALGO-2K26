package uabc.david.practica3algo2k26.Modelo;

import java.util.ArrayList;

/**
 * Registra y almacena las métricas de rendimiento ronda tras ronda.
 */
public class Historial {
    private ArrayList<Integer> throughput;
    private ArrayList<Integer> numberInSystem;
    private ArrayList<Integer> timeInSystem;
    private ArrayList<int[]> tiradas;
    private ArrayList<int[]> movimientos;
    private int estaciones;

    /**
     * Constructor de la clase Historial.
     * @param estaciones en la simulación.
     */
    public Historial(int estaciones) {
        this.estaciones = estaciones;
        this.tiradas = new ArrayList<>();
        this.movimientos = new ArrayList<>();
        this.throughput = new ArrayList<>();
        this.numberInSystem = new ArrayList<>();
        this.timeInSystem = new ArrayList<>();
    }

    /**
     * Registra los tiros de dados y clientes movidos por estación en un turno.
     * @param lanzamientos Arreglo con la capacidad/tiros antes del movimiento.
     * @param clientesDesplazados Arreglo con los clientes movidos.
     */
    public void registrarActividad(int[] lanzamientos, int[] clientesDesplazados) {
        tiradas.add(lanzamientos);
        movimientos.add(clientesDesplazados);
    }
}
