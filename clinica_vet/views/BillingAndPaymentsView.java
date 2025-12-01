package clinica_vet.views;

import java.awt.*;
import javax.swing.*;

public class BillingAndPaymentsView extends JPanel {

    public BillingAndPaymentsView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("💳 Módulo de Facturación y Pagos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 40, 40));
        
        // Contenedor principal para la lógica de pagos
        JPanel mainContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
        mainContent.setBackground(Color.WHITE);
        mainContent.add(titleLabel);
        
        JTextArea infoArea = new JTextArea("Aquí se gestionará la creación de facturas y el registro de pagos.");
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        infoArea.setForeground(new Color(90, 90, 90));
        mainContent.add(infoArea);

        add(mainContent, BorderLayout.CENTER);
    }
}