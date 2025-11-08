package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolService; // Nueva Importación
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.LoginView;
import clinica_vet.views.ProfileView; 
import clinica_vet.views.LogoutView; 
import clinica_vet.views.ManageUsersView; 


public class MainWindowController {

    private MainWindowView mainView;
    private UserRepository userRepository;
    private IRolService rolService; // Nueva dependencia
    private boolean isAdmin; 
    private User currentUser; 

    // Constructor Modificado
    public MainWindowController(MainWindowView mainView, User user, UserRepository userRepository, IRolService rolService) {
        this.mainView = mainView;
        this.userRepository = userRepository;
        this.currentUser = user; 
        this.rolService = rolService; // Asignación

        // Determinar si es admin
        isAdmin = user.getRol() != null && user.getRol().getName().equalsIgnoreCase("Administrador");

        // Mostrar u ocultar botón de Gestión de Usuarios
        mainView.getBtnUsers().setVisible(isAdmin);
        
        // --- Lógica del botón Profile ---
        this.mainView.getBtnProfile().addActionListener(e -> {
            ProfileView profileView = new ProfileView(this.currentUser); 
            profileView.setVisible(true);
        });
        
        // --- Logout ---
        this.mainView.getBtnLogout().addActionListener(e -> {
            LogoutView logoutView = new LogoutView(mainView);
            logoutView.setVisible(true);

            if (logoutView.isConfirmed()) {
                mainView.dispose();
                LoginView loginView = new LoginView();
                // Volver a instanciar el LoginController con sus dependencias
                new LoginController(loginView, userRepository, rolService); 
                loginView.setVisible(true);
            }
        });
        
        // --- Lógica de Gestión de Usuarios (CORREGIDA) ---
        this.mainView.getBtnUsers().addActionListener(e -> {
            ManageUsersView manageUsersView = new ManageUsersView();
            
            // ¡CORRECCIÓN CLAVE! Instanciar ManageUsersController y pasar RolService
            // Esto dispara la carga de la tabla.
            new ManageUsersController(manageUsersView, userRepository, rolService); 
        });
    }
}