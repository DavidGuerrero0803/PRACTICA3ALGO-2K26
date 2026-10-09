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
    private int turnoActual;

    /**
     * Constructor del controlador.
     * Configura las estaciones e inicializa la cantidad inicial de clientes.
     * @param estaciones en la línea.
     * @param clientesIniciales Clientes iniciales por estación intermedia (3 por defecto).
     */
    public ControladorJuego(int estaciones, int clientesIniciales) {
        this.estaciones = estaciones;
        this.personas = new ArrayList<>();
        this.historial = new Historial(estaciones);
        this.turnoActual = 0;

        inicializarEstaciones(clientesIniciales);
    }

    /**
     * Instancia las personas y asigna clientes iniciales a las estaciones intermedias.
     */
    private void inicializarEstaciones(int clientesIniciales) {
        for (int i = 0; i < estaciones; i++) {
            personas.add(new Persona(i));
        }

        // Asigna los clientes iniciales (grises) a las estaciones intermedias.
        for (int i = 1; i < estaciones - 1; i++) {
            for (int j = 0; j < clientesIniciales; j++) {
                personas.get(i).getCola().insertarDato(new Cliente(0, true));
            }
        }
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
                                // Registro de la métrica Time in System al llegar a la última estación.
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

    }
}
