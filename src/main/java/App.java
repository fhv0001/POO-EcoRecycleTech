import controlador.ControladorPlanta;
import vista.VistaPlanta;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            VistaPlanta vista = new VistaPlanta();
            new ControladorPlanta(vista);
            vista.setVisible(true);
        });
    }
}