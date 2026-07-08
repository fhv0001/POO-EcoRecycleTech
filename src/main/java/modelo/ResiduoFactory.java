package modelo;

import java.util.Random;

public class ResiduoFactory {
    private static final Random random = new Random();
    private static int contadorId = 1;

    public static Residuo generarResiduoAleatorio() {
        String id = "RES-" + (contadorId++);
        double peso = 0.2 + (random.nextDouble() * 4.8); // Pesos aleatorios entre 0.2kg y 5kg
        int tipo = random.nextInt(4); // 0 a 3 incluyendo el metal extensible

        switch (tipo) {
            case 0: return new ResiduoPlastico(id, peso);
            case 1: return new ResiduoVidrio(id, peso);
            case 2: return new ResiduoPapel(id, peso);
            case 3: return new ResiduoMetal(id, peso); // Extensibilidad demostrada
            default: return new ResiduoPlastico(id, peso);
        }
    }
}
