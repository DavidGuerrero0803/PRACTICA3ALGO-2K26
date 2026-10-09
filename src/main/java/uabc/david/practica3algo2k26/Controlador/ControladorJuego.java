package uabc.david.practica3algo2k26.Controlador;

import uabc.david.practica3algo2k26.Modelo.Cliente;
import uabc.david.practica3algo2k26.Modelo.Dado;
import uabc.david.practica3algo2k26.Modelo.Historial;
import uabc.david.practica3algo2k26.Modelo.Persona;

import java.util.ArrayList;

/**
 * Esta clase contiene la lógica del juego:
 * Administra las estaciones (Personas), el control de turnos, la reasignación de dados,
 * y el registro de métricas en el Historial.
 */
public class ControladorJuego {
    private ArrayList<Persona> personas;
    private Historial historial;
    private int estaciones;
    private int clientesIniciales;
    private int turnosMax;
    private int turnoActual;
    private boolean iniciado;
    private boolean dadosLanzados;

    /**
     * Constructor del controlador.
     * @param estaciones en la línea.
     * @param clientesIniciales por estación intermedia.
     * @param turnosMax los turnos que dura el juego.
     */
    public ControladorJuego(int estaciones, int clientesIniciales, int turnosMax) {
        this.estaciones = estaciones;
        this.clientesIniciales = clientesIniciales;
        this.turnosMax = turnosMax;
        this.personas = new ArrayList<>();
        this.historial = new Historial(estaciones);
        this.turnoActual = 0;
        this.iniciado = false;
        this.dadosLanzados = false;

        crearPersonas();
    }

    /**
     * Instancia las personas de la línea.
     */
    private void crearPersonas() {
        for (int i = 0; i < estaciones; i++) {
            personas.add(new Persona(i));
        }
    }

    /**
     * Empieza el juego, asignando los clientes iniciales (grises).
     */
    public void iniciar() {
        if (iniciado) {
            return;
        }
        for (int i = 1; i < estaciones - 1; i++) {
            for (int j = 0; j < clientesIniciales; j++) {
                personas.get(i).getCola().insertarDato(new Cliente(0, true));
            }
        }
        iniciado = true;
    }

    /**
     * Reinicia el juego completo.
     */
    public void reiniciar() {
        personas.clear();
        historial = new Historial(estaciones);
        turnoActual = 0;
        iniciado = false;
        dadosLanzados = false;
        crearPersonas();
    }

    /**
     * Tira los dados de todas las estaciones antes de realizar el movimiento.
     */
    public void lanzarDados() {
        for (Persona persona : personas) {
            for (Dado dado : persona.getDados()) {
                dado.lanzar();
            }
        }
        dadosLanzados = true;
    }

    /**
     * Permite mover manualmente un dado de una estación a otra.
     * @param estacionOrigen de la persona que entrega el dado.
     * @param estacionDestino de la persona que recibe el dado.
     * @return true si se pudo realizar el movimiento, false si la estación origen no tenía dados.
     */
    public boolean transferirDado(int estacionOrigen, int estacionDestino) {
        if (estacionOrigen < 0 || estacionOrigen >= estaciones || estacionDestino < 0 || estacionDestino >= estaciones) {
            return false;
        }

        Persona origen = personas.get(estacionOrigen);
        Persona destino = personas.get(estacionDestino);

        // Solamente se puede mover si la estación seleccionada posee al menos 1 dado.
        if (origen.getCantidadDados() > 0) {
            Dado dadoAMover = origen.removerDado();
            if (dadoAMover != null) {
                destino.agregarDado(dadoAMover);
                return true;
            }
        }
        return false;
    }

    /**
     * Ejecuta el movimiento de clientes entre estaciones.
     */
    public void mover() {
        turnoActual++;
        int[] clientePorProcesar = new int[estaciones];
        int[] clientesMovidos = new int[estaciones];

        // Guarda la cantidad de clientes disponibles antes del movimiento.
        for (int i = 0; i < estaciones; i++) {
            clientePorProcesar[i] = personas.get(i).getCola().getCantidadElementos();
        }

        // Recorrido en sentido inverso (penúltima estación hacia la primera).
        for (int i = estaciones - 2; i >= 0; i--) {
            Persona actual = personas.get(i);
            Persona siguiente = personas.get(i + 1);

            // Calcula la suma de todos los dados lanzados por la estación.
            int capacidadProcesamiento = 0;
            for (Dado dado : actual.getDados()) {
                capacidadProcesamiento += dado.getValor();
            }

            int movidos = 0;

            // La estación 0 representa la reserva infinita (puede mover exactamente la suma de sus dados).
            if (actual.esPrimera()) {
                for (int k = 0; k < capacidadProcesamiento; k++) {
                    siguiente.getCola().insertarDato(new Cliente(turnoActual, false));
                    movidos++;
                }
            } else {
                // Las estaciones intermedias mueven hasta el máximo disponible en su cola antes del turno.
                int limiteDisponible = clientePorProcesar[i];
                for (int k = 0; k < capacidadProcesamiento; k++) {
                    if (movidos < limiteDisponible && !actual.getCola().colaVacia()) {
                        Cliente clienteExtraido = actual.getCola().eliminarDato();

                        if (clienteExtraido != null) {
                            boolean insertado = siguiente.getCola().insertarDato(clienteExtraido);
                            if (insertado) {
                                movidos++;
                                // Registra la métrica Time in System al llegar a la última estación.
                                if (siguiente.esUltima()) {
                                    int tiempoEnSistema = turnoActual - clienteExtraido.getTurnoDeEntrada();
                                    historial.registrarTimeInSystem(tiempoEnSistema);
                                }
                            } else {
                                // Regresa el cliente a la cola si la siguiente estación está llena (desbordamiento).
                                actual.getCola().insertarDato(clienteExtraido);
                                break;
                            }
                        }
                    }
                }
            }
            clientesMovidos[i] = movidos;
        }

        // Ajustes para la estación 0 en el historial.
        int capacidadReserva = 0;
        for (Dado dado : personas.get(0).getDados()) {
            capacidadReserva += dado.getValor();
        }
        clientePorProcesar[0] = capacidadReserva;
        clientesMovidos[0] = capacidadReserva;

        // Por último, se guardan las métricas en cada turno.
        guardarMetricas(clientePorProcesar, clientesMovidos);
        dadosLanzados = false;
    }

    /**
     * Guarda Activity, Number in System y Throughput en el Historial.
     */
    private void guardarMetricas(int[] clientePorProcesar, int[] clientesMovidos) {
        int numberInSystem = 0;
        for (int i = 1; i < estaciones - 1; i++) {
            numberInSystem += personas.get(i).getCola().getCantidadElementos();
        }

        historial.registrarNumberInSystem(numberInSystem);
        historial.registrarThroughput(personas.get(estaciones - 1).getCola().getCantidadElementos());
        historial.registrarActividad(clientePorProcesar, clientesMovidos);
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    /**
     * @return true si ya se presionó Start.
     */
    public boolean isIniciado() {
        return iniciado;
    }

    /**
     * @return true si ya se lanzaron los dados del turno actual.
     */
    public boolean isDadosLanzados() {
        return dadosLanzados;
    }

    /**
     * @return true si ya se jugaron todos los turnos.
     */
    public boolean haTerminado() {
        return turnoActual >= turnosMax;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public Historial getHistorial() {
        return historial;
    }

}
