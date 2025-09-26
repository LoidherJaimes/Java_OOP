package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.LoginView;

import javax.swing.*;
import java.util.List;

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

        // Gestión de usuarios
        this.mainView.getBtnUsers().addActionListener(e -> {
            ManageUsersView manageUsersView = new ManageUsersView();
            new ManageUsersController(manageUsersView, userRepository);

            // Limpiar la tabla antes de llenarla
            manageUsersView.clearTable();

            // Cargar todos los usuarios en la tabla
            List<User> listadoUsuarios = userRepository.getAllUsers();
            for (User u : listadoUsuarios) {
                String rolNombre = (u.getRol() != null) ? u.getRol().getName() : "Sin rol";
                manageUsersView.addUserToTable(u.getId(), u.getUsername(), u.getPassword(), rolNombre);
            }

            manageUsersView.setVisible(true);
        });
    }
}
