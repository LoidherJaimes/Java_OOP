package clinica_vet.views;

import javax.swing.*;
import java.awt.*;


// ⭐ Extiende JPanel para ser contenido central
public class LogoutView extends JPanel { 

    private JButton btnYes;
    private JButton btnNo;

    public LogoutView() {
        setLayout(new GridBagLayout());
        
        // Colores base
        Color primaryColor = new Color(70, 130, 180); 
        Font boldFont = new Font("Arial", Font.BOLD, 18);

        // Mensaje
        JLabel message = new JLabel("¿Está seguro de que desea cerrar la sesión?");
        message.setFont(boldFont);
        message.setForeground(primaryColor);

        // Botones
        btnYes = new JButton("Sí, Cerrar Sesión");
        btnNo = new JButton("No, Quedarme");
        
        // Estilo simple
        btnYes.setBackground(new Color(220, 20, 60)); // Rojo para cerrar
        btnYes.setForeground(Color.WHITE);
        btnNo.setBackground(new Color(60, 179, 113)); // Verde para cancelar
        btnNo.setForeground(Color.WHITE);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(btnYes);
        buttonPanel.add(btnNo);

        // Añadir componentes al panel principal con GridBagLayout para centrar
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 20, 10);
        add(message, gbc);

        gbc.gridy = 1;
        add(buttonPanel, gbc);
    }
    
    // Getters para el controlador
    public JButton getBtnYes() { return btnYes; }
    public JButton getBtnNo() { return btnNo; }
}