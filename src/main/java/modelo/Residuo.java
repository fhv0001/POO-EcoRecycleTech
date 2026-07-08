package modelo;

public abstract class Residuo implements IResiduo {
    protected String id;
    protected double peso;
    protected boolean esToxico;

    public Residuo(String id, double peso, boolean esToxico) {
        this.id = id;
        this.peso = peso;
        this.esToxico = esToxico;
    }

    @Override
    public String getID() { return id; }

    @Override
    public double getPeso() { return peso; }

    public abstract boolean esReciclable();
}