package clinica_vet.views;

import java.awt.*;
import javax.swing.*;

public class MainWindowView extends JFrame{
    private JPanel contentView;
    private JPanel topBar;
    private JPanel sideMenu;

    public void mainView(){
        //creamos la ventana principal
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
        
        setVisible(true);
    }

    public void createTopBar(){
        topBar = new JPanel();

        topBar.setLayout(new BorderLayout());
        topBar.setBackground(Color.LIGHT_GRAY);

        // Botón perfil (izquierda)
        JButton btnPerfil = new JButton("Perfil");

        // Botón cerrar sesión (derecha)
        JButton btnLogout = new JButton("Cerrar sesión");

        topBar.add(btnPerfil, BorderLayout.WEST);
        topBar.add(btnLogout, BorderLayout.EAST);
    }

    public void createContentView(){
        contentView = new JPanel();
        
        contentView.setLayout(new BorderLayout());
        contentView.setBackground(Color.WHITE);

        // Texto de bienvenida por defecto
        JLabel lblBienvenida = new JLabel("Bienvenido a Clínica Vet", SwingConstants.CENTER);
        contentView.add(lblBienvenida, BorderLayout.CENTER);
    }

    public void createSideMenu(){
        sideMenu = new JPanel();
        
        sideMenu.setLayout(new GridLayout(0, 1)); // Vertical
        sideMenu.setBackground(Color.DARK_GRAY);

        // Ejemplo de botones
        JButton btnUsers = new JButton("Gestión de Usuarios");
        JButton btnAppointment = new JButton("Agendar Citas");
        JButton btnHistory = new JButton("Historia Clínica");
        JButton btnPayments = new JButton("Facturación y Pagos");

        sideMenu.add(btnUsers);
        sideMenu.add(btnAppointment);
        sideMenu.add(btnHistory);
        sideMenu.add(btnPayments);
    }
}