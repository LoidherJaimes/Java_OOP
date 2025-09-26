package clinica_vet.controllers;

import clinica_vet.views.CreateUserView;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.model.entities.User;
import clinica_vet.model.entities.Rol;
import clinica_vet.views.LoginView;

import javax.swing.*;
import java.util.List;

public class CreateUserController {

    private CreateUserView createUserView;
    private UserRepository userRepository;
    private LoginView loginView;

    public CreateUserController(CreateUserView createUserView, UserRepository userRepository, LoginView loginView) {
        this.createUserView = createUserView;
        this.userRepository = userRepository;
        this.loginView = loginView;

        this.createUserView.getBtnCreateUserL().addActionListener(e -> {
            String username = createUserView.getCreateUserTF().getText();
            String password = new String(createUserView.getCreatePasswordPF().getPassword());
            String passwordVerify = new String(createUserView.getVerificationPasswordPF().getPassword());

            if (username.isEmpty() || password.isEmpty() || passwordVerify.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                return;
            }

            if (!password.equals(passwordVerify)) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                return;
            }

            List<User> listadoUsuario = userRepository.getAllUsers();
            for (User u : listadoUsuario) {
                if (username.equals(u.getUsername())) {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe.");
                    return;
                }
            }

            int newId = listadoUsuario.size() + 1;
            // Rol por defecto
            Rol defaultRol = new Rol(0, "USER");
            User newUser = new User(newId, username, password, defaultRol);
            userRepository.addUser(newUser);

            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente.");

            createUserView.setVisible(false);
            loginView.setVisible(true);
        });
    }
}
