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

    /**
     * Registra el número total de clientes acumulados en la última estación.
     * @param total de unidades procesadas del sistema.
     */
    public void registrarThroughput(int total) {
        throughput.add(total);
    }

    /**
     * Registra los clientes en proceso dentro de las estaciones intermedias.
     * @param total de clientes en espera en la línea.
     */
    public void registrarNumberInSystem(int total) {
        numberInSystem.add(total);
    }

    /**
     * Registra "el tiempo" que tardó un cliente en cruzar la línea completa.
     * @param tiempo cantidad de rondas desde su ingreso hasta su salida.
     */
    public void registrarTimeInSystem(int tiempo) {
        timeInSystem.add(tiempo);
    }

    public ArrayList<Integer> getThroughput() {
        return throughput;
    }

    public ArrayList<Integer> getNumberInSystem() {
        return numberInSystem;
    }

    public ArrayList<Integer> getTimeInSystem() {
        return timeInSystem;
    }

    public int getTirada(int ronda, int persona) {
        return tiradas.get(ronda)[persona];
    }

    public int getMovimiento(int ronda, int persona) {
        return movimientos.get(ronda)[persona];
    }

    public int getRonda() {
        return tiradas.size();
    }

    public int getEstaciones() {
        return estaciones;
    }

}
