package modelo;

public class ResiduoMetal extends Residuo {
    public ResiduoMetal(String id, double peso) {
        super(id, peso, false);
    }

    @Override
    public String getTipo() { return "Metal/Aluminio"; }

    @Override
    public boolean esReciclable() { return true; }
}
