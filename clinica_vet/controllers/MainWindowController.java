package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.LoginView;
import clinica_vet.views.ProfileView;

import javax.swing.JOptionPane;
import java.util.List;

public class MainWindowController {

    private MainWindowView mainView;
    private UserRepository userRepository;

    public MainWindowController(MainWindowView mainView, User user, UserRepository userRepository) {
        this.mainView = mainView;
        this.userRepository = userRepository;

        // Listener Logout
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
                loginView.setVisible(true);
            }
        });

        // Listener Gestión de Usuarios
        this.mainView.getBtnUsers().addActionListener(e -> {
            ManageUsersView manageUsersView = new ManageUsersView();

            // Limpiamos tabla antes de llenar
            manageUsersView.clearTable();

            // Obtenemos usuarios del repositorio
            List<User> listadoUsuarios = userRepository.getAllUsers();

            // Añadimos cada usuario a la tabla
            for (User u : listadoUsuarios) {
                manageUsersView.addUserToTable(u.getId(), u.getUsername(), u.getPassword(), u.getRol());
            }

            manageUsersView.setVisible(true);
        });
    }
}
