package modelo;

public class ResiduoVidrio extends Residuo {
    public ResiduoVidrio(String id, double peso) {
        super(id, peso, false);
    }

    @Override
    public String getTipo() { return "Botella Vidrio"; }

    @Override
    public boolean esReciclable() { return peso > 0.1; } // Filtro polimórfico de ejemplo
}
