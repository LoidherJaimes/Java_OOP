package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.MainWindowView;

import clinica_vet.views.LoginView;

import javax.swing.*;


public class MainWindowController {

    private MainWindowView mainView;
    private UserRepository userRepository;
    private boolean isAdmin; // <-- Variable para controlar visibilidad

    public MainWindowController(MainWindowView mainView, User user, UserRepository userRepository) {
        this.mainView = mainView;
        this.userRepository = userRepository;

        // Determinar si es admin
        isAdmin = user.getRol() != null && user.getRol().getName().equalsIgnoreCase("Administrador");

        // Mostrar u ocultar botón de Gestión de Usuarios
        mainView.getBtnUsers().setVisible(isAdmin);
        this.mainView.getBtnProfile().addActionListener(e -> {
            JOptionPane.showMessageDialog(
                mainView,
                "Usuario: " + user.getUsername() + "\nRol: " + (user.getRol() != null ? user.getRol().getName() : "Sin rol"),
                "Perfil",
                JOptionPane.INFORMATION_MESSAGE
            );
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
                new LoginController(loginView, userRepository);
                loginView.setVisible(true);
            }
        });
        this.mainView.getBtnUsers().addActionListener(e -> {
            // Abrir ventana de gestión de usuarios
            clinica_vet.views.ManageUsersView manageUsersView = new clinica_vet.views.ManageUsersView();
            new ManageUsersController(manageUsersView, userRepository);
        });

    }
}
