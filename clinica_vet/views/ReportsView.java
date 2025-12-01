package clinica_vet.views;

import java.awt.*;
import javax.swing.*;

public class ReportsView extends JPanel {

    public ReportsView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("📊 Módulo de Reportes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 40, 40));
        
        // Contenedor principal para el contenido de los reportes
        JPanel mainContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
        mainContent.setBackground(Color.WHITE);
        mainContent.add(titleLabel);
        
        JTextArea infoArea = new JTextArea("Aquí se mostrarán diversos reportes (Citas, Usuarios, Mascotas).");
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        infoArea.setForeground(new Color(90, 90, 90));
        mainContent.add(infoArea);

        add(mainContent, BorderLayout.CENTER);
    }
}