package modelo;

public class ResiduoPapel extends Residuo {
    public ResiduoPapel(String id, double peso) {
        super(id, peso, false);
    }

    @Override
    public String getTipo() { return "Papel/Cartón"; }

    @Override
    public boolean esReciclable() { return peso < 5.0; } // No procesar bloques excesivamente densos
}
