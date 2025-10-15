package clinica_vet.views;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField userTF;
    private JPasswordField passwordPF;
    private JButton btnLogin;
    private JButton btnCreateUser;

    public LoginView() {
        setTitle("Clínica Vet - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        add(mainPanel);

        // Título
        JLabel title = new JLabel("Bienvenido a Clínica Vet", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(25, 25, 112));
        mainPanel.add(title, BorderLayout.NORTH);

        // Panel central para los campos
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(userLabel, gbc);

        userTF = new JTextField();
        userTF.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(userTF, gbc);

        // Contraseña
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(passLabel, gbc);

        passwordPF = new JPasswordField();
        passwordPF.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(passwordPF, gbc);

        // Botón login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(60, 179, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        centerPanel.add(btnLogin, gbc);

        // Botón crear usuario
        btnCreateUser = new JButton("Crear Usuario");
        btnCreateUser.setBackground(new Color(70, 130, 180));
        btnCreateUser.setForeground(Color.WHITE);
        btnCreateUser.setFont(new Font("Arial", Font.BOLD, 14));
        btnCreateUser.setFocusPainted(false);
        gbc.gridx = 2;
        gbc.gridy = 2;
        centerPanel.add(btnCreateUser, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    public JTextField getUserTF() { return userTF; }
    public JPasswordField getPasswordPF() { return passwordPF; }
    public JButton getBtnLogin() { return btnLogin; }
}
