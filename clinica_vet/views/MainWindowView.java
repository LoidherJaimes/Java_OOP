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
        topBar.setBackground(new Color(70, 130, 180)); // Azul elegante
        topBar.setPreferredSize(new Dimension(1000, 50));

        JLabel title = new JLabel("Clínica Veterinaria - Sistema de Gestión", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        btnPerfil = new JButton("Perfil");
        btnLogout = new JButton("Cerrar Sesión");

        styleTopButton(btnPerfil, new Color(255, 215, 0)); // Amarillo dorado
        styleTopButton(btnLogout, new Color(220, 20, 60)); // Rojo

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        rightPanel.setBackground(new Color(70, 130, 180));
        rightPanel.add(btnPerfil);
        rightPanel.add(btnLogout);

        topBar.add(title, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);
    }

    private void createSideMenu() {
        sideMenu = new JPanel();
        sideMenu.setLayout(new GridLayout(6, 1, 0, 15));
        sideMenu.setBackground(new Color(245, 245, 245));
        sideMenu.setPreferredSize(new Dimension(220, 0));

        btnUsers = new JButton("👥 Gestión de Usuarios");
        btnAppointment = new JButton("📅 Agendar Citas");
        btnHistory = new JButton("📖 Historia Clínica");
        btnPayments = new JButton("💳 Facturación y Pagos");

        styleMenuButton(btnUsers);
        styleMenuButton(btnAppointment);
        styleMenuButton(btnHistory);
        styleMenuButton(btnPayments);

        sideMenu.add(btnUsers);
        sideMenu.add(btnAppointment);
        sideMenu.add(btnHistory);
        sideMenu.add(btnPayments);
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
    }

    public void setContent(JPanel newContent) {
        contentView.removeAll();
        contentView.add(newContent, BorderLayout.CENTER);
        contentView.revalidate();
        contentView.repaint();
    }

    // Getters
    public JButton getBtnProfile() { return btnPerfil; }
    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnUsers() { return btnUsers; }
    public JButton getBtnAppointment() { return btnAppointment; }
    public JButton getBtnHistory() { return btnHistory; }
    public JButton getBtnPayments() { return btnPayments; }
}
