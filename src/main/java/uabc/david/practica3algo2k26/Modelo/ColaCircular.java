package uabc.david.practica3algo2k26.Modelo;

/**
 * Esta clase representa una estructura de datos de tipo Cola Circular.
 * Reutiliza las posiciones del arreglo para optimizar la memoria y evita desbordamientos.
 */
public class ColaCircular<T> {
    private T[] colaCircular;
    private int inicio;
    private int fin;

    /**
     * Constructor por defecto con capacidad inicial de 10 elementos.
     */
    public ColaCircular() {
        this.colaCircular = (T[]) (new Object[10]);
        this.inicio = -1;
        this.fin = -1;
    }

    /**
     * Constructor parametrizado.
     * @param capacidad del arreglo para la cola circular.
     */
    public ColaCircular(int capacidad) {
        this.colaCircular = (T[]) (new Object[capacidad]);
        this.inicio = -1;
        this.fin = -1;
    }

    /**
     * Inserta un dato al final de la cola circular.
     * @param dato a insertar.
     * @return true si se insertó con éxito (false si la cola está llena).
     */
    public boolean insertarDato(T dato) {
        if (colaLlena()) {
            return false;
        }
        if (fin == colaCircular.length - 1) {
            fin = 0;
        } else {
            fin = fin + 1;
        }

        colaCircular[fin] = dato;

        if (inicio == -1) {
            inicio = 0;
        }
        return true;
    }

    /**
     * Elimina y devuelve el primer elemento de la cola circular.
     * @return El elemento eliminado (null si la cola está vacía).
     */
    public T eliminarDato() {
        if (colaVacia()) {
            return null;
        }

        T dato = colaCircular[inicio];
        colaCircular[inicio] = null;

        if (inicio == fin) {
            inicio = -1;
            fin = -1;
        } else if (inicio == colaCircular.length - 1) {
            inicio = 0;
        } else {
            inicio = inicio + 1;
        }

        return dato;
    }

    /**
     * Verifica si la cola no contiene elementos.
     */
    public boolean colaVacia() {
        return inicio == -1;
    }

    /**
     * Verifica si la cola alcanzó su capacidad máxima.
     */
    public boolean colaLlena() {
        return (fin == colaCircular.length - 1 && inicio == 0) || (fin + 1) == inicio;
    }

    /**
     * Devuelve la cantidad de elementos válidos actualmente en la cola.
     */
    public int getCantidadElementos() {
        if (colaVacia()) {
            return 0;
        }
        return (fin - inicio + colaCircular.length) % colaCircular.length + 1;
    }

    /**
     * Genera una cadena de texto con el contenido actual de la cola.
     */
    public String mostrarDatos() {
        if (colaVacia()) {
            return "";
        }
        StringBuilder datos = new StringBuilder();
        int n = getCantidadElementos();
        for (int i = 0; i < n; i++) {
            datos.append(colaCircular[(inicio + i) % colaCircular.length]);
        }
        return datos.toString();
    }
}
