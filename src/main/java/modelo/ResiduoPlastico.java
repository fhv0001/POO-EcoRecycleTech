package modelo;

public class ResiduoPlastico extends Residuo {
    public ResiduoPlastico(String id, double peso) {
        super(id, peso, false);
    }

    @Override
    public String getTipo() { return "Plástico PET"; }

    @Override
    public boolean esReciclable() { return !esToxico; }
}