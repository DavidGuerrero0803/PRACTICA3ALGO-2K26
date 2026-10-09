package uabc.david.practica3algo2k26.Modelo;

import java.util.Random;

/**
 * Representa un dado individual en la simulación.
 * Genera valores aleatorios entre 1 y 6.
 */
public class Dado {
    private int valor;

    /**
     * Constructor por defecto.
     * Inicializa el dado con un valor base de 1.
     */
    public Dado() {
        this.valor = 1;
    }

    /**
     * Lanza el dado y genera un número entero aleatorio entre 1 y 6.
     * @return El nuevo valor obtenido tras el lanzamiento.
     */
    public int lanzar() {
        Random rnd = new Random();
        valor = rnd.nextInt(6) + 1;
        return valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
