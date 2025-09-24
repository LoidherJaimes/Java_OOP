package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.views.MainWindowView;
import javax.swing.JOptionPane;

public class MainWindowController{

    private MainWindowView mainView;
    private User loggedUser;

    public MainWindowController(MainWindowView MainWindowView, User user) {

        // Listener del botón Perfil
        this.mainView.getBtnProfile().addActionListener(e -> {
            ProfileView profileView = new ProfileView(loggedUser);
            profileView.setVisible(true);
        });

        // Listener del botón Logout
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
    }
}