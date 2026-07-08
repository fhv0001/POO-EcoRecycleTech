package modelo;

public class Contenedor {
    private String nombre;
    private double capacidadMaxima;
    private double llenadoActual;

    public Contenedor(String nombre, double capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.llenadoActual = 0.0;
    }

    public String getNombre() { return nombre; }
    public double getCapacidadMaxima() { return capacidadMaxima; }
    public double getLlenadoActual() { return llenadoActual; }

    public void setLlenadoActual(double llenadoActual) {
        this.llenadoActual = Math.min(llenadoActual, capacidadMaxima);
    }

    public boolean añadirResiduo(double peso) {
        if (llenadoActual + peso <= capacidadMaxima) {
            llenadoActual += peso;
            return true;
        }
        return false;
    }

    public void vaciar() {
        this.llenadoActual = 0.0;
    }

    public double getPorcentajeLlenado() {
        return (llenadoActual / capacidadMaxima) * 100;
    }
}
