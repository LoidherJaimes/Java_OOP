package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.ProfileView; // Ahora es JPanel
import clinica_vet.views.LogoutView;   // Nuevo JPanel

import javax.swing.*;

public class MainWindowController {
    
    private MainWindowView mainWindowView;
    private User currentUser;
    private UserRepository userRepository;
    private IRolService rolService;

    public MainWindowController(MainWindowView mainWindowView, User currentUser, UserRepository userRepository, IRolService rolService) {
        this.mainWindowView = mainWindowView;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.rolService = rolService;

        setupListeners();
        mainWindowView.setContent(mainWindowView.getWelcomeView());
    }

    private void setupListeners() {
        // ⭐ Listener para Perfil (Carga la vista en el panel central)
        mainWindowView.getBtnProfile().addActionListener(e -> {
            loadProfileView();
        });

        // ⭐ Listener para Cerrar Sesión (Carga la vista de confirmación en el panel central)
        mainWindowView.getBtnLogout().addActionListener(e -> {
            loadLogoutView();
        });

        // Listener para Gestión de Usuarios
        mainWindowView.getBtnUsers().addActionListener(e -> {
            loadManageUsersView();
        });
        
        // ... (otros listeners) ...
    }
    
    // --------------------------------------------------------
    // MÉTODOS DE CARGA DE VISTAS EN EL PANEL CENTRAL
    // --------------------------------------------------------

    private void loadProfileView() {
        ProfileView profileView = new ProfileView(currentUser);
        mainWindowView.setContent(profileView);
        // Opcional: Si ProfileView tuviera un botón "Volver", se le agregaría un listener aquí.
    }
    
    private void loadLogoutView() {
        LogoutView logoutView = new LogoutView();
        mainWindowView.setContent(logoutView);
        
        // Configurar los listeners para la confirmación de logout
        logoutView.getBtnYes().addActionListener(e -> {
            // Lógica para cerrar la aplicación y volver a la vista de Login (PENDIENTE DE IMPLEMENTACIÓN)
            mainWindowView.dispose();
            // Asumiendo que MainApp reabre la vista de Login...
            JOptionPane.showMessageDialog(null, "Sesión cerrada. Volviendo a la pantalla de login.");
        });
        
        logoutView.getBtnNo().addActionListener(e -> {
            // Vuelve a la vista de bienvenida si cancela el cierre
            mainWindowView.setContent(mainWindowView.getWelcomeView());
        });
    }

    private void loadManageUsersView() {
        ManageUsersView manageUsersView = new ManageUsersView();
        new ManageUsersController(manageUsersView, userRepository, rolService, mainWindowView);
        mainWindowView.setContent(manageUsersView);
        
        // Listener para el botón "Volver" dentro de ManageUsersView
        manageUsersView.getBtnClose().addActionListener(e -> {
            mainWindowView.setContent(mainWindowView.getWelcomeView());
        });
    }
    
    // ... (otros métodos de carga de vistas) ...
}