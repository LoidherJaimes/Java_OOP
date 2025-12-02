package clinica_vet.views;

import java.awt.*;
import javax.swing.*;

public class MainWindowView extends JFrame {
    private JPanel contentView;
    private JPanel topBar;
    private JPanel sideMenu;

    private JButton btnPerfil;
    private JButton btnLogout;
    private JButton btnUsers;
    private JButton btnAppointment;
    private JButton btnHistory;
    private JButton btnPayments;
    private JButton btnOwners;
    private JButton btnPets;
    private JButton btnAgenda;
    private JButton btnReports; 
    private JButton btnBillingAndPayments;

    public MainWindowView() {
        setTitle("Clínica Vet - Panel Principal");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        createTopBar();
        createSideMenu();
        createContentView();

        add(topBar, BorderLayout.NORTH);
        add(sideMenu, BorderLayout.WEST);
        add(contentView, BorderLayout.CENTER);
    }

    private void createTopBar() {
        topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(70, 130, 180));
        topBar.setPreferredSize(new Dimension(1000, 50));

        JLabel title = new JLabel("Clínica Veterinaria - Sistema de Gestión", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        btnPerfil = new JButton("Perfil");
        btnLogout = new JButton("Cerrar Sesión");

        styleTopButton(btnPerfil, new Color(255, 215, 0));
        styleTopButton(btnLogout, new Color(220, 20, 60));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        rightPanel.setBackground(new Color(70, 130, 180));
        rightPanel.add(btnPerfil);
        rightPanel.add(btnLogout);

        topBar.add(title, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);
    }

    private void createSideMenu() {
        sideMenu = new JPanel();
        // Usamos BoxLayout para mejor control
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(245, 245, 245));
        sideMenu.setPreferredSize(new Dimension(220, 0));
        
        // Agregar un poco de espacio en la parte superior
        sideMenu.add(Box.createRigidArea(new Dimension(0, 20)));

        btnUsers = new JButton("Gestión de Usuarios");
        btnAppointment = new JButton("Agendar Citas");
        btnHistory = new JButton("Historia Clínica");
        btnBillingAndPayments = new JButton("Facturación y Pagos");
        btnOwners = new JButton("Gestión de Dueños");
        btnPets = new JButton("Gestión de Mascotas");
        btnReports = new JButton("Reportes");

        // Estilizar botones
        styleMenuButton(btnUsers);
        styleMenuButton(btnAppointment);
        styleMenuButton(btnHistory);
        styleMenuButton(btnBillingAndPayments);
        styleMenuButton(btnOwners);
        styleMenuButton(btnPets);
        styleMenuButton(btnReports);

        // Hacer que los botones se expandan horizontalmente
        Dimension buttonSize = new Dimension(200, 45);
        btnUsers.setPreferredSize(buttonSize);
        btnUsers.setMaximumSize(buttonSize);
        btnUsers.setMinimumSize(buttonSize);
        
        btnAppointment.setPreferredSize(buttonSize);
        btnAppointment.setMaximumSize(buttonSize);
        btnAppointment.setMinimumSize(buttonSize);
        
        btnHistory.setPreferredSize(buttonSize);
        btnHistory.setMaximumSize(buttonSize);
        btnHistory.setMinimumSize(buttonSize);
        
        btnBillingAndPayments.setPreferredSize(buttonSize);
        btnBillingAndPayments.setMaximumSize(buttonSize);
        btnBillingAndPayments.setMinimumSize(buttonSize);
        
        btnOwners.setPreferredSize(buttonSize);
        btnOwners.setMaximumSize(buttonSize);
        btnOwners.setMinimumSize(buttonSize);
        
        btnPets.setPreferredSize(buttonSize);
        btnPets.setMaximumSize(buttonSize);
        btnPets.setMinimumSize(buttonSize);
        
        btnReports.setPreferredSize(buttonSize);
        btnReports.setMaximumSize(buttonSize);
        btnReports.setMinimumSize(buttonSize);

        // Agregar botones con espacio entre ellos
        JPanel btnUsersPanel = createButtonPanel(btnUsers);
        JPanel btnAppointmentPanel = createButtonPanel(btnAppointment);
        JPanel btnHistoryPanel = createButtonPanel(btnHistory);
        JPanel btnBillingPanel = createButtonPanel(btnBillingAndPayments);
        JPanel btnOwnersPanel = createButtonPanel(btnOwners);
        JPanel btnPetsPanel = createButtonPanel(btnPets);
        JPanel btnReportsPanel = createButtonPanel(btnReports);

        sideMenu.add(btnUsersPanel);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(btnAppointmentPanel);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(btnHistoryPanel);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(btnBillingPanel);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(btnOwnersPanel);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(btnPetsPanel);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(btnReportsPanel);
    }
    
    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(new Color(245, 245, 245));
        panel.add(button);
        return panel;
    }

    private void createContentView() {
        contentView = new JPanel(new BorderLayout());
        contentView.setBackground(Color.WHITE);

        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Clínica Vet", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 20));
        lblBienvenida.setForeground(new Color(60, 60, 60));

        contentView.add(lblBienvenida, BorderLayout.CENTER);
    }

    private void styleTopButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    private void styleMenuButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        // Hacer que el texto se alinee a la izquierda
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void setContent(JPanel newContent) {
        contentView.removeAll();
        contentView.add(newContent, BorderLayout.CENTER);
        contentView.revalidate();
        contentView.repaint();
    }
    
    public JPanel getWelcomeView() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Clínica Vet", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 20));
        lblBienvenida.setForeground(new Color(60, 60, 60));
        welcomePanel.add(lblBienvenida, BorderLayout.CENTER);
        return welcomePanel;
    }

    public JButton getBtnProfile() { return btnPerfil; }
    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnUsers() { return btnUsers; }
    public JButton getBtnAppointment() { return btnAppointment; }
    public JButton getBtnHistory() { return btnHistory; }
    public JButton getBtnPayments() { return btnPayments; }
    public JButton getBtnOwners() { return btnOwners; }
    public JButton getBtnPets() { return btnPets; }
    public JButton getBtnReports() { return btnReports; } 
    public JButton getBtnBillingAndPayments() { return btnBillingAndPayments; }
}
