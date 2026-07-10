package controlador;

import modelo.*;
import vista.VistaPlanta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorPlanta {
    private List<Contenedor> contenedores;
    private Residuo residuoEnCinta = null;
    private VistaPlanta vista;

    private final String LOG_PATH = "recycle.log";
    private final String JSON_PATH = "estado_planta.json";

    public ControladorPlanta(VistaPlanta vista) {
        this.vista = vista;
        this.contenedores = new ArrayList<>();

        // Inicializar contenedores base + contenedor extendido (Metal)
        contenedores.add(new Contenedor("Plástico", 50.0));
        contenedores.add(new Contenedor("Vidrio", 100.0));
        contenedores.add(new Contenedor("Papel", 80.0));
        contenedores.add(new Contenedor("Metal", 60.0)); // Extensible

        cargarEstadoPlanta();
        actualizarVistaPlanta();

        // Registrar Listeners
        this.vista.addSimularListener(new SimularClick());
        this.vista.addProcesarListener(new ProcesarClick());
        this.vista.addVaciarListener(new VaciarClick());

        // Guardar estado automáticamente al cerrar la ventana
        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                guardarEstadoPlanta();
            }
        });
    }

    private void actualizarVistaPlanta() {
        if (residuoEnCinta != null) {
            vista.actualizarCinta(residuoEnCinta.getID() + " - " + residuoEnCinta.getTipo() + " (" + String.format("%.2f", residuoEnCinta.getPeso()) + " kg)");
        } else {
            vista.actualizarCinta("Vacía");
        }

        StringBuilder sb = new StringBuilder();
        for (Contenedor c : contenedores) {
            sb.append(String.format("%-10s: [%6.2f / %6.2f kg] (%5.1f%% llenado)\n",
                    c.getNombre(), c.getLlenadoActual(), c.getCapacidadMaxima(), c.getPorcentajeLlenado()));
        }
        vista.actualizarContenedores(sb.toString());
    }

    class SimularClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (residuoEnCinta != null) {
                vista.mostrarAlerta("Ya hay un residuo en la cinta esperando ser procesado.");
                return;
            }
            residuoEnCinta = ResiduoFactory.generarResiduoAleatorio();
            actualizarVistaPlanta();
        }
    }

    class ProcesarClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (residuoEnCinta == null) {
                vista.mostrarAlerta("No hay ningún residuo en la cinta para clasificar.");
                return;
            }

            if (!residuoEnCinta.esReciclable()) {
                vista.mostrarAlerta("Residuo descartado automáticamente (No cumple criterios polimórficos de reciclaje).");
                residuoEnCinta = null;
                actualizarVistaPlanta();
                return;
            }

            boolean clasificado = false;
            for (Contenedor c : contenedores) {
                if (residuoEnCinta.getTipo().contains(c.getNombre()) || c.getNombre().contains("Plástico") && residuoEnCinta.getTipo().contains("Plástico")) {
                    if (c.añadirResiduo(residuoEnCinta.getPeso())) {
                        registrarEnLog(residuoEnCinta);
                        residuoEnCinta = null;
                        clasificado = true;
                        break;
                    } else {
                        vista.mostrarAlerta("¡ALERTA! El depósito de " + c.getNombre() + " está lleno. ¡Vacíalo!");
                        return;
                    }
                }
            }

            if (!clasificado) {
                vista.mostrarAlerta("No se encontró un contenedor compatible para este residuo.");
            }
            actualizarVistaPlanta();
        }
    }

    class VaciarClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Contenedor c : contenedores) {
                c.vaciar();
            }
            actualizarVistaPlanta();
        }
    }

    private void registrarEnLog(Residuo r) {
        try (FileWriter fw = new FileWriter(LOG_PATH, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(LocalDateTime.now() + " | ID: " + r.getID() + " | Tipo: " + r.getTipo() + " | Peso: " + String.format("%.2f", r.getPeso()) + " kg\n");
        } catch (IOException e) {
            System.err.println("Error escribiendo en el log.");
        }
    }

    private void guardarEstadoPlanta() {
        try (PrintWriter out = new PrintWriter(new FileWriter(JSON_PATH))) {
            out.println("{");
            out.println("  \"contenedores\": [");
            for (int i = 0; i < contenedores.size(); i++) {
                Contenedor c = contenedores.get(i);
                out.printf("    { \"nombre\": \"%s\", \"llenadoActual\": %.2f }%s\n",
                        c.getNombre(), c.getLlenadoActual(), (i == contenedores.size() - 1) ? "" : ",");
            }
            out.println("  ]");
            out.println("}");
        } catch (IOException e) {
            System.err.println("Error al guardar el estado JSON.");
        }
    }

    private void cargarEstadoPlanta() {
        File file = new File(JSON_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                for (Contenedor c : contenedores) {
                    if (linea.contains("\"nombre\": \"" + c.getNombre() + "\"")) {
                        // Extracción manual simple de datos JSON nativa sin librerías externas
                        String[] partes = linea.split("\"llenadoActual\":");
                        if (partes.length > 1) {
                            String valorStr = partes[1].replace("}", "").replace(",", "").trim();
                            double valor = Double.parseDouble(valorStr);
                            c.setLlenadoActual(valor);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo parsear el archivo JSON, iniciando con valores limpios.");
        }
    }
}