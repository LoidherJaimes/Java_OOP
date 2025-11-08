package clinica_vet.views;

import javax.swing.*;
import java.awt.*;
    

public class LogoutView extends JDialog {

    private JButton btnYes;
    private JButton btnNo;
    private boolean isConfirmed = false; 

    public LogoutView(JFrame owner) {
        // Configuración de la ventana JDialog
        super(owner, "Confirmar Cierre de Sesión", true); 
        setSize(350, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner); 
        setLayout(new BorderLayout(15, 15));
        setResizable(false); 

        // Colores y fuentes base (iguales a tu MainWindowView)
        Color primaryColor = new Color(70, 130, 180); 
        Color backgroundColor = new Color(245, 245, 245); 
        Font titleFont = new Font("Arial", Font.BOLD, 18);
        Font labelFont = new Font("Arial", Font.PLAIN, 15);

        // --- Panel Superior (Título) ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(0, 50));
        
        JLabel lblTitle = new JLabel("¿Desea cerrar la sesión actual?");
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // --- Panel Central (Mensaje) ---
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setBackground(backgroundColor);
        JLabel lblMessage = new JLabel("Al confirmar volverá a la pantalla de Login.");
        lblMessage.setFont(labelFont);
        messagePanel.add(lblMessage);
        add(messagePanel, BorderLayout.CENTER);

        // --- Panel Inferior (Botones de Confirmación) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(backgroundColor);
        
        btnYes = new JButton("Sí, Cerrar Sesión");
        btnNo = new JButton("No, Quedarme");

        // Estilo para el botón de acción positiva (Sí)
        styleButton(btnYes, new Color(220, 20, 60)); // Rojo para Logout
        // Estilo para el botón de acción negativa (No)
        styleButton(btnNo, new Color(105, 105, 105)); // Gris para cancelar

        buttonPanel.add(btnYes);
        buttonPanel.add(btnNo);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // --- Listeners Internos ---
        btnYes.addActionListener(e -> {
            isConfirmed = true;
            dispose();
        });
        
        btnNo.addActionListener(e -> {
            isConfirmed = false;
            dispose();
        });
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE); 
        button.setFont(new Font("Arial", Font.BOLD, 13)); 
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
    
    public JButton getBtnYes() { return btnYes; }
    public JButton getBtnNo() { return btnNo; }
}