package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.LoginView;
import clinica_vet.views.ProfileView; // Importar la vista de perfil

import javax.swing.*;


public class MainWindowController {

    private MainWindowView mainView;
    private UserRepository userRepository;
    private boolean isAdmin; 
    private User currentUser; // Guardar el usuario actual

    public MainWindowController(MainWindowView mainView, User user, UserRepository userRepository) {
        this.mainView = mainView;
        this.userRepository = userRepository;
        this.currentUser = user; // Asignar el usuario

        // Determinar si es admin
        isAdmin = user.getRol() != null && user.getRol().getName().equalsIgnoreCase("Administrador");

        // Mostrar u ocultar botón de Gestión de Usuarios
        mainView.getBtnUsers().setVisible(isAdmin);
        
        // --- Lógica del botón Profile MODIFICADA ---
        this.mainView.getBtnProfile().addActionListener(e -> {
            // Instanciar y mostrar la ProfileView, pasándole el objeto 'user'
            ProfileView profileView = new ProfileView(this.currentUser); 
            profileView.setVisible(true);
            // Si quieres que la ventana principal se oculte: mainView.setVisible(false);
        });
        
        // Logout
        this.mainView.getBtnLogout().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                mainView,
                "¿Desea cerrar sesión?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                mainView.dispose();
                LoginView loginView = new LoginView();
                // Asumo que tienes un LoginController
                // new LoginController(loginView, userRepository); 
                loginView.setVisible(true);
            }
        });
        
        this.mainView.getBtnUsers().addActionListener(e -> {
            // Abrir ventana de gestión de usuarios
            clinica_vet.views.ManageUsersView manageUsersView = new clinica_vet.views.ManageUsersView();
            // Asumo que tienes un ManageUsersController
            // new ManageUsersController(manageUsersView, userRepository);
            manageUsersView.setVisible(true);
        });

    }
}