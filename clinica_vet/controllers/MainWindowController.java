package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.LoginView;
import clinica_vet.views.ProfileView; 
import clinica_vet.views.LogoutView; // Importar la nueva vista de Logout
import clinica_vet.views.ManageUsersView; // Importar la vista de gestión de usuarios




public class MainWindowController {

    private MainWindowView mainView;
    private UserRepository userRepository;
    private boolean isAdmin; 
    private User currentUser; 

    public MainWindowController(MainWindowView mainView, User user, UserRepository userRepository) {
        this.mainView = mainView;
        this.userRepository = userRepository;
        this.currentUser = user; 

        // Determinar si es admin
        isAdmin = user.getRol() != null && user.getRol().getName().equalsIgnoreCase("Administrador");

        // Mostrar u ocultar botón de Gestión de Usuarios
        mainView.getBtnUsers().setVisible(isAdmin);
        
        // --- Lógica del botón Profile ---
        this.mainView.getBtnProfile().addActionListener(e -> {
            ProfileView profileView = new ProfileView(this.currentUser); 
            profileView.setVisible(true);
        });
        
        // --- Logout (Lógica MODIFICADA) ---
        this.mainView.getBtnLogout().addActionListener(e -> {
            
            // 1. Instanciar la vista personalizada de Logout
            LogoutView logoutView = new LogoutView(mainView);
            
            // 2. Mostrar la vista (es modal, espera la respuesta)
            logoutView.setVisible(true);

            // 3. Verificar el resultado de la confirmación
            if (logoutView.isConfirmed()) {
                mainView.dispose();
                LoginView loginView = new LoginView();
                // Asumo que tienes un LoginController
                // new LoginController(loginView, userRepository); 
                loginView.setVisible(true);
            }
        });
        
        // --- Lógica de Gestión de Usuarios ---
        this.mainView.getBtnUsers().addActionListener(e -> {
            // Abrir ventana de gestión de usuarios
            ManageUsersView manageUsersView = new ManageUsersView();
            // Asumo que tienes un ManageUsersController que recibe las dependencias
            // new ManageUsersController(manageUsersView, userRepository, rolService);
            manageUsersView.setVisible(true);
        });
    }
}