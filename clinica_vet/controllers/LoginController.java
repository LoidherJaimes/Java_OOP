package clinica_vet.controllers;

import clinica_vet.model.repositories.UserRepository;
import clinica_vet.model.entities.User;
import clinica_vet.views.CreateUserView;
import clinica_vet.views.loginView;

import javax.swing.JOptionPane;
import java.util.List;

public class LoginController {

    public LoginController(loginView vista, UserRepository userRepository) {
        // Listener del botón Login
        vista.getBtnLogin().addActionListener(e -> {
            String username = vista.getUserTF().getText();
            String password = new String(vista.getPasswordPF().getPassword());
            List<User> listadoUsuario = userRepository.getAllUsers();

            boolean encontrado = false;
            for (User u : listadoUsuario) {
                if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                JOptionPane.showMessageDialog(null, " Login exitoso");
                
            } else {
                JOptionPane.showMessageDialog(null, " Login fallido");
                
            }
        });

        // Listener del botón Crear Usuario (fuera del login)
        vista.getBtnCreateUser().addActionListener(ev -> {
            CreateUserView createUserView = new CreateUserView();
            createUserView.setVisible(true);
            vista.setVisible(false);
        });
    }
}
