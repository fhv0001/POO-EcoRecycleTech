package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaPlanta extends JFrame {
    private JButton btnSimular = new JButton("Simular Entrada de Residuo");
    private JButton btnProcesar = new JButton("Procesar Residuo");
    private JButton btnVaciar = new JButton("Vaciar Contenedores");

    private JLabel lblCinta = new JLabel("Cinta Transportadora: Vacía");
    private JTextArea txtContenedores = new JTextArea(8, 30);

    public VistaPlanta() {
        setTitle("EcoRecycle Tech SA - Panel de Control Visual");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Superior: Estado de la Cinta
        JPanel pnlNorte = new JPanel();
        pnlNorte.setBorder(BorderFactory.createTitledBorder("Cinta Transportadora"));
        lblCinta.setFont(new Font("Arial", Font.BOLD, 14));
        pnlNorte.add(lblCinta);
        add(pnlNorte, BorderLayout.NORTH);

        // Panel Central: Monitoreo de Contenedores
        txtContenedores.setEditable(false);
        txtContenedores.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtContenedores);
        scroll.setBorder(BorderFactory.createTitledBorder("Nivel de los Contenedores"));
        add(scroll, BorderLayout.CENTER);

        // Panel Inferior: Botones de Acción
        JPanel pnlSur = new JPanel(new FlowLayout());
        pnlSur.add(btnSimular);
        pnlSur.add(btnProcesar);
        pnlSur.add(btnVaciar);
        add(pnlSur, BorderLayout.SOUTH);
    }

    public void actualizarCinta(String texto) {
        lblCinta.setText("Cinta Transportadora: " + texto);
    }

    public void actualizarContenedores(String texto) {
        txtContenedores.setText(texto);
    }

    public void mostrarAlerta(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Alerta Planta", JOptionPane.WARNING_MESSAGE);
    }

    public void addSimularListener(ActionListener listen) { btnSimular.addActionListener(listen); }
    public void addProcesarListener(ActionListener listen) { btnProcesar.addActionListener(listen); }
    public void addVaciarListener(ActionListener listen) { btnVaciar.addActionListener(listen); }
}