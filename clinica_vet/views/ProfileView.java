package clinica_vet.views;

import clinica_vet.model.entities.User;
import javax.swing.*;
import java.awt.*;

// ⭐ CAMBIO CLAVE: Extiende JPanel para ser contenido central
public class ProfileView extends JPanel { 

    private JLabel lblTitle;
    private JLabel lblUsernameValue;
    private JLabel lblRoleValue;

    public ProfileView(User user) {
        // Ya no se requiere JDialog setup (setTitle, setModal, setLocationRelativeTo, etc.)
        setLayout(new BorderLayout(15, 15)); 
        
        // Colores y fuentes base
        Color primaryColor = new Color(70, 130, 180); 
        Color backgroundColor = new Color(245, 245, 245); 
        Color textColor = new Color(60, 60, 60); 
        Font titleFont = new Font("Arial", Font.BOLD, 22);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font valueFont = new Font("Arial", Font.BOLD, 16);

        // --- Panel Superior (Título) ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(0, 60)); 
        
        lblTitle = new JLabel("Detalles de Mi Perfil");
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // --- Panel Central (Información del usuario) ---
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout()); 
        infoPanel.setBackground(backgroundColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.WEST; 

        // Nombre de Usuario
        JLabel lblUsername = new JLabel("Nombre de Usuario:");
        lblUsername.setFont(labelFont);
        lblUsername.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(lblUsername, gbc);

        lblUsernameValue = new JLabel(user.getUsername());
        lblUsernameValue.setFont(valueFont);
        lblUsernameValue.setForeground(primaryColor); 
        gbc.gridx = 1; gbc.gridy = 0;
        infoPanel.add(lblUsernameValue, gbc);

        // Rol del Usuario
        JLabel lblRole = new JLabel("Nivel de Acceso:");
        lblRole.setFont(labelFont);
        lblRole.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(lblRole, gbc);

        String roleName = user.getRol() != null ? user.getRol().getName() : "Sin Rol Asignado";
        lblRoleValue = new JLabel(roleName);
        lblRoleValue.setFont(valueFont);
        lblRoleValue.setForeground(primaryColor);
        gbc.gridx = 1; gbc.gridy = 1;
        infoPanel.add(lblRoleValue, gbc);
        
        add(infoPanel, BorderLayout.CENTER);
    }
}