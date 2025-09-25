package clinica_vet.views;

import java.awt.*;
import javax.swing.*;

public class MainWindowView extends JFrame {
    private JPanel contentView;
    private JPanel topBar;
    private JPanel sideMenu;

    // Botones accesibles desde el controlador
    private JButton btnPerfil;
    private JButton btnLogout;
    private JButton btnUsers;
    private JButton btnAppointment;
    private JButton btnHistory;
    private JButton btnPayments;

    // ✅ Constructor: arma la ventana directamente
    public MainWindowView() {
        setTitle("Clinica Vet");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createTopBar();
        createContentView();
        createSideMenu();

        add(topBar, BorderLayout.NORTH);
        add(contentView, BorderLayout.CENTER);
        add(sideMenu, BorderLayout.WEST);
    }

    public void createTopBar() {
        topBar = new JPanel();
        topBar.setLayout(new BorderLayout());
        topBar.setBackground(Color.LIGHT_GRAY);

        // Botón perfil (izquierda)
        btnPerfil = new JButton("Perfil");

        // Botón cerrar sesión (derecha)
        btnLogout = new JButton("Cerrar sesión");

        topBar.add(btnPerfil, BorderLayout.WEST);
        topBar.add(btnLogout, BorderLayout.EAST);
    }

    public void createContentView() {
        contentView = new JPanel();
        contentView.setLayout(new BorderLayout());
        contentView.setBackground(Color.WHITE);

        // Texto de bienvenida por defecto
        JLabel lblBienvenida = new JLabel("Bienvenido a Clínica Vet", SwingConstants.CENTER);
        contentView.add(lblBienvenida, BorderLayout.CENTER);
    }

    public void createSideMenu() {
        sideMenu = new JPanel();
        sideMenu.setLayout(new GridLayout(0, 1)); // Vertical
        sideMenu.setBackground(Color.DARK_GRAY);

        // Botones del menú
        btnUsers = new JButton("Gestión de Usuarios");
        btnAppointment = new JButton("Agendar Citas");
        btnHistory = new JButton("Historia Clínica");
        btnPayments = new JButton("Facturación y Pagos");

        sideMenu.add(btnUsers);
        sideMenu.add(btnAppointment);
        sideMenu.add(btnHistory);
        sideMenu.add(btnPayments);
    }

    // Getters para los botones
    public JButton getBtnProfile() { return btnPerfil; }
    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnUsers() { return btnUsers; }
    public JButton getBtnAppointment() { return btnAppointment; }
    public JButton getBtnHistory() { return btnHistory; }
    public JButton getBtnPayments() { return btnPayments; }
}
