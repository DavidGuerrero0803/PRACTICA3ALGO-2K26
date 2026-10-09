package uabc.david.practica3algo2k26.Modelo;

import java.util.ArrayList;

/**
 * Representa una estación de trabajo (jugador) dentro de la simulación.
 * Contiene una ColaCircular para almacenar sus clientes
 * y soporta una cantidad variable de dados.
 */
public class Persona {
    private ColaCircular<Cliente> clientes;
    private ArrayList<Dado> dados;
    private int idEstacion;

    /**
     * Constructor de la estación.
     * @param idEstacion Identificador de la persona.
     */
    public Persona(int idEstacion) {
        this.idEstacion = idEstacion;
        this.clientes = new ColaCircular<>(200);
        this.dados = new ArrayList<>();
        this.dados.add(new Dado());
    }

    /**
     * Agrega un dado adicional a esta persona.
     * @param dado a agregar a esta estación.
     */
    public void agregarDado(Dado dado) {
        this.dados.add(dado);
    }

    /**
     * Remueve un dado de esta persona para ser transferido a otra estación.
     * @return Dado removido (o null si la persona no tenía dados).
     */
    public Dado removerDado() {
        if (!dados.isEmpty()) {
            return dados.remove(dados.size() - 1);
        }
        return null;
    }

    /**
     * Determina si esta estación es la de entrada/reserva con clientes ilimitados.
     */
    public boolean esPrimera() {
        return idEstacion == 0;
    }

    /**
     * Determina si esta estación es la de salida/cliente procesado.
     */
    public boolean esUltima() {
        return idEstacion == 9;
    }

    /**
     * Devuelve la cantidad de dados que la persona posee en el turno actual.
     */
    public int getCantidadDados() {
        return dados.size();
    }

    public int getIdEstacion() {
        return idEstacion;
    }

    public ColaCircular<Cliente> getCola() {
        return clientes;
    }

    public void setCola(ColaCircular<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Dado> getDados() {
        return dados;
    }

    public void setDados(ArrayList<Dado> dados) {
        this.dados = dados;
    }

}
