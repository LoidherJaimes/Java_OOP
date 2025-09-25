package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.CreateUserView;
import clinica_vet.views.LoginView;
import clinica_vet.views.MainWindowView;
import javax.swing.*;
import java.util.List;

public class LoginController {

    public LoginController(LoginView vista, UserRepository userRepository) {
        // Listener del botón Login
        vista.getBtnLogin().addActionListener(e -> {
            String username = vista.getUserTF().getText();
            String password = new String(vista.getPasswordPF().getPassword());
            List<User> listadoUsuario = userRepository.getAllUsers();
            User loginUser = null;

            for (User u : listadoUsuario) {
                if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                    loginUser = u;
                    break;
                }
            }

            if (loginUser != null) {
                JOptionPane.showMessageDialog(null, "Login exitoso");
                vista.dispose();

                // ✅ Abrir la ventana principal directamente
                MainWindowView mainView = new MainWindowView();
                MainWindowController mainController = new MainWindowController(mainView, loginUser, userRepository);
                mainView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Login fallido");
            }
        });

        // Listener del botón Crear Usuario
        vista.getBtnCreateUser().addActionListener(ev -> {
            CreateUserView createUserView = new CreateUserView();
            createUserView.setVisible(true);
            vista.setVisible(false);
        });
    }
}
